package wdm.qop;

import java.util.HashSet;

public class Servicio {
	
	private final Solicitud solicitud;
	private final double pFalla;
	private final double pRecuperacion;
	private final HashSet<SegmentoProtegido> segmentos = new HashSet<SegmentoProtegido>();
	private boolean disponible = true;
	
	public Servicio(Solicitud solicitud, double pFalla, double pRecuperacion) {
		super();
		this.solicitud = solicitud;
		this.pFalla = pFalla;
		this.pRecuperacion = pRecuperacion;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public double getpFalla() {
		return pFalla;
	}

	public double getpRecuperacion() {
		return pRecuperacion;
	}

	public HashSet<SegmentoProtegido> getSegmentos() {
		return segmentos;
	}

	public boolean estaDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
}
