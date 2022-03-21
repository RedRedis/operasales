package ru.learnup.vtb.operasales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.vtb.operasales.controllers.PremiereController;
import ru.learnup.vtb.operasales.controllers.TicketController;
import ru.learnup.vtb.operasales.repositories.entities.PremiereEntity;

import java.util.Scanner;

@SpringBootApplication
public class OperaSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperaSalesApplication.class, args);
    }

}
