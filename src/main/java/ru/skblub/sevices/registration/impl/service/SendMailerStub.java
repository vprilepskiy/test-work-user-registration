package ru.skblub.sevices.registration.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skblub.sevices.registration.api.service.SendMailer;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class SendMailerStub implements SendMailer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailerStub.class);

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }

    @Override
    public void sendMail(String toAddress, String messageBody) throws TimeoutException {
        if(shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        LOGGER.info("Send mail from={}, body={}", toAddress, messageBody);
    }
}
