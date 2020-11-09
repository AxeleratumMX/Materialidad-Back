package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

public enum TypeMasterContract {
		tower("TOWER"),
		land("LAND"),
		habitacional("HABITACIONAL"),
		comercial("COMERCIAL"),
		;

		/**
		 * Variable que guarda el valor de la enumeración en un string
		 */
		private final String type;

		/**
		 * Constructor de la enumeración.
		 *
		 * @param Tipo contrato.
		 */
		private TypeMasterContract(String type) {
			this.type = type;
		}

		public String toString() {
			return this.type;
		}
}
