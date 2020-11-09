package com.mx.axeleratum.americantower.contract.bpm.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class SendContratoActivarService implements JavaDelegate {

    @Autowired
    EmailService emailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("executed sendContratoActivarService: {}", execution);

        String cliente = (String)execution.getVariable("cliente");
        String assetNumber = "";
        if (Objects.nonNull(execution.getVariable("assetNumber"))) {
            assetNumber = execution.getVariable("assetNumber").toString();
        }
        String tipoContrato = (String)execution.getVariable("tipoContrato");
        String subTipoContrato = (String)execution.getVariable("subTipoContrato");
        String body = "El contrato: " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato + " se encuentra en estado Activo.";
        String subject = "Contrato  " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato;

        emailService.sendSimpleMessage("gforrade@gmail.com", subject ,body);


    }
}
