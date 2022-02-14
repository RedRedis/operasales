package ru.learnup.vtb.operasales.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.services.PremiereService;

import java.util.List;

@Controller
public class PremiereController {

    private static PremiereService service;

    @Autowired
    public PremiereController(PremiereService service) {
        this.service = service;
    }

    public void getAllPremiere() {
        List<String> names = service.getAllPremiere();
        for (String name : names) {
            System.out.println(name);
        }
    }

    public void getPremiereInfo(String name) {
        System.out.println(service.getPremiereInfo(name));
    }

    public void createPremiere(Premiere premiere) {
        service.createPremiere(premiere);
    }

    public void changePremiere(Premiere premiere) {
        service.changePremiere(premiere);
    }

    public void deletePremiere(String name) {
        if (service.deletePremiere(name) != null) {
            System.out.println("Premiere " + name + " is deleted");
        } else {
            System.out.println("Not such premiere in DB: " + name);
        }
    }
}
