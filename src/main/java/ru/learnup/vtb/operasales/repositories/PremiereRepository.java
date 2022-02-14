package ru.learnup.vtb.operasales.repositories;

import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.entities.Ticket;

import java.util.List;

public interface PremiereRepository {

    Premiere get(String name);
    List<Premiere> getAll();
    void create(Premiere premiere);
    Premiere delete(String name);
}
