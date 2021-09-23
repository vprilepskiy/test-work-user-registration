package ru.skblub.sevices.registration.impl.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skblub.sevices.registration.impl.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByConformEmailRequestIdAndConformIsNull(String conformEmailRequestId);

}
