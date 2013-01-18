package wdm.qop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import wdm.Camino;
import wdm.Nodo;

@Entity
public class Servicio implements Comparable<Servicio> {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Solicitud solicitud;

	@OneToOne(cascade = CascadeType.ALL)
	private Camino primario;

	@OneToOne(cascade = CascadeType.ALL)
	private Camino alternativo;

	@Transient
	private double pFalla;

	@Transient
	private double pRecuperacion;

	@Transient
	private boolean disponible = true;

	/**
	 * Constructor principal
	 * 
	 * @param solicitud
	 *            Solicitud a la que se desea proveerle un servicio
	 */
	public Servicio(Solicitud solicitud) {
		super();
		this.solicitud = solicitud;
		this.inicializar();
	}

	/**
	 * Constructor usado para crear hijos
	 * 
	 * @param primario
	 * @param alternativo
	 */
	public Servicio(Camino primario, Camino alternativo, Nivel nivel) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		this.solicitud = new Solicitud(primario.getOrigen(),
				primario.getDestino(), nivel);
	}

	/**
	 * Getter de la solicitud
	 * 
	 * @return Solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Obtiene la probabilidad de falla del servicio
	 * 
	 * @return Probabilidad de Falla
	 */
	public double getpFalla() {
		return pFalla;
	}

	/**
	 * Obtiene la probabilidad de recuperacion del servicio.
	 * 
	 * @return Probabilidad de servicio
	 */
	public double getpRecuperacion() {
		return pRecuperacion;
	}

	/**
	 * Funcion de Simulacion, retorna true si el servicio esta disponible
	 * 
	 * @return Disponibildad del servicio
	 */
	public boolean estaDisponible() {
		return disponible;
	}

	/**
	 * Setter de la disponibilidad del servicio
	 * 
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
		return "De:" + solicitud.getOrigen() + " A:" + solicitud.getDestino();
	}

	@Override
	public int compareTo(Servicio otro) {
		int retorno = (int) (this.getSolicitud().getId() - otro.getSolicitud()
				.getId());
		return retorno;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		if (solicitud == null) {
			if (other.solicitud != null)
				return false;
		} else if (!solicitud.equals(other.solicitud))
			return false;
		return true;
	}

	/**
	 * Función para crear un camino primario y otro secundario.
	 */
	public void inicializar() {
		this.primario = crearPrimario();
		this.primario.bloquearCanales();
		this.alternativo = crearPrimario();
		if (this.solicitud.getNivel().equals(Nivel.Oro))
			this.alternativo.bloquearNodos();
	}

	private Camino crearPrimario() {
		Camino route = null;
		Nodo origen = this.solicitud.getOrigen();
		Nodo destino = this.solicitud.getDestino();
		route = origen.dijkstra(destino);
		route.setEnlaces();
		return route;
	}
}
