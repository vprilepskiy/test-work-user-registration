package ru.skblub.sevices.registration.impl.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.core.JmsTemplate;
import ru.skblub.sevices.registration.api.service.RegistrationService;
import ru.skblub.sevices.registration.impl.dto.MessageDto;
import ru.skblub.sevices.registration.impl.exception.RegistrationException;
import ru.skblub.sevices.registration.impl.jms.JmsQueueSet;
import ru.skblub.sevices.registration.impl.mapper.UserMapper;
import ru.skblub.sevices.registration.impl.model.UserEntity;
import ru.skblub.sevices.registration.impl.repository.UserRepository;
import ru.skblub.sevices.registration.impl.request.UserRegistrationRequest;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.skblub.sevices.registration.impl.mapper.MessageMapper.toMessage;

@SpringBootTest(classes = RegistrationServiceImplTestConfig.class)
class RegistrationServiceImplTest {

    @Autowired
    RegistrationService registrationService;

    @MockBean
    MessagingServiceStub messagingService;

    @SpyBean
    UserMapper userMapper;

    @MockBean
    JmsTemplate jmsTemplate;

    @MockBean
    UserRepository userRepository;

    @Test
    void registrationUserTestWhenOk() {
        var request = new UserRegistrationRequest();
        request.setFirstName("firstName");
        request.setLogin("login");
        var entity = new UserEntity();
        var id = new Random().nextLong();
        entity.setId(id);
        var messageId = UUID.randomUUID().toString();
        var entityCaptor = ArgumentCaptor.forClass(UserEntity.class);

        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(messagingService.send(any())).thenReturn(messageId);
        when(userRepository.save(entityCaptor.capture())).thenReturn(entity);

        var serviceResult = registrationService.registrationUser(request);

        verify(jmsTemplate).convertAndSend(eq(JmsQueueSet.EMAIL_FOR_CONFORMATION), eq(messageId));
        var entityCaptorValue = entityCaptor.getValue();
        assertThat(entityCaptorValue.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(entityCaptorValue.getLogin()).isEqualTo(request.getLogin());
        assertThat(entityCaptorValue.getConformEmailRequestId()).isEqualTo(messageId);
        assertNull(entityCaptorValue.getConform());
        assertNotNull(serviceResult);
        assertThat(serviceResult).isEqualTo(id);
    }

    @Test
    void registrationUserTestWhenThrowRegistrationException() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(RegistrationException.class, () -> registrationService.registrationUser(new UserRegistrationRequest()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void makeConfirmationUserEmailTestWhenOk() throws TimeoutException {
        var entity = new UserEntity();
        var conformEmailRequestId = UUID.randomUUID().toString();
        entity.setConformEmailRequestId(conformEmailRequestId);

        when(messagingService.receive(any())).thenReturn(toMessage(Boolean.TRUE));
        when(userRepository.findByConformEmailRequestIdAndConformIsNull(eq(conformEmailRequestId))).thenReturn(Optional.of(new UserEntity()));

        registrationService.makeConfirmationUserEmail(conformEmailRequestId);

        verify(userRepository).save(any());
        verify(jmsTemplate).convertAndSend(eq(JmsQueueSet.EMAIL_MESSAGE), any(MessageDto.class));
    }

    @Test
    void makeConfirmationUserEmailTestWhenThrowTimeoutException() throws TimeoutException {
        when(messagingService.receive(any())).thenThrow(new TimeoutException());
        assertThrows(IllegalStateException.class, () -> registrationService.makeConfirmationUserEmail(""));
        verify(userRepository, never()).save(any());
    }

}