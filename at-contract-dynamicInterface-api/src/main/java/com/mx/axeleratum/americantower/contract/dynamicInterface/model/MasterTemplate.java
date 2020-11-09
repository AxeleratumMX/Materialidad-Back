package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.ContractValueTemplate;
import com.mx.axeleratum.americantower.contract.core.model.SectionTemplate;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@QueryEntity
@Document(collection = "mastertemplates")
public class MasterTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2880282170624393825L;
	@Id
	private String  id;
	@Indexed(unique=true)
	private String  tipo;
	private String  nameTemplate;
	List<ContractValueTemplate> values;
	List<SectionTemplate> sections;
}
