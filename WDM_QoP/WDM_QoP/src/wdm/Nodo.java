package wdm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
	private final String label = "";
	private boolean usaConversor = false;
	private final HashMap<Nodo, CanalOptico> canales = new HashMap<Nodo, CanalOptico>();

	/**
	 * Retorna la etiqueta del nodo.
	 * 
	 * @return Etiqueta del nodo
	 */
	public String getLabel() {
		return label;
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
	 * Obtiene la lista de nodos vecinos a este nodo.
	 * 
	 * @return HashSet<Nodo> vecinos.
	 */
	public Set<Nodo> getVecinos() {

		return canales.keySet();
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

			for (CanalOptico canal : actual.canales.values()) {
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

	/**
	 * Elimina todos los enlaces del nodo.
	 */
	public void romperEnlaces() {
		for (Nodo vecino : canales.keySet()) {

			/* Se elimina el enlace del vecino al nodo */
			vecino.canales.remove(this);

			/* Se elimina el enlace del nodo al vecino */
			canales.remove(this);

			/* Se elimina ambos enlaces de la red */
			Red.getRed().removeCanal(canales.get(vecino));
			Red.getRed().removeCanal(vecino.canales.get(this));
		}
	}
}
