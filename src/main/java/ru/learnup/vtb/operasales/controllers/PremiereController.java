package ru.learnup.vtb.operasales.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.learnup.vtb.operasales.controllers.dto.PremiereDto;
import ru.learnup.vtb.operasales.mappers.PremiereMapper;
import ru.learnup.vtb.operasales.services.PremiereService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/premiere")
public class PremiereController {

    private PremiereService service;
    private PremiereMapper premiereMapper;

    @Autowired
    public PremiereController(PremiereService service, PremiereMapper premiereMapper) {
        this.service = service;
        this.premiereMapper = premiereMapper;
    }

    @GetMapping
    public Collection<PremiereDto> getAll() {
        return service.getAll().stream()
                .map(premiereMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PremiereDto get(@PathVariable("id") long id) {
        return premiereMapper.toDto(
                service.get(id));
    }

    @PostMapping
    public PremiereDto save(@RequestBody PremiereDto premiereDto) {
        return premiereMapper.toDto(
                service.save(
                        premiereMapper.toDomain(premiereDto)));
    }

    @PutMapping
    public PremiereDto update(@RequestBody PremiereDto premiereDto) {
        return premiereMapper.toDto(
                service.update(
                        premiereMapper.toDomain(premiereDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
