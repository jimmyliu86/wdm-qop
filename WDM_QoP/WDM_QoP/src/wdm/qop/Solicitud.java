package wdm.qop;

import wdm.*;
import java.util.StringTokenizer;

public class Solicitud {
	private final Nodo origen;
	private final Nodo destino;
	private final Nivel nivel;
	
	public Solicitud(Nodo origen, Nodo destino, Nivel nivel){
		this.origen = origen;
		this.destino = destino;
		this.nivel = nivel;
	}
	
	public Solicitud(Red red, String sSolicitud){	
		StringTokenizer st = new StringTokenizer(sSolicitud,":",false);
		
		this.origen = red.getNodo(st.nextToken());
		this.destino = red.getNodo(st.nextToken());
		this.nivel = Nivel.valueOf(st.nextToken());
	}
	
	public Nodo getOrigen() {
		return origen;
	}

	public Nodo getDestino() {
		return destino;
	}

	public Nivel getNivel() {
		return nivel;
	}
}
