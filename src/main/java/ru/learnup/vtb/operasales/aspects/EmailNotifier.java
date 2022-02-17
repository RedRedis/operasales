package ru.learnup.vtb.operasales.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.learnup.vtb.operasales.entities.Premiere;
import ru.learnup.vtb.operasales.entities.Ticket;
import ru.learnup.vtb.operasales.services.PremiereService;
import ru.learnup.vtb.operasales.services.TicketService;

@Aspect
@Component
public class EmailNotifier {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.to}")
    private String to;

    @Autowired
    public EmailNotifier(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Pointcut("@annotation(ru.learnup.vtb.operasales.annotations.Email)")
    public void emailNotifierService() {}

    @Around("emailNotifierService()")
    public void sendEmail(ProceedingJoinPoint point) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);

        if (point.getSignature().getDeclaringType().equals(TicketService.class)) {
            byeTicket(point, message);
        } else if (point.getSignature().getDeclaringType().equals(PremiereService.class)) {
            premiere(point, message);
        }
        if (message.getText() != null) {
            mailSender.send(message);
        }
    }

    private void byeTicket(ProceedingJoinPoint point, SimpleMailMessage message) {
        try {
            Ticket ticket = (Ticket) point.proceed();
            message.setSubject("Buy ticket");
            message.setText("Ticket number: " + ticket.getNumber() + ", premiere: " + ticket.getNameOfPremiere());
        } catch (Throwable e) {}
    }

    private void premiere(ProceedingJoinPoint point, SimpleMailMessage message) {
        try {
            Premiere premiere = (Premiere) point.proceed();
            message.setSubject("Premiere Info");
            message.setText(premiere.toString());
        }
        catch (Throwable e) {}
    }
}
