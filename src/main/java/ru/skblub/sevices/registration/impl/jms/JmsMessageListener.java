package ru.skblub.sevices.registration.impl.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.skblub.sevices.registration.api.service.RegistrationService;
import ru.skblub.sevices.registration.api.service.SendMailer;
import ru.skblub.sevices.registration.impl.dto.MessageDto;

import java.util.concurrent.TimeoutException;

@Transactional(propagation = Propagation.REQUIRED)
@Component
public class JmsMessageListener {

    private final RegistrationService registrationService;
    private final SendMailer sendMailer;

    public JmsMessageListener(RegistrationService registrationService, SendMailer sendMailer) {
        this.registrationService = registrationService;
        this.sendMailer = sendMailer;
    }

    @JmsListener(destination = JmsQueueSet.EMAIL_FOR_CONFORMATION)
    private void callMakeConfirmationUserEmail(String conformEmailRequestId) {
        registrationService.makeConfirmationUserEmail(conformEmailRequestId);
    }

    @JmsListener(destination = JmsQueueSet.EMAIL_MESSAGE)
    private void callMakeConfirmationUserEmail(MessageDto message) {
        try {
            sendMailer.sendMail(message.getEmail(), message.getMessageBody());
        } catch (TimeoutException e) {
            throw new IllegalStateException("Error sending email", e);
        }
    }

}
