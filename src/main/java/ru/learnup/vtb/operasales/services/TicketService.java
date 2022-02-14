package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return repository.buyTicket(name);
    }

    public boolean returnTicket(Ticket ticket) {
        return repository.returnTicket(ticket);
    }
}
