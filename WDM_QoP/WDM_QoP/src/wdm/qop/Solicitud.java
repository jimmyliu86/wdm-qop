package wdm.qop;

import wdm.*;
import java.util.StringTokenizer;

public class Solicitud {
	private final Nodo origen;
	private final Nodo destino;
	private final Nivel nivel;
	
	/**
	 * Constructor principal
	 * 
	 * @param origen	Nodo Origen
	 * @param destino	Nodo Destino
	 * @param nivel		Nivel de Calidad de Proteccion solicitada.
	 */
	public Solicitud(Nodo origen, Nodo destino, Nivel nivel){
		this.origen = origen;
		this.destino = destino;
		this.nivel = nivel;
	}
	/**
	 * Constructor que toma los datos de un String. Utiliza la red
	 * para obtener el objeto Nodo.
	 * 
	 * @param red
	 * @param sSolicitud
	 */
	public Solicitud(Red red, String sSolicitud){	
		StringTokenizer st = new StringTokenizer(sSolicitud,":",false);
		
		this.origen = red.getNodo(st.nextToken());
		this.destino = red.getNodo(st.nextToken());
		this.nivel = Nivel.valueOf(st.nextToken());
	}
	
	/**
	 * Getter del nodo Origen
	 * @return	Nodo origen
	 */
	public Nodo getOrigen() {
		return origen;
	}

	/**
	 * Getter del nodo Destino
	 * @return	Nodo destino
	 */
	public Nodo getDestino() {
		return destino;
	}

	/**
	 * Nivel de Calidad
	 * @return
	 */
	public Nivel getNivel() {
		return nivel;
	}
}
