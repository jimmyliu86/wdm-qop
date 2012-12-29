package wdm.qop;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Nodo;
import wdm.Salto;

@Entity
public class Servicio{
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private  Solicitud solicitud;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino primario;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino alternativo;
	
	@Transient
	private double pFalla;
	
	@Transient
	private double pRecuperacion;
	
	@Transient
	private boolean disponible = true;
	
	/**
	 * Constructor principal
	 * @param solicitud	Solicitud a la que se desea proveerle un servicio
	 */
	public Servicio(Solicitud solicitud) {
		super();
		this.solicitud = solicitud;
	}

	/**
	 * Getter de la solicitud
	 * @return	Solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Obtiene la probabilidad de falla del servicio
	 * @return	Probabilidad de Falla
	 */
	public double getpFalla() {
		return pFalla;
	}

	/**
	 * Obtiene la probabilidad de recuperacion del servicio.
	 * @return	Probabilidad de servicio
	 */
	public double getpRecuperacion() {
		return pRecuperacion;
	}


	/**
	 * Funcion de Simulacion, retorna true si el servicio esta disponible
	 * @return	Disponibildad del servicio
	 */
	public boolean estaDisponible() {
		return disponible;
	}

	/**
	 * Setter de la disponibilidad del servicio
	 * @param disponible
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Camino getPrimario() {
		return primario;
	}

	public void setPrimario(Camino primario) {
		this.primario = primario;
	}

	public Camino getAlternativo() {
		return alternativo;
	}

	public void setAlternativo(Camino alternativo) {
		this.alternativo = alternativo;
	}
			
	@Override
	public String toString() {
		return "s"+solicitud.getOrigen()+"_"+solicitud.getDestino();
	}
}
