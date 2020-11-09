package com.mx.axeleratum.americantower.contract.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mx.axeleratum.americantower.contract.core.model.Autorizaciones;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarContratos;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarDominios;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarInbox;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarIndicadores;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarNotificaciones;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarParamTemplates;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarRoles;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarTemplates;
import com.mx.axeleratum.americantower.contract.core.model.AutorizarUsuarios;

public class ValidAutorizacionesValidator implements ConstraintValidator<ValidAutorizaciones, Autorizaciones> {

	@Override
	public boolean isValid(Autorizaciones autorizaciones, ConstraintValidatorContext context) {
		if(autorizaciones==null) {
				return false;
		}
		AutorizarContratos contratos = autorizaciones.getAutorizarContratos();
		AutorizarIndicadores indicadores = autorizaciones.getAutorizarIndicadores();
		AutorizarRoles roles = autorizaciones.getAutorizarRoles();
		AutorizarDominios dominios = autorizaciones.getAutorizarDominios();
		AutorizarInbox inbox = autorizaciones.getAutorizarInbox();
		AutorizarUsuarios usuarios = autorizaciones.getAutorizarUsuarios();
		AutorizarParamTemplates paramTemplates = autorizaciones.getAutorizarParamTemplates();
		AutorizarNotificaciones notificaciones = autorizaciones.getAutorizarNotificaciones();
		AutorizarTemplates templates = autorizaciones.getAutorizarTemplates();
		
		if(contratos==null && indicadores==null && roles==null && dominios==null && inbox==null 
			&& usuarios==null && paramTemplates==null && notificaciones==null && templates==null) {
			return false;
		}
		
		Boolean autorizarContratos = (contratos==null)?false:(Boolean.TRUE.equals(contratos.getCancelar())
				|| Boolean.TRUE.equals (contratos.getCrear()) || Boolean.TRUE.equals(contratos.getDescargar())
				|| Boolean.TRUE.equals(contratos.getListar()) || Boolean.TRUE.equals(contratos.getVer())
				|| Boolean.TRUE.equals(contratos.getVerHistorial())
				|| Boolean.TRUE.equals(contratos.getVerTrazabilidad()));

		Boolean utorizarIndicadores =  (indicadores==null)?false:(Boolean.TRUE.equals(indicadores.getGraficaVGeneral())
				|| Boolean.TRUE.equals(indicadores.getGraficaFirmados())
				|| Boolean.TRUE.equals(indicadores.getGraficaCancelados())
				|| Boolean.TRUE.equals(indicadores.getUltimosActivos())
				|| Boolean.TRUE.equals(indicadores.getUltimosFirma())
				|| Boolean.TRUE.equals(indicadores.getUltimosRevision())
				|| Boolean.TRUE.equals(indicadores.getUltimosCancelados()));

		Boolean utorizarRoles = (roles==null)?false:(Boolean.TRUE.equals(roles.getCrear()) 
				|| Boolean.TRUE.equals(roles.getEditar())
				|| Boolean.TRUE.equals(roles.getEliminar()));

		Boolean autorizarDominios = (dominios==null)?false:(Boolean.TRUE.equals(dominios.getCrear())
				|| Boolean.TRUE.equals(dominios.getEditar()) 
				|| Boolean.TRUE.equals(dominios.getEliminar()));

		Boolean autorizarInbox = (inbox==null)?false:(Boolean.TRUE.equals(inbox.getResponderMensajes())
				|| Boolean.TRUE.equals(inbox.getVerContratos()) 
				|| Boolean.TRUE.equals(inbox.getVerMensajes()));

		Boolean autorizarUsuarios = (usuarios==null)?false:(Boolean.TRUE.equals(usuarios.getEditar())
				|| Boolean.TRUE.equals(usuarios.getCrear()) 
				|| Boolean.TRUE.equals(usuarios.getEliminar())
				|| Boolean.TRUE.equals(usuarios.getListar()) 
				|| Boolean.TRUE.equals(usuarios.getVer()));

		Boolean autorizarParamTemplates = (paramTemplates==null)?false:(Boolean.TRUE.equals(paramTemplates.getVer()));

		Boolean autorizarNotificaciones = (paramTemplates==null)?false:(Boolean.TRUE.equals(notificaciones.getVer()));

		Boolean autorizarTemplates = (templates==null)?false:(Boolean.TRUE.equals(templates.getCrear())
				|| Boolean.TRUE.equals(templates.getEditar()) 
				|| Boolean.TRUE.equals(templates.getListar())
				|| Boolean.TRUE.equals(templates.getVer()) 
				|| Boolean.TRUE.equals(templates.getEliminar()));

		return autorizarContratos || utorizarIndicadores || utorizarRoles || autorizarDominios || autorizarInbox
				|| autorizarUsuarios || autorizarParamTemplates || autorizarNotificaciones || autorizarTemplates;

	}

}
