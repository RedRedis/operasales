package ru.learnup.vtb.operasales.repositories;

import org.springframework.stereotype.Repository;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.entities.Ticket;

import java.util.*;

@Repository
public class PremiereRepositoryImpl implements PremiereRepository {

    private static Map<String, Premiere> repository = new HashMap<>();

    static
    {
        repository.put("Spider", new Premiere("Spider", "About spiders", 12, 400, new HashSet<>()));
        repository.put("Humankind", new Premiere("Humankind", "About war", 18, 1000, new HashSet<>()));
        repository.put("Cats", new Premiere("Cats", "About cats", 8, 2500, new HashSet<>()));
    }

    @Override
    public List<Premiere> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Premiere get(String name) {
        return repository.get(name);
    }

    @Override
    public void create(Premiere premiere) {
        repository.put(premiere.getName(), premiere);
    }

    @Override
    public Premiere delete(String name) {
        return repository.remove(name);
    }

    @Override
    public Ticket buyTicket(String name) {
        Premiere premiere = repository.get(name);
        if (premiere != null && premiere.getAvailableSeats() > 0) {
            Ticket ticket = new Ticket((int)(2_000_000_000 * Math.random()), premiere.getName());
            premiere.getTickets().add(ticket);
            premiere.setAvailableSeats(premiere.getAvailableSeats() - 1);
            return ticket;
        }
        return null;
    }

    @Override
    public boolean returnTicket(Ticket ticket) {
        Premiere premiere = repository.get(ticket.getNameOfPremiere());
        if (premiere != null) {
            if (premiere.getTickets().contains(ticket)) {
                premiere.getTickets().remove(ticket);
                premiere.setAvailableSeats(premiere.getAvailableSeats() + 1);
                return true;
            }
        }
        return false;
    }
}
