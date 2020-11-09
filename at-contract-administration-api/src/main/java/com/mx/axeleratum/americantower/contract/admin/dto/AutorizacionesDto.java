package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class AutorizacionesDto {
    
   private AutorizarIndicadoresDto autorizarIndicadores;
    
    private AutorizarRolesDto autorizarRoles;
    
    private AutorizarDominiosDto autorizarDominios;
    
    private AutorizarInboxDto autorizarInbox;
    
    private AutorizarContratosDto autorizarContratos;
    
    private AutorizarUsuariosDto autorizarUsuarios;   
    
    private AutorizarParamTemplatesDto autorizarParamTemplates;
    
    private AutorizarNotificacionesDto autorizarNotificaciones;
    
    private AutorizarTemplatesDto autorizarTemplates;  
}
