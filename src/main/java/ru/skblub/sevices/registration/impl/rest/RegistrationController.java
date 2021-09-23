package ru.skblub.sevices.registration.impl.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.skblub.sevices.registration.api.service.RegistrationService;
import ru.skblub.sevices.registration.impl.request.UserRegistrationRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api")
@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/echo")
    public String echo() {
        return "echo";
    }

    @Operation(description = "Регистрация нового пользователя")
    @PostMapping(path = "/registration-user", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Long registrationUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Запрос на регистрацию нового пользователя", required = true)
            @RequestBody UserRegistrationRequest request
    ) {
        return registrationService.registrationUser(request);
    }

}
