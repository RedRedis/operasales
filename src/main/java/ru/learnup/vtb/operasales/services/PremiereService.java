package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.learnup.vtb.operasales.annotations.Email;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PremiereService {

    private static PremiereRepository repository;

    @Autowired
    public PremiereService(PremiereRepository repository) {
        this.repository = repository;
    }

    public List<String> getAllPremiere() {
        List<Premiere> premieres = repository.findAll();
        List<String> names = new ArrayList<>();
        for (Premiere premiere : premieres) {
            names.add(premiere.getName());
        }
        return names;
    }

    public String getPremiereInfo(String name) {
        Premiere premiere = repository.findByName(name);
        if (premiere == null) {
            return "No such premiere";
        }
        return premiere.toString();
    }

    @Email
    public Premiere createPremiere(Premiere premiere) {
        if (premiere.getName() == null || premiere.getName().isEmpty()) {
            throw new IllegalArgumentException("Null or empty name");
        }
        if (premiere.getDescription() == null) {
            throw new IllegalArgumentException("Null description");
        }
        if (premiere.getAgeCategory() == null || premiere.getAgeCategory() > 21 || premiere.getAgeCategory() < 0) {
            throw new IllegalArgumentException("Age Category must be in [0, 21]");
        }
        if (premiere.getAvailableSeats() == null || premiere.getAvailableSeats() < 0) {
            throw new IllegalArgumentException("Available seat cannot be less then 0");
        }
        repository.save(premiere);
        return premiere;
    }

    @Email
    public Premiere changePremiere(Premiere changePremiere) {

        if (changePremiere.getName() == null || changePremiere.getName().isEmpty()) {
            throw new IllegalArgumentException("Null or empty name for getting premiere");
        }

        Premiere premiere = repository.findByName(changePremiere.getName());

        if (premiere == null) {
            throw new IllegalArgumentException("No such premiere for changing");
        }

        if (changePremiere.getDescription() != null) {
            premiere.setDescription(changePremiere.getDescription());
        }
        if (changePremiere.getAgeCategory() != null && changePremiere.getAgeCategory() <= 21 && changePremiere.getAgeCategory() >= 0) {
            premiere.setAgeCategory(changePremiere.getAgeCategory());
        }
        if (premiere.getAvailableSeats() != null && premiere.getAvailableSeats() >= 0) {
            premiere.setAvailableSeats(changePremiere.getAvailableSeats());
        }
        repository.save(premiere);
        return premiere;
    }

    public Premiere deletePremiere(String name) {
        Premiere premiere = repository.findByName(name);
        if (premiere != null) {
            repository.delete(premiere);
        }
        return premiere;
    }

}
