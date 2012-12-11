package wdm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade=CascadeType.ALL)
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
		LinkedList<Nodo> aVisitar = new LinkedList<Nodo>();
		HashSet<Nodo> visitados = new HashSet<Nodo>();

		Camino caminoActual = new Camino(this);

		/* Inicio del algoritmo de busqueda en anchura */
		aVisitar.add(this);

		while (!aVisitar.isEmpty()) {
			Nodo actual = aVisitar.poll();

			if (visitados.contains(actual))
				continue;

			visitados.add(actual);

			/* Llegamos a destino */
			if (actual.equals(destino)) {
				return caminoActual;
			}
			
			for(CanalOptico canal : actual.canales){
				Enlace e = canal.getEnlaceLibre();

				if (e == null)
					break;

				aVisitar.add(e.getDestino());
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

	public CanalOptico romperEnlace(Nodo vecino){
		for ( CanalOptico c: canales){
			if (vecino.equals(c.getDestino())){
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
			Nodo vecino = canal.getDestino();
			
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
}
