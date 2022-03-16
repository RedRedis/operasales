package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.vtb.operasales.annotations.Email;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PremiereService {

    private static PremiereRepository repository;

    @Autowired
    public PremiereService(PremiereRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<String> getAllPremiere() {
        List<Premiere> premieres = repository.findAll();
        List<String> names = new ArrayList<>();
        for (Premiere premiere : premieres) {
            names.add(premiere.getName());
        }
        return names;
    }

    @Transactional(readOnly = true)
    public String getPremiereInfo(String name) {
        Premiere premiere = repository.findByName(name);
        if (premiere == null) {
            return "No such premiere";
        }
        return premiere.toString();
    }

    @Email
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            timeout = 2,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
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
    @Transactional(
            timeout = 2,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
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

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Premiere deletePremiere(String name) {
        Premiere premiere = repository.findByName(name);
        if (premiere != null) {
            repository.delete(premiere);
        }
        return premiere;
    }

}
