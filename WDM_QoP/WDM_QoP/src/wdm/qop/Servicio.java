package wdm.qop;

import java.util.HashSet;

public class Servicio {
	
	private final Solicitud solicitud;
	private double pFalla;
	private double pRecuperacion;
	private final HashSet<SegmentoProtegido> segmentos = new HashSet<SegmentoProtegido>();
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
	 * Retorna los segmentos protegidos
	 * @return	Segmentos Protegidos
	 */
	public HashSet<SegmentoProtegido> getSegmentos() {
		return segmentos;
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
}
