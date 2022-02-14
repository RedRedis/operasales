package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.entities.Ticket;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;

@Service
public class TicketService {

    private static PremiereRepository repository;

    @Autowired
    public TicketService(PremiereRepository repository) {
        this.repository = repository;
    }

    public Ticket buyTicket(String name) {
        Premiere premiere = repository.get(name);
        if (premiere != null && premiere.getAvailableSeats() > 0) {
            Ticket ticket = new Ticket((int)(2_000_000_000 * Math.random()), premiere.getName());
            premiere.getTickets().add(ticket);
            premiere.setAvailableSeats(premiere.getAvailableSeats() - 1);
            return ticket;
        }
        return null;
    }

    public boolean returnTicket(Ticket ticket) {
        Premiere premiere = repository.get(ticket.getNameOfPremiere());
        if (premiere != null) {
            if (premiere.getTickets().contains(ticket)) {
                premiere.getTickets().remove(ticket);
                premiere.setAvailableSeats(premiere.getAvailableSeats() + 1);
                return true;
            }
        }
        return false;
    }
}
