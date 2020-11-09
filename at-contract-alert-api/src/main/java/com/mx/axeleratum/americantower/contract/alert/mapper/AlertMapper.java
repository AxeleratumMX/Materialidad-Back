package com.mx.axeleratum.americantower.contract.alert.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mx.axeleratum.americantower.contract.alert.dto.AlertDto;
import com.mx.axeleratum.americantower.contract.alert.dto.AlertDtoEntry;
import com.mx.axeleratum.americantower.contract.alert.model.Alert;

@Mapper(componentModel = "spring")
public interface AlertMapper {

	@Mapping(source = "createdDate", target = "createdDate")
	AlertDto toAlertDto(Alert notification);

	@InheritInverseConfiguration
	Alert toNotification(AlertDto notificationDto);
	
	Alert fromEntryToDto(AlertDtoEntry alertEntryDto);

	List<AlertDto> toAlertDtoList(Collection<Alert> alerts);

	List<Alert> toNotificationList(Collection<AlertDto> alertsDtos);

	List<Alert> fromEntryToAlertList(Collection<AlertDtoEntry> alertsDto);
	
}
