package ru.skblub.sevices.registration.impl.jms;

public class JmsQueueSet {

    /**
     * Очередь для отправки сообщений для подтверждения электронной почты пользователя внешней системой
     */
    public static final String EMAIL_FOR_CONFORMATION = "EMAIL_FOR_CONFORMATION";

    /**
     * Очередь для отправки сообщений на почту
     */
    public static final String EMAIL_MESSAGE = "EMAIL_MESSAGE";

}
