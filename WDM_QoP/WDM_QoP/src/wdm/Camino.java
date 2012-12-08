package wdm;

import java.util.LinkedList;

/**
 * Clase Camino, representa un camino por su nodo origen, y una lista de
 * enlaces que debe seguir
 * @author albert
 *
 */
public class Camino {
	private final Nodo origen;
	private final LinkedList<Enlace> saltos;
	
	/**
	 * Constructor principal
	 * @param origen
	 */
	public Camino(Nodo origen){
		this.origen = origen;
		this.saltos = new LinkedList<Enlace>();
		this.saltos.clear();
	}
	
	/**
	 * Constructor apartir de un camin existente
	 * @param c	Camino existente
	 */
	public Camino(Camino c){
		this.origen = c.origen;
		this.saltos = (LinkedList<Enlace>) c.saltos.clone();
	}
	
	/**
	 * Metodo para agregar un salto al camino
	 * @param salto
	 */
	public void addSalto(Enlace salto){
		saltos.add(salto);
	}
	
	/**
	 * Utilizado para obtener el destino del camino
	 * @return El ultimo nodo visitado en el camino
	 */
	public Nodo getDestino(){
		return saltos.getLast().getDestino();
	}
	
	/**
	 * Utilizado para obtener la longitud del camino en saltos
	 * @return La cantidad de saltos, es decir el tamaño de la lista saltos
	 */
	public int getDistancia(){
		return saltos.size();
	}
}
