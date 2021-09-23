package ru.skblub.sevices.registration.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.skblub.sevices.registration.api.service.RegistrationService;
import ru.skblub.sevices.registration.impl.dto.MessageDto;
import ru.skblub.sevices.registration.impl.exception.RegistrationException;
import ru.skblub.sevices.registration.impl.jms.JmsQueueSet;
import ru.skblub.sevices.registration.impl.mapper.UserMapper;
import ru.skblub.sevices.registration.impl.repository.UserRepository;
import ru.skblub.sevices.registration.impl.request.UserRegistrationRequest;

import java.util.concurrent.TimeoutException;

import static ru.skblub.sevices.registration.impl.mapper.MessageMapper.toMessage;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final UserRepository userRepository;
    private final MessagingServiceStub messagingService;
    private final UserMapper userMapper;
    private final JmsTemplate jmsTemplate;

    public RegistrationServiceImpl(UserRepository userRepository, MessagingServiceStub messagingService, UserMapper userMapper, JmsTemplate jmsTemplate) {
        this.userRepository = userRepository;
        this.messagingService = messagingService;
        this.userMapper = userMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Long registrationUser(UserRegistrationRequest request) {
        checkUniqueLogin(request.getLogin());
        var conformEmailRequestId = messagingService.send(toMessage(request.getEmail()));
        var userEntity = userMapper.toEntity(request);
        userEntity.setConformEmailRequestId(conformEmailRequestId);
        userEntity = userRepository.save(userEntity);
        jmsTemplate.convertAndSend(JmsQueueSet.EMAIL_FOR_CONFORMATION, conformEmailRequestId);
        LOGGER.info("Saved user with id={}", userEntity.getId());
        return userEntity.getId();
    }

    @Override
    public void makeConfirmationUserEmail(String conformEmailRequestId) {
        Boolean isConform = null;
        try {
            isConform = (Boolean)messagingService.receive(conformEmailRequestId).getPayload();
        } catch (TimeoutException e) {
            throw new IllegalStateException("Response timeout from the external system", e);
        }
        var userEntity = userRepository.findByConformEmailRequestIdAndConformIsNull(conformEmailRequestId)
                .orElseThrow(() -> new IllegalStateException("User with conformEmailRequestId=" + conformEmailRequestId + " not found"));
        userEntity.setConform(isConform);
        jmsTemplate.convertAndSend(JmsQueueSet.EMAIL_MESSAGE, new MessageDto(userEntity.getEmail(), isConform ? "Mail confirmed" : "Mail not confirmed"));
        userRepository.save(userEntity);
        LOGGER.info("Conformed email user with id={}", userEntity.getId());
    }

    private void checkUniqueLogin(String login) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new RegistrationException("User with this username already exists");
        }
    }
}
