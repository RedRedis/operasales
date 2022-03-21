package ru.learnup.vtb.operasales.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.learnup.vtb.operasales.model.Premiere;
import ru.learnup.vtb.operasales.model.Ticket;
import ru.learnup.vtb.operasales.repositories.entities.PremiereEntity;
import ru.learnup.vtb.operasales.repositories.entities.TicketEntity;
import ru.learnup.vtb.operasales.services.PremiereService;
import ru.learnup.vtb.operasales.services.TicketService;

@Aspect
@Component
public class EmailNotifier {

    private JavaMailSender mailSender;
    private String from;
    private String to;

    @Autowired
    public EmailNotifier(JavaMailSender mailSender, @Value("${spring.mail.username}") String from, @Value("${spring.to}") String to) {
        this.mailSender = mailSender;
        this.from = from;
        this.to = to;
    }

    @Pointcut("@annotation(ru.learnup.vtb.operasales.annotations.Email)")
    public void emailNotifierService() {}

    @Around("emailNotifierService()")
    public Object sendEmail(ProceedingJoinPoint point) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);

        try {
            Object object = point.proceed();
            if (point.getSignature().getDeclaringType().equals(TicketService.class)) {
                byeTicket(object, message);
            } else if (point.getSignature().getDeclaringType().equals(PremiereService.class)) {
                premiere(object, message);
            }
            if (message.getText() != null) {
                mailSender.send(message);
            }
            return object;
        }
        catch (Throwable e) {
            return null;
        }
    }

    private void byeTicket(Object object, SimpleMailMessage message) {
        Ticket ticket = (Ticket) object;
        message.setSubject("Buy ticket");
        message.setText("Ticket number: " + ticket.getNumber() + ", premiere: " + ticket.getPremiere().getName());
    }

    private void premiere(Object object, SimpleMailMessage message) {
        Premiere premiere = (Premiere) object;
        message.setSubject("Premiere Info");
        message.setText(premiere.toString());
    }
}
