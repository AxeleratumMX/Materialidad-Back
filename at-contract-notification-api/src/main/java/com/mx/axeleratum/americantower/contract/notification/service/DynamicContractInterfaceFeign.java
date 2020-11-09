package com.mx.axeleratum.americantower.contract.notification.service;

import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.notification.dto.DynamicContractDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "AT-DYNAMICINTERFACE-API")
@Service
public interface DynamicContractInterfaceFeign {
    @RequestMapping(value = "/templates/contract/partial/{idContractTemplate}", method = RequestMethod.GET)
    public DynamicContractDto findDynamicContract(@PathVariable("idContractTemplate") String idContractTemplate);

    @RequestMapping(value ="/templates/contract/{id}/status2/{status}" , method = RequestMethod.PUT)
    public void changeStatus( @PathVariable("id") String id, @PathVariable("status") ContractStatusType status);

}
