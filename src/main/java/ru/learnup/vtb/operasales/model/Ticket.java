package ru.learnup.vtb.operasales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {

    private Long id;
    private Integer number;
    private Premiere premiere;
}
