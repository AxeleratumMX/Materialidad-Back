package com.mx.axeleratum.americantower.contract.notification.mapper;


import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.model.Notification;
import org.mapstruct.Mapper;

//import com.mx.axeleratum.americantower.contract.notification.dto.NotificationEntryDto;

@Mapper(componentModel = "spring")
public interface KaleidoMapper {

/*
	@Mapping( target = "userSenderId", source = "sender.id")
	@Mapping( target = "userReceiverId", source = "receiver.id")
*/
	KaleidoRequestDto toKaleidoRequestDto(Notification notification);


}


//	private String comments;
//	private String dateNotify;
//	private String dateResponse;
//	private String description;
//	private String statusContract;
//	private String user;
//	private String uuid;

//	private String id;
//	private String contractTemplateId;
//	private String description;
//	private String assetId;
//	private String folio;
//	private String client;
//	private String tipoContrato;
//	private String subTipoContrato;
//	private Boolean notify;
//	private Boolean reminder;
//	private String contractStatus;
