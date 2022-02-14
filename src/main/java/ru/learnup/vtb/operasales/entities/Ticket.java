package ru.learnup.vtb.operasales.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Ticket {

    private Integer number;
    private String nameOfPremiere;

    @Override
    public String toString() {
        return "Ticket: number - " + number + ", name of Premiere - " + nameOfPremiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(number, ticket.number) && Objects.equals(nameOfPremiere, ticket.nameOfPremiere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, nameOfPremiere);
    }
}
