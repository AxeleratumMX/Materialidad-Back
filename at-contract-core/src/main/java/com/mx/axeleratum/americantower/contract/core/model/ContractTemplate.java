package com.mx.axeleratum.americantower.contract.core.model;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "contractstemplates")
public class ContractTemplate {
    @Id
    private String	id;
	private String  idContract;
	private String  processInstanceId;
	private Template template;
	private List<Contact> revisores;
	private List<Contact> firmantes;
	@LastModifiedDate
    private Date lastModifiedDate;
    @CreatedDate
    private Date createdDate;

    public Map<String,ContractValueTemplate> listToMapContractValue() {
        Map<String,ContractValueTemplate> map = Maps.uniqueIndex(template.getValues(), ContractValueTemplate::getId);
        return map;
    }
}
