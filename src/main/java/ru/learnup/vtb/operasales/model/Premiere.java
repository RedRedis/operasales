package ru.learnup.vtb.operasales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Premiere {

    private Long id;
    private String name;
    private String description;
    private Integer ageCategory;
    private Integer availableSeats;
}
