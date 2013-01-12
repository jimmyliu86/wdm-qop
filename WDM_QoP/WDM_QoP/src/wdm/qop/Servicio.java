package wdm.qop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import wdm.Camino;

@Entity
public class Servicio implements Comparable<Servicio>{
	
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
	 * Constructor usado para crear hijos 
	 * @param primario
	 * @param alternativo
	 */
	public Servicio(Camino primario, Camino alternativo) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
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
	
	@Override
	public int compareTo(Servicio otro) {
		int retorno = (int) (this.getSolicitud().getId() - otro.getSolicitud().getId());
		return retorno;
	}
}
