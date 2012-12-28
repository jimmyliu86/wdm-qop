package wdm.qop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import wdm.Nodo;

@Entity
public class Solicitud {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Nodo origen;

	@ManyToOne(cascade = CascadeType.ALL)
	private Nodo destino;

	private Nivel nivel;

	public Solicitud() {
	}

	/**
	 * Constructor principal
	 * 
	 * @param origen
	 *            Nodo Origen
	 * @param destino
	 *            Nodo Destino
	 * @param nivel
	 *            Nivel de Calidad de Proteccion solicitada.
	 */
	public Solicitud(Nodo origen, Nodo destino, Nivel nivel) {
		this.origen = origen;
		this.destino = destino;
		this.nivel = nivel;
	}

	/**
	 * Getter del nodo Origen
	 * 
	 * @return Nodo origen
	 */
	public Nodo getOrigen() {
		return origen;
	}

	/**
	 * Getter del nodo Destino
	 * 
	 * @return Nodo destino
	 */
	public Nodo getDestino() {
		return destino;
	}

	/**
	 * Nivel de Calidad
	 * 
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Solicitud other = (Solicitud) obj;
		// id
		if (id != other.id)
			return false;
		// origen
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		// destino
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		// nivel
		if (nivel != other.nivel)
			return false;

		return true;
	}

}
