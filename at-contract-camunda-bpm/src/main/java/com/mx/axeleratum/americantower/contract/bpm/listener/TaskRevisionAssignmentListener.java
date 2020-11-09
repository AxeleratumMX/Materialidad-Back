package com.mx.axeleratum.americantower.contract.bpm.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.Objects;

@Slf4j
public class TaskRevisionAssignmentListener implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        log.info("TaskRevisionAssignmentListener.notify: " + delegateTask);
        Boolean response = (Boolean) delegateTask.getExecution().getVariable("responseRevisor");
        Boolean okRevision = (Boolean) delegateTask.getVariable("okRevision");
        log.info("response " + response);
        log.info("okRevision " + okRevision);
        if (Objects.nonNull(okRevision) && Objects.nonNull(response)) {
            delegateTask.setVariable("okRevision",(response&&okRevision));
        } else {
            delegateTask.setVariable("okRevision",(response));
        }

    }
}
