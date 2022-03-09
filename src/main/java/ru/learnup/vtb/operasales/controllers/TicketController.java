package ru.learnup.vtb.operasales.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.learnup.vtb.operasales.entities.Ticket;
import ru.learnup.vtb.operasales.services.TicketService;

@Controller
public class TicketController {

    private static TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    public void buyTicket(String name) {
        Ticket ticket = service.buyTicket(name);
        if (ticket != null) {
            System.out.println("Ticket is bought: " + ticket.toString());
        } else {
            System.out.println("No seats or premiere");
        }
    }

    public void returnTicket(Integer number) {
        if (service.returnTicket(number)) {
            System.out.println("ticket is returned");
        } else {
            System.out.println("ticket is not found");
        }
    }
}
