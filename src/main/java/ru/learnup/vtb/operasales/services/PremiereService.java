package ru.learnup.vtb.operasales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.vtb.operasales.annotations.Email;
import ru.learnup.vtb.operasales.mappers.PremiereMapper;
import ru.learnup.vtb.operasales.model.Premiere;
import ru.learnup.vtb.operasales.repositories.entities.PremiereEntity;
import ru.learnup.vtb.operasales.repositories.PremiereRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiereService {

    private PremiereRepository repository;
    private PremiereMapper mapper;

    @Autowired
    public PremiereService(PremiereRepository repository, PremiereMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<Premiere> getAll() {
       return repository.findAll().stream()
               .map(mapper::toDomain)
               .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Premiere get(long id) {
        return mapper.toDomain(
                repository.getById(id));
    }

    @Email
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            timeout = 2,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Premiere save(Premiere premiere) {
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
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(premiere)));
    }

    @Email
    @Transactional(
            timeout = 2,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Premiere update(Premiere changePremiere) {

        if (changePremiere.getName() == null || changePremiere.getName().isEmpty()) {
            throw new IllegalArgumentException("Null or empty name for getting premiere");
        }

        Premiere premiere = get(changePremiere.getId());

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
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(premiere)));
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {FileNotFoundException.class, IOException.class, EOFException.class}
    )
    public Premiere delete(long id) {
        PremiereEntity premiere = repository.getById(id);
        if (premiere == null) {
            throw new IllegalArgumentException("No such premiere for delete");
        }
        repository.delete(premiere);
        return mapper.toDomain(premiere);
    }

}
