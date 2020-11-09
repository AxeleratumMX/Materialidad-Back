package com.mx.axeleratum.americantower.contract.bpm.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class InformarAprobacionRevisionService implements JavaDelegate {
    @Autowired
    NotificationService notificationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("executed informarAprobacionRevisionService: {}", execution);

        String userAbogadoCreador = (String) execution.getVariable("asssignCreateContractUser");
        // Envio Notificacion al usuario creador del contrato
        String cliente = (String)execution.getVariable("cliente");
        String assetNumber = "";
        if (Objects.nonNull(execution.getVariable("assetNumber"))) {
            assetNumber = execution.getVariable("assetNumber").toString();
        }
        String tipoContrato = (String)execution.getVariable("tipoContrato");
        String subTipoContrato = (String)execution.getVariable("subTipoContrato");
        String comment = "Se ha aprobado el contrato: " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato + ", el cual se encuentra en estado Revision";

        String subject = "Contrato  " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato;

        notificationService.sendNotification(userAbogadoCreador,comment,execution);

    }
}
