package wdm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Nodo")
/**
 * Clase que representa a un Nodo de la red representada.
 * <P>
 * Descripción: representación del Nodo cuyos atributos son: etiqueta o clave,
 * si variable que indica que usa Conversor, y la lista de canales ópticos
 * directamente conectados a nodos vecinos y si mismo.
 * </p>
 * 
 * @author aamadeo
 * 
 */
public class Nodo {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String label = "";
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<CanalOptico> canales = new HashSet<CanalOptico>();
	
	@Transient
	private boolean bloqueado = false;
	
	public Nodo() {
		label = "";
	}

	public Nodo(String label) {
		this.label = label;
	}

	/**
	 * Retorna la etiqueta del nodo.
	 * 
	 * @return Etiqueta del nodo
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Setter de la etiqueta del nodo
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean estaBloqueado(){
		return this.bloqueado;
	}
	
	public void bloquear(){
		this.bloqueado = true;
	}
	
	public void desbloquear(){
		this.bloqueado = false;
	}

	/**
	 * Resetea el estado del nodo.
	 */
	public void inicializar() {
		this.bloqueado = false;
	}
		
	/**
	 * Retorna el camino mas corto al nodo especificado.
	 * 
	 * @param destino
	 *            Nodo destino
	 * @return Camino mas corto al nodo destino.
	 */
	public Camino busquedaAnchura(Nodo destino) {
		LinkedList<Camino> aExplorar = new LinkedList<Camino>();
		HashSet<Nodo> visitados = new HashSet<Nodo>();

		/* Inicio del algoritmo de busqueda en anchura */
		Camino caminoBase = new Camino(this,this);
		aExplorar.add(caminoBase);

		while (!aExplorar.isEmpty()) {
			Camino caminoActual = aExplorar.poll();
			
			Nodo nodoActual = caminoActual.getDestino();

			if (visitados.contains(nodoActual)) continue;

			visitados.add(nodoActual);

			/* Llegamos a destino */
			if (nodoActual.equals(destino)) {
				return caminoActual;
			}
			
			for(CanalOptico canal : nodoActual.canales){				
				Nodo otroExtremo = canal.getOtroExtremo(nodoActual);
				
				if ( visitados.contains(otroExtremo) ) continue;
				
				Camino caminoNuevo = new Camino(caminoActual);
				int secuencia = caminoActual.getDistancia()+canal.getCosto();
				caminoNuevo.addSalto(new Salto(secuencia, canal));
				
				aExplorar.add(caminoNuevo);
			}
		}

		return null;
	}

	/**
	 * Retorna true si el nodo tiene la misma etiqueta.
	 * 
	 * @param b
	 * @return
	 */
	public boolean equals(Nodo b) {
		return this.label == b.label;
	}
	
	public void addCanal(CanalOptico canal){
		if(! canales.contains(canal)){
			canales.add(canal);
		}
	}

	public CanalOptico romperEnlace(Nodo vecino){
		for ( CanalOptico c: canales){
			if (vecino.equals(c.getOtroExtremo(this))){
				canales.remove(c);
				return c;
			}
		}
		
		return null;
	}
	
	/**
	 * Elimina todos los enlaces del nodo.
	 */
	public void romperEnlaces(Red red){
		for (CanalOptico canal : canales){
			Nodo vecino = canal.getOtroExtremo(this);
			
			/*Se elimina el enlace del vecino al nodo*/
			CanalOptico canalInverso = vecino.romperEnlace(this); 

			/* Se elimina el enlace del nodo al vecino */
			canales.remove(canal);

			/* Se elimina ambos enlaces de la red */
			red.removeCanal(canal);
			red.removeCanal(canalInverso);
		}
	}

	public String toString(){
		return this.label;
	}

	public Set<CanalOptico> getCanales() {
		return canales;
	}

	public void setCanales(Set<CanalOptico> canales) {
		this.canales = canales;
	}
	
	private class NodoDijkstra implements Comparable<NodoDijkstra> {
		private final Nodo nodo;
		private Camino camino;
		private int distancia = Integer.MAX_VALUE;
		
		public NodoDijkstra(Nodo nodo, int distancia){
			this.nodo = nodo;
			this.distancia = distancia;
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof Nodo){
				return ((Nodo)o).label.equalsIgnoreCase(nodo.label);
			}
			
			if (o instanceof NodoDijkstra){
				return ((NodoDijkstra)o).nodo.label.equalsIgnoreCase(nodo.label);
			}
			
			return false;
		}
		
		@Override
		public int compareTo(NodoDijkstra arg0) {			
			NodoDijkstra b = (NodoDijkstra) arg0;
			
			return this.distancia - b.distancia;
		}
		
		public int getDistancia(){
			return this.distancia;
		}

		public Nodo getNodo() {
			return nodo;
		}

		public Camino getCamino() {
			return camino;
		}

		public void setCamino(Camino camino) {
			this.camino = camino;
		}
	}
	
	/**
	 * Retorna el camino mas corto al nodo especificado utilizando el algoritmo de dijkstra.
	 * 
	 * @param destino
	 *            Nodo destino
	 * @return Camino mas corto al nodo destino.
	 */
	public Camino dijkstra(Nodo destino){
		
		PriorityQueue<NodoDijkstra> aVisitar = new PriorityQueue<NodoDijkstra>();
		HashMap<Nodo,Integer> distancias = new HashMap<Nodo,Integer>();
		HashSet<Nodo> visitados = new HashSet<Nodo>();
		
		NodoDijkstra nodoOrigen = new NodoDijkstra(this,0);
		nodoOrigen.setCamino(new Camino(this,this));
		aVisitar.add(nodoOrigen);
		distancias.put(this, new Integer(0));
		
		while(! aVisitar.isEmpty()){
			NodoDijkstra dNodo = aVisitar.poll();
			Nodo actual = dNodo.getNodo();
			Camino camino = dNodo.getCamino();
			
			if(actual.equals(destino)) {
				return camino;
			}

			if (visitados.contains(actual)) continue;
			
			visitados.add(actual);
			
			for(CanalOptico canal : actual.canales){
				Nodo vecino = canal.getOtroExtremo(actual);
				int costo = canal.getCosto();
				
				if (canal.estaBloqueado())	continue;
				if (vecino.estaBloqueado())	continue;
				if (visitados.contains(vecino)) continue;
				
				if(distancias.containsKey(vecino)){
					int dActual = distancias.get(vecino);
					
					if( dActual <= dNodo.getDistancia()+costo) continue;
					else {
						aVisitar.remove(vecino);
						distancias.remove(vecino);
					}
				}
				
				NodoDijkstra nuevoNodo = new NodoDijkstra(vecino,dNodo.getDistancia()+costo);
				Camino caminoNuevo = new Camino(camino);
				caminoNuevo.addSalto(new Salto(camino.getSaltos().size()+1, canal));
				nuevoNodo.setCamino(caminoNuevo);
				aVisitar.add(nuevoNodo);
				distancias.put(nuevoNodo.getNodo(), nuevoNodo.distancia);
			}
		}
		
		return null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		int labelInt = Integer.parseInt(label);
		
		if (labelInt > 0) return labelInt; 
		
		return super.hashCode();
	}
	
	
}
