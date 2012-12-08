package wdm.qop;

import wdm.Camino;

public class SegmentoProtegido {
	private final Camino primario;
	private final Camino alternativo;
	private final double pFalla;
	private final double pRecuperacion;
	
	/**
	 * Constructor Principal
	 * @param primario
	 * @param alternativo
	 */
	public SegmentoProtegido(Camino primario, Camino alternativo) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		
		this.pFalla = probabilidadFalla();
		this.pRecuperacion = probabilidadRecuperacion();
	}
	
	/**
	 * Retorna la probabilidad de falla del segmento
	 * @return
	 */
	private double probabilidadFalla(){
		return pFalla;
	}
	
	/**
	 * Retorna la probabilidad de recuperacion del segmento
	 * @return
	 */
	private double probabilidadRecuperacion(){
		return pRecuperacion;
	}
}
