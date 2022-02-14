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

}
