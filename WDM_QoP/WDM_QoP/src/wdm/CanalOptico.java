package wdm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="CanalOptico")
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

	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("longitudDeOnda ASC")
	private Set<Enlace> enlaces = new HashSet<Enlace>();
	
	@Id 
	@GeneratedValue 
	private int id;
	
	private int fibras;
	
	private int ldos;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo extremoA;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo extremoB;
	
	@Transient
	private boolean bloqueado = false;
	
	public CanalOptico(){}
	
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
	public CanalOptico(Nodo a, Nodo b, int fibras, int ldos) {
		this.extremoA = a;
		this.extremoB = b;
		this.fibras = fibras;
		this.ldos = ldos;

		this.enlaces.clear();
		crearEnlaces();
	}
	
	public void crearEnlaces(){
		for (int i = 0; i < fibras; i++) {
			for (int j = 0; j < ldos; j++) {
				this.enlaces.add(new Enlace(i, j, this));
			}
		}
	}

	/**
	 * Inicializa los valores del canal, en caso de que algun enlace este
	 * bloqueado, reservado.
	 */
	public void inicializar() {
		this.desbloquear();
		
		for (Enlace e : enlaces) {
			e.eliminarReservas();
		}
	}
	/**
	 * Getter del nodo Destino
	 * 
	 * @return Nodo Destino
	 */
	public Nodo getExtremoB() {
		return extremoB;
	}

	/* ************************
	 * Metodos de simulacion 
	 * *************************
	 */

	/**
	 * Simula una falla, echando cada enlace y notificando en cada servicio.
	 */
	public void echarCanal() {
		for (Enlace e : enlaces) {
			e.echar();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFibras() {
		return fibras;
	}

	public void setFibras(int fibras) {
		this.fibras = fibras;
	}

	public int getLdos() {
		return ldos;
	}

	public void setLdos(int ldos) {
		this.ldos = ldos;
	}

	public void setDestino(Nodo b) {
		this.extremoB = b;
	}

	public Nodo getExtremoA() {
		return this.extremoA;
	}

	public void setOrigen(Nodo a) {
		this.extremoA = a;
	}
	
	public Nodo getOtroExtremo(Nodo a){
		if(! a.equals(extremoA) && ! a.equals(extremoB)) return null;
		
		if(a.equals(extremoA)) return extremoB;
		
		return extremoA;
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

	
}
