package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

@Data
public class Autorizaciones {
    
   private AutorizarIndicadores autorizarIndicadores;
    
    private AutorizarRoles autorizarRoles;
    
    private AutorizarDominios autorizarDominios;
    
    private AutorizarInbox autorizarInbox;
    
    private AutorizarContratos autorizarContratos;
    
    private AutorizarUsuarios autorizarUsuarios;   
    
    private AutorizarParamTemplates autorizarParamTemplates;
    
    private AutorizarNotificaciones autorizarNotificaciones;
    
    private AutorizarTemplates autorizarTemplates;  
}
