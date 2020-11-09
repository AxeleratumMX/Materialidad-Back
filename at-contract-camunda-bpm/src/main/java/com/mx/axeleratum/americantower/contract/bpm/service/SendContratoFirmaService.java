package com.mx.axeleratum.americantower.contract.bpm.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Component
public class SendContratoFirmaService implements JavaDelegate {

    @Autowired
    EmailService emailService;

    @Autowired
    NotificationService notificationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("executed sendContratoFirmaService: {}", execution);

        //todo obtener los contactos desde contract
        ArrayList<String> assigneeList = new ArrayList();
        assigneeList.add("userFirmante");
        assigneeList.add("userFirmante2");
        execution.setVariable("assigneeFirmantesList",assigneeList);


        String cliente = (String)execution.getVariable("cliente");
        String assetNumber = "";
        if (Objects.nonNull(execution.getVariable("assetNumber"))) {
            assetNumber = execution.getVariable("assetNumber").toString();
        }
        String tipoContrato = (String)execution.getVariable("tipoContrato");
        String subTipoContrato = (String)execution.getVariable("subTipoContrato");
        String comment = "Se ha enviado el contrato: " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato + " para su Firma";

        String subject = "Contrato  " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato;

        notificationService.sendNotification("userFirmante",comment,execution);
        notificationService.sendNotification("userFirmante2",comment,execution);
        emailService.sendSimpleMessage("gforrade@gmail.com", subject ,comment);


    }
}
