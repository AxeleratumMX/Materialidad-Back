package com.mx.axeleratum.americantower.contract.bpm.service;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.repository.ContractTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class ChangeStatusBorradorService implements JavaDelegate {

    @Autowired
    ContractTemplateRepository contractTemplateRepository;

    @Autowired
    KaleidoService kaleidoService;

    @Autowired
    NotificationService notificationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String contractTemplateId = execution.getProcessInstance().getProcessBusinessKey();
        Optional<ContractTemplate> optionalContractTemplate = contractTemplateRepository.findById(contractTemplateId);
        optionalContractTemplate.ifPresent(
                c -> {
                    c.getTemplate().setEstado(ContractStatusType.BORRADOR.toString());
                    contractTemplateRepository.save(c);
                    execution.setVariable("statusKey", c.getTemplate().getEstado());
                    execution.setVariable("status",ContractStatusType.valueOf(c.getTemplate().getEstado()).getContractType());

                }
        );
        execution.removeVariable("okRevision");
        execution.removeVariable("okFirma");
        execution.removeVariable("responseRevisor");
        execution.removeVariable("responseFirma");

        String userAbogadoCreador = (String) execution.getVariable("asssignCreateContractUser");


        //agregar manejo de errores respuesta de kaleido
        kaleidoService.postData(build(userAbogadoCreador,contractTemplateId,ContractStatusType.BORRADOR.toString(),"En Borrador" ,null));

        // Envio Notificacion al usuario creador del contrato
        String cliente = (String)execution.getVariable("cliente");
        String assetNumber = "";
        if (Objects.nonNull(execution.getVariable("assetNumber"))) {
            assetNumber = execution.getVariable("assetNumber").toString();
        }
        String tipoContrato = (String)execution.getVariable("tipoContrato");
        String subTipoContrato = (String)execution.getVariable("subTipoContrato");
        String comment = "Se ha rechazado el contrato: " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato + ", el cual se encuentra en estado Borrador";

        String subject = "Contrato  " + cliente + " | " + assetNumber + " | " + tipoContrato + " | " + subTipoContrato;

        notificationService.sendNotification(userAbogadoCreador,comment,execution);



        log.info("executed actualizanndo Contract:  {} , nuevo estado {}", contractTemplateId,ContractStatusType.BORRADOR.toString());
    }

    private KaleidoRequestDto build(String username, String idContrato, String keyStatus, String status, String comment) {
        KaleidoRequestDto kaleidoRequestDto = new KaleidoRequestDto();
        kaleidoRequestDto.setKeyStatus(keyStatus);
        kaleidoRequestDto.setStatus(status);
        if (Objects.isNull(comment)) {
            kaleidoRequestDto.setComment("Se envía contrato a " + kaleidoRequestDto.getStatus());
        } else {
            kaleidoRequestDto.setComment(comment);
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = dtf.format(now);
        kaleidoRequestDto.setFecha(dateString);
        kaleidoRequestDto.setId(idContrato);
        kaleidoRequestDto.setUser(username);
        return kaleidoRequestDto;


    }

}
