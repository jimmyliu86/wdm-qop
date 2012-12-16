package wdm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	private String label = "";
	private boolean usaConversor = false;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<CanalOptico> canales = new HashSet<CanalOptico>();

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

	/**
	 * Retorna true si el nodo necesita utilizar un conversor de longitudes de
	 * onda.
	 * 
	 * @return boolean Necesidad del conversor.
	 */
	public boolean usaConversor() {
		return usaConversor;
	}

	/**
	 * Indica que el nodo necesita un conversor de longitud onda.
	 */
	public void utilizarConversor() {
		this.usaConversor = true;
	}

	/**
	 * Función que cambia el nodo como que no necesita un conversor de longitud
	 * de onda.
	 */
	public void inicializar() {
		this.usaConversor = false;
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
		HashMap<Camino,Integer> ultimaLdO = new HashMap<Camino,Integer>();

		/* Inicio del algoritmo de busqueda en anchura */
		Camino caminoBase = new Camino(this);
		ultimaLdO.put(caminoBase, new Integer(-1));
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
				int ldoPreferida = ultimaLdO.get(caminoActual);
				
				Enlace e = canal.getEnlaceLibre( ldoPreferida );
				
				if ( e == null ) continue;
				
				Nodo otroExtremo = e.getOtroExtremo(nodoActual);
				
				if ( visitados.contains(otroExtremo) ) continue;
				
				Camino caminoNuevo = new Camino(caminoActual);
				int secuencia = caminoActual.getDistancia()+1;
				// TODO: se debe recibir el canal optico en vez del enlace
				caminoNuevo.addSalto(new Salto(secuencia, e));
				
				aExplorar.add(caminoNuevo);
				ultimaLdO.put(caminoNuevo, e.getLongitudDeOnda());
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
	
	public Camino dijkstra(){
		
		return null;
	}
}
