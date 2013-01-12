package wdm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import wdm.qop.Servicio;

/**
 * Clase que representa el enlace de un canal Optico.
 * <p>
 * Descripcion: Enlace cuyos componentes son: longitud de Onda
 * </p>
 * 
 * @author aamadeo
 * 
 */

@Entity
public class Enlace {
	
	@Id
	@GeneratedValue
	private long id;
	
	private int longitudDeOnda = -1;
	private int fibra = -1;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private CanalOptico canal;

	//Propiedad de Simulacion
	@Transient
	private Servicio servicio;
	
	@Transient
	private Set<Servicio> reservas = new HashSet<Servicio>();
	
	@Transient
	private boolean bloqueado = false;
	
	@Transient
	private boolean disponible = true;
	
	public Enlace(){}

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
	public Enlace(int fibra, int ldo,  CanalOptico canal) {
		this.longitudDeOnda = ldo;
		this.fibra = fibra;
		this.canal = canal;
	}

	/**
	 * Retorna el valor de longitud de onda del enlace.
	 * 
	 * @return
	 */
	public int getLongitudDeOnda() {
		return this.longitudDeOnda;
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
	public Nodo getExtremoA() {
		return canal.getExtremoA();
	}

	/**
	 * Retorna el nodo destino del canal optico.
	 * 
	 * @return Nodo destino.
	 */
	public Nodo getExtremoB() {
		return canal.getExtremoB();
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

	public CanalOptico getCanal() {
		return canal;
	}

	public void setFibra(int fibra) {
		this.fibra = fibra;
	}

	public void setCanal(CanalOptico canal) {
		this.canal = canal;
	}

	public void setLongitudDeOnda(int longitudDeOnda) {
		this.longitudDeOnda = longitudDeOnda;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Servicio> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Servicio> reservas) {
		this.reservas = reservas;
	}

	public Nodo getOtroExtremo(Nodo a) {
		return canal.getOtroExtremo(a);
	}

	public boolean isBloqueado() {
		return bloqueado;
	}
	
	public void bloquear(){
		this.bloqueado = true;
		
		System.out.println("Bloqueando " + canal + "." + longitudDeOnda);
	
		for(Enlace e : canal.getEnlaces()){
			if(!e.bloqueado) return;
		}
		
		canal.bloquear();
	}
	
	public void desbloquear(){
		System.out.println("Desloqueando " + canal + "." + longitudDeOnda);
		this.bloqueado = false;
		this.canal.desbloquear();
	}
}
