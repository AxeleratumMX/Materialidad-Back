package com.mx.axeleratum.americantower.contract.notification.mapper;

import com.mx.axeleratum.americantower.contract.core.model.Notification;
import com.mx.axeleratum.americantower.contract.notification.dto.NotificationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

	NotificationDto toNotificationDto(Notification notification);

	@InheritInverseConfiguration
	Notification toNotification(NotificationDto notificationDto);	

	List<NotificationDto> toNotificationDtoList(Collection<Notification> notifications);	
	
	List<Notification> toNotificationList(Collection<NotificationDto> notificationDtos);

	Notification cloneNotification(Notification notification);

}
