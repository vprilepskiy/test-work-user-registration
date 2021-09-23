package ru.skblub.sevices.registration.impl.dto;

import java.io.Serializable;

/**
 * Сообщение на электронную почту
 */
public class MessageDto implements Serializable {

    public MessageDto() {
    }

    public MessageDto(String email, String messageBody) {
        this.email = email;
        this.messageBody = messageBody;
    }

    /**
     * Электронный адрес назначения
     */
    private String email;

    /**
     * Текст сообщения
     */
    private String messageBody;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
