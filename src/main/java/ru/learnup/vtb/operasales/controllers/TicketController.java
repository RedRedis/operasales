package ru.learnup.vtb.operasales.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.learnup.vtb.operasales.controllers.dto.TicketDto;
import ru.learnup.vtb.operasales.mappers.TicketMapper;
import ru.learnup.vtb.operasales.services.TicketService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private TicketService service;
    private TicketMapper ticketMapper;

    @Autowired
    public TicketController(TicketService service, TicketMapper ticketMapper) {
        this.service = service;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping
    public Collection<TicketDto> getAll() {
        return service.getAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TicketDto get(@PathVariable("id") long id) {
        return ticketMapper.toDto(
                service.getInfo(id));
    }

    @PostMapping
    public TicketDto save(@RequestBody TicketDto ticket) {
        return ticketMapper.toDto(
                service.buyTicket(
                        ticketMapper.toDomain(ticket)));
    }

    @DeleteMapping("/{number}")
    public void delete(@PathVariable("number") int number) {
        service.returnTicket(number);
    }
}
