package wdm;

import java.util.HashMap;
/**
 * Clase que representa los canales Ópticos que forman parte de la red.
 * <p>
 * Descripción: Canal Óptico utilizado para representar la agrupacion de enlaces
 * que están definidos por longitud de onda, por fibra, y por canal.
 * </p>
 * 
 * @author aamadeo
 * 
 */
public class CanalOptico {
	private final Nodo origen;
	private final Nodo destino;
	private final int fibras;
	private final int ldos;

	private final HashMap<String, Enlace> enlaces = new HashMap<String, Enlace>();

	/**
	 * Constructor principal. Setea los atributos principales y genera los
	 * enlaces del canal.
	 * 
	 * @param origen
	 *            Nodo Origen
	 * @param destino
	 *            Nodo Destino
	 * @param fibras
	 *            Cantidad de fibras en el canal
	 * @param ldos
	 *            Cantidad de Longitudes de Onda por fibra
	 */
	public CanalOptico(Nodo origen, Nodo destino, int fibras, int ldos) {
		this.origen = origen;
		this.destino = destino;
		this.fibras = fibras;
		this.ldos = ldos;

		this.enlaces.clear();

		for (int i = 0; i < fibras; i++) {
			for (int j = 0; j < ldos; j++) {
				this.enlaces.put(i + "-" + j, new Enlace(i, j, this));
			}
		}
	}

	/**
	 * Inicializa los valores del canal, en caso de que algun enlace este
	 * bloqueado, reservado.
	 */
	public void inicializar() {
		for (Enlace e : enlaces.values()) {
			e.desbloquear();
			e.eliminarReservas();
		}
	}

	/**
	 * Getter del nodo origen
	 * 
	 * @return Nodo origen
	 */
	public Nodo getOrigen() {
		return origen;
	}

	/**
	 * Getter del nodo Destino
	 * 
	 * @return Nodo Destino
	 */
	public Nodo getDestino() {
		return destino;
	}

	/**
	 * Retorna un enlace que no este bloqueado. Retorna null si no encuentra un
	 * enlace libre.
	 * 
	 * @return Enlace libre
	 */
	public Enlace getEnlaceLibre() {
		for (int i = 0; i < fibras; i++) {
			for (int j = 0; j < ldos; j++) {
				Enlace e = enlaces.get(i + "-" + j);

				if (!e.estaBloqueado())
					return e;
			}
		}

		return null;
	}

	/* ************************
	 * Metodos de simulacion 
	 * *************************
	 */

	/**
	 * Simula una falla, echando cada enlace y notificando en cada servicio.
	 */
	public void echarCanal() {
		for (Enlace e : enlaces.values()) {
			e.echar();
		}
	}
}
