package ru.learnup.vtb.operasales.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class Premiere {

    private String name;
    private String description;
    private Integer ageCategory;
    private Integer availableSeats;
    private Set<Ticket> tickets;

    @Override
    public String toString() {
        return name +
                " [" + description + "] " +
                "(Age Category: " + ageCategory + ") - " +
                "Free Seats: " + availableSeats + "\n" +
                "Tickets: " + tickets;
    }
}
