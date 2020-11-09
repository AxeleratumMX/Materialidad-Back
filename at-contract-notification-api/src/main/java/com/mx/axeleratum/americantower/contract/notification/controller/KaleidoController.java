package com.mx.axeleratum.americantower.contract.notification.controller;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.KaleidoResponseDto;
import com.mx.axeleratum.americantower.contract.notification.service.KaleidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kaleido")
@Api(tags = "Kaleido Controller Methods", description = "Kaleido Controller")
@Slf4j
public class KaleidoController {

	@Autowired
	KaleidoService kaleidoService;

	@PostMapping("/init")
	@ApiOperation(value = "Response a  Notification", notes = "Response the notification created", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public KaleidoResponseDto postData(@RequestBody KaleidoRequestDto kaleidoRequestDto) {
		return kaleidoService.postData(kaleidoRequestDto);
	}



}
