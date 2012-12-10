package wdm;

import java.util.HashSet;

import wdm.qop.Servicio;

/**
 * Clase que representa el enlace de un canal óptico.
 * <p>
 * Descripción: Enlace cuyos componentes son: longitud de Onda
 * </p>
 * 
 * @author aamadeo
 * 
 */
public class Enlace {
	private final int longitudOnda;
	private final int fibra;
	private final CanalOptico canal;

	private Servicio servicio;
	private final HashSet<Servicio> reservas = new HashSet<Servicio>();

	private boolean bloqueado = false;
	private boolean disponible = true;

	/**
	 * Constructor principal
	 * 
	 * @param ldo
	 *            Longitud de Onda
	 * @param fibra
	 *            Identificador de Fibra
	 * @param canal
	 *            Canal Optico que contiene al enlace
	 */
	public Enlace(int ldo, int fibra, CanalOptico canal) {
		this.longitudOnda = ldo;
		this.fibra = fibra;
		this.canal = canal;
	}

	/**
	 * Retorna el valor de longitud de onda del enlace.
	 * 
	 * @return
	 */
	public int getLongitudDeOnda() {
		return this.longitudOnda;
	}

	/**
	 * Retorna el identificador de fibra del enlace.
	 * 
	 * @return
	 */
	public int getFibra() {
		return fibra;
	}

	/**
	 * Retorna el nodo origen del canal optico.
	 * 
	 * @return Nodo Origen
	 */
	public Nodo getOrigen() {
		return canal.getOrigen();
	}

	/**
	 * Retorna el nodo destino del canal optico.
	 * 
	 * @return Nodo destino.
	 */
	public Nodo getDestino() {
		return canal.getDestino();
	}

	/**
	 * Reserva el canal optico como parte de un segmento alternativo
	 * 
	 * @param servicio
	 *            Servicio que utilizara el enlace en su segmento alternativo
	 */
	public void reservar(Servicio servicio) {
		if (!reservas.contains(servicio)) {
			reservas.add(servicio);
		}
	}

	/**
	 * Retorna true si el enlace tiene al menos una reserva.
	 * 
	 * @return
	 */
	public boolean estaReservado() {
		return (reservas.size() > 0);
	}

	/**
	 * Elimina una reserva especifica
	 * 
	 * @param servicio
	 *            Servicio que reservo el enlace.
	 */
	public void eliminarReserva(Servicio servicio) {
		reservas.remove(servicio);
	}

	/**
	 * Elminar todas las reservas.
	 */
	public void eliminarReservas() {
		reservas.clear();
	}

	/**
	 * Bloquear el enlace porque forma parte del camino primario de algun
	 * Servicio
	 */
	public void bloquear() {
		this.bloqueado = true;
	}

	/**
	 * Desbloquea el enlace porque ya no forma parte del camino primario de
	 * algun Servicio
	 */
	public void desbloquear() {
		this.bloqueado = false;
	}

	/**
	 * Retorna true si el enlace está bloqueado.
	 * 
	 * @return
	 */
	public boolean estaBloqueado() {
		return this.bloqueado;
	}

	/**
	 * Funcion de simulación, que provee conexion al servicio en cuestion.
	 * 
	 * @param servicio
	 *            Servicio que desea utilizar el enlace.
	 */
	public void utilizar(Servicio servicio) {
		this.servicio = servicio;
	}

	/**
	 * Funcion de simulacion, que libera de uso al enlace.
	 */
	public void liberar() {
		this.servicio = null;
	}

	/**
	 * Funcion de simulacion, que retorna true si el enlace esta siendo
	 * utilizado.
	 * 
	 * @return
	 */
	public boolean estaLibre() {
		return this.servicio != null;
	}

	/**
	 * Funcion de simulacion, que interrumpe la conectividad del enlace.
	 * Notifica al servicio que lo utilizaba, si hubiera alguno.
	 */
	public void echar() {
		this.disponible = false;

		if (this.servicio == null)
			return;

		this.servicio.setDisponible(false);
	}

	/**
	 * Funcion de simulacion, Restablece el servicio del enlace.
	 */
	public void restablecer() {
		this.disponible = true;
	}

	/**
	 * Funcion de simulacion, retorna true si el enlace esta disponible
	 * 
	 * @return Estado del enlace
	 */
	public boolean estaDisponible() {
		return this.disponible;
	}

	/**
	 * Restablece los valores iniciales del enlace
	 */
	public void inicializar() {
		this.bloqueado = false;
		this.disponible = true;
		this.reservas.clear();
		this.servicio = null;
	}
}
