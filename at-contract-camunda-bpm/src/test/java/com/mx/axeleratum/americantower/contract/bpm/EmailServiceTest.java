package com.mx.axeleratum.americantower.contract.bpm;

import com.mx.axeleratum.americantower.contract.bpm.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testSendEmail() {
        emailService.sendSimpleMessage("gforrade@gmail.com","Algo para ver si lega" , "viri viri viri");
        log.info("envie");
    }
}
