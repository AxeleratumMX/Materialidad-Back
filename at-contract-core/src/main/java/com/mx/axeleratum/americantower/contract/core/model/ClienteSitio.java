package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientesitios")
public class ClienteSitio {
    @Id
    String id;
    String idCliente;
    String idActivo;
}
