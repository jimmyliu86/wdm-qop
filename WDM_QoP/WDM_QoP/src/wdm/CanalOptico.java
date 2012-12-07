package wdm;

import java.util.HashMap;

public class CanalOptico {
	private Nodo origen;
	private Nodo destino;
	private int fibras;
	private int ldos;
	
	private final HashMap<String, Enlace> enlaces = new HashMap<String, Enlace>();
	
	/**
	 * 
	 * @param origen	Nodo Origen
	 * @param destino	Nodo Destino
	 * @param fibras	Cantidad de fibras en el canal
	 * @param ldos		Cantidad de Longitudes de Onda por fibra
	 */
	public CanalOptico(Nodo origen, Nodo destino, int fibras, int ldos){
		this.origen = origen;
		this.destino = destino;
		this.fibras = fibras;
		this.ldos = ldos;
		
		this.enlaces.clear();
		
		for(int i = 0; i < fibras; i++){
			for(int j = 0; j < ldos; j++){
				this.enlaces.put(i+"-"+j, new Enlace(i,j,this));
			}
		}
	}
	
	public void inicializar(){
		for( Enlace e: enlaces.values() ){
			e.desbloquear();
			e.eliminarReservas();
		}
	}
	
	public Nodo getOrigen() {
		return origen;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}

	public Nodo getDestino() {
		return destino;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}

	public int getFibras() {
		return fibras;
	}

	public void setFibras(int fibras) {
		this.fibras = fibras;
	}

	public int getLdos() {
		return ldos;
	}

	public void setLdos(int ldos) {
		this.ldos = ldos;
	}

	public HashMap<String, Enlace> getEnlaces() {
		return enlaces;
	}
	
	/* ************************
	 *  Metodos de simulacion *
	 **************************/

	/**
	 * Simula una falla
	 */
	public void echarCanal(){
		/*
		 * Se bloquea primeramente los enlaces y se notifica a los
		 * servicios afectados.
		 */
		
		for( Enlace e: enlaces.values() ){
			e.bloquear();
		}
	}
}
