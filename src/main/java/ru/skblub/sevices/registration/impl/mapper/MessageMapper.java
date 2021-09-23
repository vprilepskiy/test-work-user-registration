package ru.skblub.sevices.registration.impl.mapper;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class MessageMapper {

    public static <T> Message<T> toMessage(T message) {
        return new Message<T>() {
            @Override
            public T getPayload() {
                return message;
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };
    }

}
