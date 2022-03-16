package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.vtb.operasales.annotations.Email;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.entities.Ticket;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;
import ru.learnup.vtb.operasales.repositories.TicketRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class TicketService {

    private static PremiereRepository repository;
    private static TicketRepository ticketRepository;

    @Autowired
    public TicketService(PremiereRepository repository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
    }

    @Email
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            timeout = 2,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Ticket buyTicket(String name) {
        Premiere premiere = repository.findByName(name);
        if (premiere != null && premiere.getAvailableSeats() > 0) {
            Ticket ticket = new Ticket(null, (int)(2_000_000_000 * Math.random()), premiere);
            premiere.setAvailableSeats(premiere.getAvailableSeats() - 1);
            repository.save(premiere);
            ticketRepository.save(ticket);
            return ticket;
        }
        return null;
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public boolean returnTicket(Integer number) {
        Ticket ticket = ticketRepository.findByNumber(number);
        if (ticket != null) {
            Premiere premiere = repository.findByName(ticket.getPremiere().getName());
            if (premiere != null) {
                premiere.setAvailableSeats(premiere.getAvailableSeats() + 1);
                repository.save(premiere);
                ticketRepository.delete(ticket);
                return true;
            }
        }
        return false;
    }
}
