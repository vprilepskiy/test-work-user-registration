package ru.skblub.sevices.registration.api.service;

import ru.skblub.sevices.registration.impl.request.UserRegistrationRequest;

/**
 * Сервис регистрации пользователей
 */
public interface RegistrationService {

    /**
     * Регистрация нового пользователя
     *
     * @param request Запрос на регистрацию нового пользователя
     * @return Идентификатор пользователя
     */
    Long registrationUser(UserRegistrationRequest request);

    /**
     * Проверяет подтверждение электронной почты пользователя и отправляет письма с результатом проверки.
     */
    void makeConfirmationUserEmail(String conformEmailRequestId);

}
