package com.mx.axeleratum.americantower.contract.core.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mx.axeleratum.americantower.contract.core.validation.ValidAutorizaciones;

import lombok.Data;

@Data
@Document(collection = "roles")
public class Rol {

	@Id
	private String id;

	@NotNull(message = "Debe ingresar un nombre de rol")
	@Size(min = 1, message = "Debe ingresar un nombre de rol")
	@Indexed(unique = true)
	private String name;

	private String description;

	@ValidAutorizaciones(message = "Debe seleccionar al menos una autorización en alguna sección")
	Autorizaciones secciones;
}
