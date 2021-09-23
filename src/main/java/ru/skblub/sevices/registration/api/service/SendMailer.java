package ru.skblub.sevices.registration.api.service;

import java.util.concurrent.TimeoutException;

/**
 * Ориентировочный интерфейс мейлера.
 */
public interface SendMailer {

    void sendMail(String toAddress, String messageBody) throws TimeoutException;

}
