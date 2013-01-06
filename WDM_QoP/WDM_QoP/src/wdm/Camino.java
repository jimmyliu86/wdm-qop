package wdm;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
	
	private int distancia = 0;
	
	public Camino(){}
	
	/**
	 * Constructor principal
	 * @param origen
	 */
	public Camino(Nodo origen){
		this.origen = origen;
		this.destino = origen;
		this.saltos = new TreeSet<Salto>();
		this.saltos.clear();
		this.distancia = 0;
	}
	
	/**
	 * Constructor apartir de un camin existente
	 * @param c	Camino existente
	 */
	public Camino(Camino c){
		this.origen = c.origen;
		
		this.saltos = new TreeSet<Salto>();
		this.saltos.addAll(c.saltos);
		this.destino = c.destino;
		this.distancia = c.distancia;
	}
	
	/**
	 * Metodo para agregar un salto al camino
	 * @param salto
	 */
	public void addSalto(Salto salto){
		saltos.add(salto);
		
		if (destino == null) destino = origen;
		
		destino = salto.getCanal().getOtroExtremo(destino);
		distancia = salto.getCanal().getCosto();
	}
	
	public void addNull() {
		saltos.add(null);
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
	 * @return La cantidad de saltos, es decir el tama�o de la lista saltos
	 */
	public int getDistancia(){
		return this.distancia;
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
		actual.bloquear();
		
		//int i = 0;
		for(Salto salto : saltos){
			CanalOptico canal = salto.getCanal();
			Nodo anterior = actual;
			actual = canal.getOtroExtremo(actual);
			//i++;
			
//			System.out.print("["+i+"/"+saltos.size()+"] ");
//			System.out.println("Bloqueando : " + canal+"("+anterior+")");
			if(actual == null) System.out.println("c"+canal.getId()+"-"+anterior);
			
			actual.bloquear();
		}
	}
	
	public void desbloquearNodos(){
		Nodo actual = this.origen;
		
		//int i = 0;
		for(Salto salto : saltos){
			actual = salto.getCanal().getOtroExtremo(actual);
			//i++;
			
			actual.desbloquear();
		}
	}
	
	public void desbloquearEnlaces(){
		for(Salto salto : saltos){
			Enlace e = salto.getEnlace();
			if ( e != null ) e.desbloquear();
		}
	}

	public Nodo getOrigen() {
		return origen;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	
	public void anexar(Camino c){		
		if ( ! c.origen.equals(this.destino) ) return;
		
		int secuencia = this.saltos.size()+1;
		Nodo actual = this.destino;
		
		for(Salto s: c.saltos){
			actual = s.getCanal().getOtroExtremo(actual);
			Salto newSalto = new Salto(secuencia++,s.getCanal());
			this.saltos.add(newSalto);
		}
		
		this.distancia = distancia + c.distancia;
		this.destino = c.destino;
	}
	
	public void setEnlaces(){
		int ldo = -1;
		for(Salto salto: saltos){
			ldo = salto.setEnlace(ldo);
		}
	}
	
	@Override
	public String toString(){
		String camino = origen.toString();
		Nodo actual = origen;
		
		for(Salto s: saltos){
			actual = s.getCanal().getOtroExtremo(actual);
			camino = camino + "-" + actual;
		}
		
		return camino;
	}
}
