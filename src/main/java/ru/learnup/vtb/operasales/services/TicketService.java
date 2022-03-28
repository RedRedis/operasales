package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.vtb.operasales.mappers.PremiereMapper;
import ru.learnup.vtb.operasales.mappers.TicketMapper;
import ru.learnup.vtb.operasales.model.Premiere;
import ru.learnup.vtb.operasales.model.Ticket;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;
import ru.learnup.vtb.operasales.repositories.TicketRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static PremiereRepository repository;
    private static TicketRepository ticketRepository;
    private static PremiereMapper premiereMapper;
    private static TicketMapper ticketMapper;

    @Autowired
    public TicketService(PremiereRepository repository, TicketRepository ticketRepository, PremiereMapper premiereMapper, TicketMapper ticketMapper) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
        this.premiereMapper = premiereMapper;
        this.ticketMapper = ticketMapper;
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    public List<Ticket> getAll() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDomain)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    public Ticket getInfo(long id) {
        return ticketMapper.toDomain(
                ticketRepository.getById(id));
    }

    //@Email
    @PreAuthorize("hasRole(\"USER\")")
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            timeout = 5,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Ticket buyTicket(Ticket ticket) {
        Premiere premiere = premiereMapper.toDomain(repository.findByName(ticket.getPremiere().getName()));
        if (premiere != null && premiere.getAvailableSeats() > 0) {
            ticket.setNumber((int)(2_000_000_000 * Math.random()));
            premiere.setAvailableSeats(premiere.getAvailableSeats() - 1);
            repository.save(
                    premiereMapper.toEntity(premiere));
            ticket.setPremiere(premiere);
            return ticketMapper.toDomain(
                    ticketRepository.save(
                            ticketMapper.toEntity(ticket)));
        }
        return null;
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public boolean returnTicket(Integer number) {
        Ticket ticket = ticketMapper.toDomain(
                ticketRepository.findByNumber(number));
        if (ticket != null) {
            Premiere premiere = premiereMapper.toDomain(
                    repository.findByName(ticket.getPremiere().getName()));
            if (premiere != null) {
                premiere.setAvailableSeats(premiere.getAvailableSeats() + 1);
                ticketRepository.delete(
                        ticketMapper.toEntity(ticket));
                repository.save(
                        premiereMapper.toEntity(premiere));
                return true;
            }
        }
        return false;
    }
}
