package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "templates")
public class Template {
	@Id
	private String  idTemplate;
	private String  clientId;
	private String  tipoContrato;
	
	@Indexed(unique=true)
	private String  name;
	private String  abstractType;
	private String  leaseType;
	private String  acuerdo;
	private String  estado;
	private String  content;
	private String  tipoContratoOracle;
	private String  subTipoContratoOracle;
	private Integer idActivo;
	private Integer version;
	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date lastModifiedDate;

	List<ContractValueTemplate> values;
	List<SectionTemplate> sections;

}
