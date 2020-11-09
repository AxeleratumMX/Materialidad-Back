package com.mx.axeleratum.americantower.contract.bpm.service;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.core.repository.ContractTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ChangeStatusActivoService implements JavaDelegate {
    @Autowired
    ContractTemplateRepository contractTemplateRepository;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String contractTemplateId = execution.getProcessInstance().getProcessBusinessKey();
        Optional<ContractTemplate> optionalContractTemplate = contractTemplateRepository.findById(contractTemplateId);
        optionalContractTemplate.ifPresent(
                c -> {
                    c.getTemplate().setEstado(ContractStatusType.ACTIVO.toString());
                    contractTemplateRepository.save(c);
                    execution.setVariable("statusKey", c.getTemplate().getEstado());
                    execution.setVariable("status",ContractStatusType.valueOf(c.getTemplate().getEstado()).getContractType());

                }
        );

        log.info("executed actualizanndo Contract:  {} , nuevo estado {}", contractTemplateId,ContractStatusType.ACTIVO.toString());
    }
}
