package com.mx.axeleratum.americantower.contract.notification;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.KaleidoResponseDto;
import com.mx.axeleratum.americantower.contract.notification.service.KaleidoService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KaleidoServiceTest {
    @Autowired
    KaleidoService kaleidoService;

    @Autowired()
    @Qualifier("encryptorBean")
    StringEncryptor stringEncryptor;


    @Test
    public void testPass() {
        log.info("pass");
    }


    @Test
    public void testPostData() {
        KaleidoRequestDto kaleidoRequestDto = new KaleidoRequestDto();
        kaleidoRequestDto.setComment("coomment");
        kaleidoRequestDto.setFecha("13-07-2020");
        kaleidoRequestDto.setId("Gaby-00b1");
        kaleidoRequestDto.setKeyStatus("BORRADOR");
        kaleidoRequestDto.setStatus("BORRADOR");
        kaleidoRequestDto.setUser("TEST");
        KaleidoResponseDto kaleidoResponseDto = kaleidoService.postData(kaleidoRequestDto);
        log.info("sali");
    }

    @Test
    public void testEncript() {
        String pass = "1jCeGYtwCiJyRLGcA8OiNeT9bb_SjSW2yKbihOfLhe8";
        String valor = stringEncryptor.encrypt(pass);
        log.info(valor);
    }

/*
    _comments: "post",
    _dateNotify: "13-07-2020",
    _dateResponse: "15-07-2020",
    _description: "post test",
    _statusContract: "En Revision",
    _user : "Javier",
    _uuid: "50201"

*/




}
