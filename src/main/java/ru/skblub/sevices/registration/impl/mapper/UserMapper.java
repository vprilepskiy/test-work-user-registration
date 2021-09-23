package ru.skblub.sevices.registration.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skblub.sevices.registration.impl.model.UserEntity;
import ru.skblub.sevices.registration.impl.request.UserRegistrationRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conformEmailRequestId", ignore = true)
    @Mapping(target = "conform", ignore = true)
    UserEntity toEntity(UserRegistrationRequest request);
    //TODO: прикрутить BCryptPasswordEncoder для хранения шифрованных пролей в БД

}
