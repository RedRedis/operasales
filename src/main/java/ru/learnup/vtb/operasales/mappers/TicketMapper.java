package ru.learnup.vtb.operasales.mappers;

import org.mapstruct.Mapper;
import ru.learnup.vtb.operasales.controllers.dto.TicketDto;
import ru.learnup.vtb.operasales.model.Ticket;
import ru.learnup.vtb.operasales.repositories.entities.TicketEntity;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDto toDto(Ticket ticket);
    Ticket toDomain(TicketDto ticketDto);

    TicketEntity toEntity(Ticket ticket);
    Ticket toDomain(TicketEntity ticketEntity);
}
