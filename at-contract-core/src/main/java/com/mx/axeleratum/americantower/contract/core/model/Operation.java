package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Permite realizar operaciones entre l
 * 0.....................
 * o valores del contrato
 * @author usuario
 *
 */
@Data
@ToString
public class Operation {
   private String name;
   private String descripcion;
   /**
    * Va definir el nivel de anidacion de los calculos
    * 
    * Ejemplo  Nivel 0:  El valor depende directamente del valor seteado en los contractValues
    *                1:  El valor depende de un calculo realizado en nivel 0
    *                2:  El valor depende de un cálculo realizado en nivel 2
    *                n:  El valor depende de un cálculo realizado en el nivel n-1
    */
   private Integer level;
   /**
    * Define si la operacion es un calculo que devuelve un valor o sólo es una verificación
    */
   private OperationType type;
   private List<Param> params;
}
