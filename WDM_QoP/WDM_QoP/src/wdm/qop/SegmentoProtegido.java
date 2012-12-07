package wdm.qop;

import wdm.Camino;

public class SegmentoProtegido {
	private final Camino primario;
	private final Camino alternativo;
	private final double pFalla;
	private final double pRecuperacion;
	
	public SegmentoProtegido(Camino primario, Camino alternativo) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		
		this.pFalla = probabilidadFalla();
		this.pRecuperacion = probabilidadRecuperacion();
	}
	
	private double probabilidadFalla(){
		return 0.0;
	}
	
	private double probabilidadRecuperacion(){
		return 0.0;
	}
}
