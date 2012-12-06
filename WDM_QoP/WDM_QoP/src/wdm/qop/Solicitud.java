package wdm.qop;

import wdm.*;
import java.util.StringTokenizer;

public class Solicitud {
	private Nodo origen;
	private Nodo destino;
	private Nivel nivel;
	private final Red red;
	
	public Solicitud(Red red){
		this.red = red;
	}
	
	public void parseSolicitud(String sSolicitud){
		StringTokenizer st = new StringTokenizer(sSolicitud,":",false);
		
		this.origen = red.get(st.nextToken());
		this.destino = red.get(st.nextToken());
		this.nivel = Nivel.valueOf(st.nextToken());
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
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	/* ********************************* */
	
}
