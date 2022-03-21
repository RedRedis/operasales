package ru.learnup.vtb.operasales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.vtb.operasales.repositories.entities.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByNumber(Integer number);
}
