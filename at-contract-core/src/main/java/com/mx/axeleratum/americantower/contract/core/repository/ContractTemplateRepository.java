package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractTemplateRepository extends MongoRepository<ContractTemplate,String> {

    @Query(value="{ 'id' : ?0 }", fields="{ 'id' : 1, 'idContract' : 1 , 'template.clientId' : 1 , 'template.tipoContrato' : 1 , 'template.estado' : 1}")
    Optional<ContractTemplate> findPartialContractTemplateById(String id);
    
    List<ContractTemplate> findByTemplateTipoContrato(String tipoContrato);
}
