package wdm.qop;

import wdm.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Id;

@Entity
public class Solicitud implements Comparable<Solicitud>{
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo origen;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo destino;
	
	private Nivel nivel;
	
	private EsquemaRestauracion esquema = EsquemaRestauracion.FullPath;
	
	public Solicitud(){}
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	@Override
	public int hashCode() {
		return origen.hashCode()*10000 + destino.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Solicitud)) return false;
		
		Solicitud b = (Solicitud) obj;
		
		return origen.equals(b.origen) && destino.equals(b.destino);
	}
	
	@Override
	public String toString() {
		return origen + "_a_" + destino + "." + nivel.toString();
	}

	public EsquemaRestauracion getEsquema() {
		return esquema;
	}

	public void setEsquema(EsquemaRestauracion esquema) {
		this.esquema = esquema;
	}

	@Override
	public int compareTo(Solicitud s) {
		int cmpOrd = this.nivel.ordinal() - s.nivel.ordinal();
		
		if (cmpOrd != 0) return cmpOrd;
		
		return hashCode() - s.hashCode();
	}
	
	public Exclusividad getExclusividadPrimario(){
		if (nivel == Nivel.Oro || nivel == Nivel.Plata1) return Exclusividad.Exclusivo;

		return Exclusividad.SinReservasBronce;
	}
	
	public Exclusividad getExclusividadAlternativo(){
		if (nivel == Nivel.Oro) return Exclusividad.Exclusivo;
		
		return Exclusividad.NoExclusivo;
	}
}
