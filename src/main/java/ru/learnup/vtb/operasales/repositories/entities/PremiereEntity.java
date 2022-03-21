package ru.learnup.vtb.operasales.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="premiere", schema="opera")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PremiereEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="age_category")
    private Integer ageCategory;

    @Column(name="available_seats")
    private Integer availableSeats;

    @OneToMany(mappedBy = "premiere", fetch = FetchType.EAGER)
    private Collection<TicketEntity> tickets;

    @Override
    public String toString() {
        return name +
                " [" + description + "] " +
                "(Age Category: " + ageCategory + ") - " +
                "Free Seats: " + availableSeats + "\n" +
                "Tickets: " + tickets;
    }
}
