package ru.skblub.sevices.registration.impl.service;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.skblub.sevices.registration.api.service.MessagingService;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static ru.skblub.sevices.registration.impl.mapper.MessageMapper.toMessage;

@Component
public class MessagingServiceStub implements MessagingService<String, Boolean> {

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
    public String send(Message<String> msg) {
        return UUID.randomUUID().toString();
    }

    @Override
    public Message<Boolean> receive(String messageId) throws TimeoutException {
        if(shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        return toMessage(Boolean.TRUE);
    }

}
