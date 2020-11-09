package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.KaleidoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "AT-NOTIFICATIONS-API")
@Service
public interface KaleidoServiceFeign {

    String AUTH_TOKEN = "Authorization";

    @RequestMapping(value ="/kaleido/init" , method = RequestMethod.POST)
    public KaleidoResponseDto posData(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody KaleidoRequestDto kaleidoRequestDto);

}
