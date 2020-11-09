package com.mx.axeleratum.americantower.contract.admin.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.admin.dto.UserDto;
import com.mx.axeleratum.americantower.contract.admin.dto.UserPhotoDto;
import com.mx.axeleratum.americantower.contract.core.model.UserPhoto;
import com.mx.axeleratum.americantower.contract.core.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

	List<UserDto> toListUserDto(List<User> users);

	List<UserPhotoDto> toListUserPhotoDto(List<UserPhoto> photos);
}
