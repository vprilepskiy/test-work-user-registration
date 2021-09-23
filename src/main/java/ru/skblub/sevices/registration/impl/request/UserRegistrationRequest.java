package ru.skblub.sevices.registration.impl.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * Запрос на регистрацию нового пользователя
 */
@Schema(description = "Запрос на регистрацию нового пользователя")
public class UserRegistrationRequest implements Serializable {

    /**
     * Логин
     */
    @Schema(description = "Логин", minLength = 1, maxLength = 255, required = true)
    private String login;

    /**
     * Пароль
     */
    @Schema(description = "Пароль", minLength = 1, maxLength = 255, required = true)
    private String password;

    /**
     * Адрес электронной почты
     */
    @Schema(description = "Адрес электронной почты", minLength = 1, maxLength = 255, required = true)
    private String email;

    /**
     * Фамилия
     */
    @Schema(description = "Фамилия", minLength = 1, maxLength = 255, required = true)
    private String lastName;

    /**
     * Имя
     */
    @Schema(description = "Имя", minLength = 1, maxLength = 255, required = true)
    private String firstName;

    /**
     * Отчество
     */
    @Schema(description = "Отчество", maxLength = 255)
    private String middleName;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
