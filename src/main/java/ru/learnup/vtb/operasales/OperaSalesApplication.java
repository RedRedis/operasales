package ru.learnup.vtb.operasales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.vtb.operasales.controllers.PremiereController;
import ru.learnup.vtb.operasales.controllers.TicketController;
import ru.learnup.vtb.operasales.entities.Premiere;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

@SpringBootApplication
public class OperaSalesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OperaSalesApplication.class, args);

        //1
        ctx.getBean(PremiereController.class).getAllPremiere();
        ctx.getBean(PremiereController.class).createPremiere(new Premiere("Flowers", "About Flowers", 15, 400, new HashSet<>()));
        System.out.println();
        ctx.getBean(PremiereController.class).getAllPremiere();

        //2
        System.out.println();
        ctx.getBean(PremiereController.class).getPremiereInfo("Flowers");
        ctx.getBean(PremiereController.class).changePremiere(new Premiere("Flowers", null, null, 1000, null));
        ctx.getBean(PremiereController.class).getPremiereInfo("Flowers");

        //3
        System.out.println();
        ctx.getBean(PremiereController.class).deletePremiere("Flowers");
        ctx.getBean(PremiereController.class).deletePremiere("Flowers");

        //4
        System.out.println();
        ctx.getBean(PremiereController.class).getAllPremiere();
        ctx.getBean(PremiereController.class).getPremiereInfo("Spider");
        ctx.getBean(PremiereController.class).getPremiereInfo("Dogs");

        //5
        System.out.println();
        ctx.getBean(TicketController.class).buyTicket("Iron tank");
        ctx.getBean(TicketController.class).buyTicket("Spider");
        ctx.getBean(PremiereController.class).getPremiereInfo("Spider");

        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input number of ticket for return:");
        int number = scanner.nextInt();
        System.out.println("Input name of premiere for return ticket:");
        String name = scanner.next();
        ctx.getBean(TicketController.class).returnTicket(number, name);
        ctx.getBean(PremiereController.class).getPremiereInfo("Spider");
    }

}
