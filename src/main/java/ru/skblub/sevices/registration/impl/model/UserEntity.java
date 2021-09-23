package ru.skblub.sevices.registration.impl.model;

import javax.persistence.*;

/**
 * Пользователь
 */
@Entity
@Table(name = "USER")
public class UserEntity {

    /**
     * Идентификатор (первичный ключ)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Логин
     */
    @Column(nullable = false, unique = true)
    private String login;

    /**
     * Пароль (хэш)
     */
    @Column(nullable = false)
    private String password;

    /**
     * Адрес электронной почты
     */
    @Column(nullable = false)
    private String email;

    /**
     * Идентификатор запроса подтверждения адреса электронной почты внешней системой
     */
    @Column(nullable = false)
    private String conformEmailRequestId;

    /**
     * Признак подтверждения адреса электронной почты внешней системой
     */
    @Column
    private Boolean conform;

    /**
     * Фамилия
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Имя
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Отчество
     */
    @Column
    private String middleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getConformEmailRequestId() {
        return conformEmailRequestId;
    }

    public void setConformEmailRequestId(String conformRequestId) {
        this.conformEmailRequestId = conformRequestId;
    }

    public Boolean getConform() {
        return conform;
    }

    public void setConform(Boolean conform) {
        this.conform = conform;
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
