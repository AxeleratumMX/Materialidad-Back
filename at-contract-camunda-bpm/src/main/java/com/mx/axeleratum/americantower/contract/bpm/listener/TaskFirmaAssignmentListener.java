package com.mx.axeleratum.americantower.contract.bpm.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.Objects;

@Slf4j
public class TaskFirmaAssignmentListener implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        log.info("TaskRevisionAssignmentListener.notify: " + delegateTask);
        Boolean response = (Boolean) delegateTask.getExecution().getVariable("responseFirma");
        Boolean okFirma = (Boolean) delegateTask.getVariable("okFirma");
        log.info("response " + response);
        log.info("okFirma " + okFirma);
        if (Objects.nonNull(okFirma) && Objects.nonNull(response)) {
            delegateTask.setVariable("okFirma",(response&&okFirma));
        } else {
            delegateTask.setVariable("okFirma",(response));
        }

    }
}
