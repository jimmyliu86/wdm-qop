package wdm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

import wdm.qop.Servicio;

/**
 * Clase Camino, representa un camino por su nodo origen, y una lista de
 * enlaces que debe seguir
 * @author albert
 *
 */
@Entity
public class Camino {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo origen;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo destino;
	
	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("secuencia ASC")
	private Set<Salto> saltos;
	
	public Camino(){}
	
	/**
	 * Constructor principal
	 * @param origen
	 */
	public Camino(Nodo origen){
		this.origen = origen;
		this.destino = origen;
		this.saltos = new HashSet<Salto>();
		this.saltos.clear();
	}
	
	/**
	 * Constructor apartir de un camin existente
	 * @param c	Camino existente
	 */
	public Camino(Camino c){
		this.origen = c.origen;
		
		this.saltos = new HashSet<Salto>();
		this.saltos.addAll(c.saltos);
		this.destino = c.destino;
	}
	
	/**
	 * Metodo para agregar un salto al camino
	 * @param salto
	 */
	public void addSalto(Salto salto){
		saltos.add(salto);
		
		if (destino == null) destino = origen;
		
		destino = salto.getCanal().getOtroExtremo(destino);
	}
	
	/**
	 * Utilizado para obtener el destino del camino
	 * @return El ultimo nodo visitado en el camino
	 */
	public Nodo getDestino(){
		return this.destino;
	}
	
	/**
	 * Utilizado para obtener la longitud del camino en saltos
	 * @return La cantidad de saltos, es decir el tamaño de la lista saltos
	 */
	public int getDistancia(){
		return saltos.size();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Set<Salto> getSaltos(){
		return saltos;
	}
	
	public void setSaltos(Set<Salto> saltos){
		this.saltos = saltos;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}
	
	public void bloquearCanales(){
		for(Salto salto : saltos){
			salto.getCanal().bloquear();
		}
	}
	
	public void bloquearNodos(){
		Nodo actual = this.origen;
		
		int i = 0;
		for(Salto salto : saltos){
			actual = salto.getCanal().getOtroExtremo(actual);
			i++;
			
			if( i < saltos.size()) actual.bloquear();
		}
	}

	public Nodo getOrigen() {
		return origen;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	
	/**
	 * TODO: Agregar bloquear nodo
	 */
}
