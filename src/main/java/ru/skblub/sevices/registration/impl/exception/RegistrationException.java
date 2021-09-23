package ru.skblub.sevices.registration.impl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }

}
