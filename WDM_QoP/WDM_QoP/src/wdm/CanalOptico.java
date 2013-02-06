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

import wdm.qop.Exclusividad;
import wdm.qop.Nivel;
import wdm.qop.Servicio;

@Entity
@Table(name="CanalOptico")
/**
 * Clase que representa los canales opticos que forman parte de la red.
 * <p>
 * Descripcion: Canal optico utilizado para representar la agrupacion de enlaces
 * que estan definidos por longitud de onda, por fibra, y por canal.
 * </p>
 * 
 * @author aamadeo
 * 
 */
public class CanalOptico implements Comparable<CanalOptico> {

	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("longitudDeOnda ASC")
	private Set<Enlace> enlaces = new HashSet<Enlace>();
	
	@Transient
	private Set<Enlace> enlacesNecesarios = new HashSet<Enlace>();
	
	@Id 
	@GeneratedValue 
	private int id;
	
	private int fibras;
	
	@Transient int fibrasExtra = 0;
	
	private int ldos;
	
	private int costo;
	
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
		this.fibrasExtra= 0;
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
		this.fibrasExtra = 0;
		this.enlacesNecesarios.clear();
		this.enlacesNecesarios.addAll(enlaces);
		
		for (Enlace e : enlaces) {
			e.inicializar();
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
	
	public void agregarFibraExtra(){
		this.fibrasExtra++;
		
		for ( int i = 0; i < ldos; i++ ){
			enlacesNecesarios.add(new Enlace(fibras + fibrasExtra, i, this));
		}
	}
	
	public int getFibrasExtra(){
		return this.fibrasExtra;
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
	
	public Enlace getEnlaceLibre(Exclusividad exclusividad){
		if(bloqueado) return null;		
		
		Enlace [] disponibles = new Enlace[enlacesNecesarios.size()];
		int i = 0;
		for(Enlace e: this.enlacesNecesarios){
			if(!e.isBloqueado()){
				if (e.cumpleExclusividad(exclusividad)){
					disponibles[i++] = e;
				}
			}
		}
		
		int sorteado = (int)(Math.random()*((double)i));
		
		return disponibles[sorteado];
	}
	
	public Enlace getEnlaceLibre(Exclusividad exclusividad, int ldO){
		if(bloqueado)return null;
		
		for(Enlace e: enlacesNecesarios){
			if (!e.isBloqueado()){
				if(e.getLongitudDeOnda() == ldO){
					if (e.cumpleExclusividad(exclusividad)){
						return e;
					}
				}
			}
		}
		
		return getEnlaceLibre(exclusividad);
	}
	
	
	/**
	 * Bloquear el enlace porque forma parte del camino primario de algun
	 * Servicio
	 */
	public void bloquear() {
		this.bloqueado = true;
	}

	public void desbloquear() {
		this.bloqueado = false;
	}

	/**
	 * Retorna true si el enlace esta bloqueado.
	 * 
	 * @return
	 */
	public boolean estaBloqueado() {
		return this.bloqueado;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public Set<Enlace> getEnlaces() {
		return enlaces;
	}
	
	public Enlace getEnlace(int fibra, int ldo){
		for(Enlace e : enlacesNecesarios){
			if ( e.getFibra() == fibra && e.getLongitudDeOnda() == ldo) return e;
		}
		
		return null;
	}

	public void setEnlaces(Set<Enlace> enlaces) {
		this.enlaces = enlaces;
		this.enlacesNecesarios.clear();
		this.enlacesNecesarios.addAll(enlaces);
		
		System.out.println("enlacesNecesarios : " + enlacesNecesarios.size());
	}

	@Override
	public String toString() {
		return extremoA + "-" + extremoB;
	}
	
	public int getUso(){
		double total = 0;
		double utilizados = 0;
		for(Enlace e : enlacesNecesarios){
			total+=1;
			if(e.isBloqueado()) utilizados+=1;
		}
		
		return (int) (100.0*utilizados/total);
	}

	@Override
	public int compareTo(CanalOptico arg0) {
		int cmpOrigen = extremoA.compareTo(arg0.extremoA);
		
		if ( cmpOrigen != 0 ) return cmpOrigen;
		
		return extremoB.compareTo(arg0.extremoB);
	}
	
	public boolean tieneEnlacesExclusivos(){
		for(Enlace e: enlacesNecesarios){
			if(!e.estaReservado() && !e.isBloqueado() ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean libreSegunExclusividad(Exclusividad exclusividad){

		if ( Exclusividad.Exclusivo == exclusividad ) return tieneEnlacesExclusivos();
		
		if ( Exclusividad.SinReservasBronce == exclusividad ){
			for(Enlace e: enlacesNecesarios){
				if(!e.isBloqueado()) {
					boolean tieneReservasBronce = false;
					
					for(Servicio s: e.getReservas()){
						if ( s.getSolicitud().getNivel() == Nivel.Bronce ){
							tieneReservasBronce = true;
							break;
						}
					}
					
					if ( ! tieneReservasBronce ) return true;
				}
			}
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CanalOptico)) return false;
		
		CanalOptico b = (CanalOptico) obj;
		
		return this.extremoA.equals(b.extremoA) && this.extremoB.equals(b.extremoB);
	}
}
