package com.mx.axeleratum.americantower.contract.notification.util;

import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.notification.dto.TaskDto;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class BuilderDtoUtil {
    @Builder(builderMethodName = "builderTaskDtp")
    public static TaskDto newTaskDto(LinkedHashMap linkedHashMap,Map<String,LinkedHashMap> variablesMap) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(linkedHashMap.get("id").toString());
        taskDto.setProcessInstanceId(linkedHashMap.get("processInstanceId").toString());
        taskDto.setFecha(linkedHashMap.get("created").toString());
        taskDto.setContractId(getStringValue(variablesMap.get("contractId")));
        taskDto.setAssetNumber(getStringValue(variablesMap.get("assetNumber")));
        taskDto.setCliente(getStringValue(variablesMap.get("cliente")));
        taskDto.setFolio(getStringValue(variablesMap.get("folio")));
        taskDto.setContractStatusKey(getStringValue(variablesMap.get("statusKey")));
        taskDto.setTipoContrato(getStringValue(variablesMap.get("tipoContrato")));
        taskDto.setSubTipoContrato(getStringValue(variablesMap.get("subTipoContrato")));
        taskDto.setAsssignCreateContractUser(getStringValue(variablesMap.get("asssignCreateContractUser")));
        taskDto.setContractStatus(getContractStatus(taskDto.getContractStatusKey()));

        return taskDto;
    }

    private static String getStringValue(LinkedHashMap map) {
        if (Objects.nonNull(map)) {
            return map.get("value").toString();
        } else
        return "";
    }

    private static String getContractStatus(String statusKey) {
        if (Objects.isNull(statusKey)|| statusKey.equals("")) return "";
        try {
            return ContractStatusType.valueOf(statusKey).getContractType();
        } catch (Exception ex) {
            log.error("Valor de statuskey no encontrado para key {} ",statusKey);
            return "";
        }
    }
}
