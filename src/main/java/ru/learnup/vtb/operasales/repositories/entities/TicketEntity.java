package ru.learnup.vtb.operasales.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="ticket", schema="opera")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number")
    private Integer number;

    @JoinColumn(name = "name_id")
    @ManyToOne
    private PremiereEntity premiere;

    @Override
    public String toString() {
        return "Ticket: number - " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketEntity ticket = (TicketEntity) o;
        return Objects.equals(number, ticket.number) && Objects.equals(premiere, ticket.premiere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, premiere);
    }
}
