package wdm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
/**
 * Clase que representa la red sobre la que se simularán las solicitudes.
 * <p>
 * Descripción: Red cuyos componentes son: una instancia de la misma Red, un
 * conjunto de nodos y un conjunto de canales.
 * </p>
 * 
 * @author aamadeo
 * 
 */
public class Red {
	private static Red instancia = null;
	private final HashMap<String, Nodo> nodos = new HashMap<String, Nodo>();
	private final HashSet<CanalOptico> canales = new HashSet<CanalOptico>();

	/**
	 * Función de acceso a la instancia singleton de la red. Si no está
	 * instanciada, instancia un objeto y luego lo devuelve.
	 * 
	 * @return Instancia de la red
	 */
	public static Red getRed() {
		if (Red.instancia == null) {
			Red.instancia = new Red();
		}

		return Red.instancia;
	}

	/**
	 * Constructor principal
	 */
	private Red() {
		nodos.clear();
		canales.clear();
	}

	/* -----------------------Metodos delegados del conjunto----------------- */

	/**
	 * Función que controla la existencia de un nodo en la red a partir de su
	 * etiqueta. Retorna true si el nodo ya forma parte de la red.
	 * 
	 * @param label
	 *            Etiqueta del nodo
	 * @return Existencia del nodo
	 */
	public boolean existeNodo(String label) {
		return nodos.containsKey(label);
	}

	/**
	 * Función que controla la existencia de un nodo en la red a partir de la
	 * Instancia del Nodo buscado. Retorna true si el nodo ya forma parte de la
	 * red.
	 * 
	 * @param nodo
	 *            Nodo a ser buscado
	 * @return Existencia del nodo
	 */
	public boolean existeNodo(Nodo nodo) {
		return nodos.containsValue(nodo);
	}

	/**
	 * Retorna el nodo correspondiente a la etiqueta recibida.
	 * 
	 * @param label
	 *            Etiqueta del Nodo
	 * @return Nodo
	 */
	public Nodo getNodo(String label) {
		return nodos.get(label);
	}

	/**
	 * Agrega un nodo a la red
	 * 
	 * @param key
	 *            Etiqueta del nodo
	 * @param value
	 *            Nodo
	 * @return El nodo agregado
	 */
	public Nodo addNodo(String key, Nodo value) {
		return nodos.put(key, value);
	}

	/**
	 * Elimina el nodo de la red, a partir de su clave.
	 * 
	 * @param clave
	 * @return
	 */
	public Nodo removeNodo(String clave) {
		Nodo nodo = nodos.remove(clave);

		if (nodo != null) {
			nodo.romperEnlaces();
		}

		return nodo;
	}

	/**
	 * Retorna el numero de nodos que posee la red.
	 * 
	 * @return
	 */
	public int cantidadNodos() {
		return nodos.size();
	}

	/**
	 * Retorna el conjunto de nodos
	 * 
	 * @return Conjunto de Nodos
	 */
	public Collection<Nodo> nodos() {
		return nodos.values();
	}

	/**
	 * Agrega un canal a la red
	 * 
	 * @param canal
	 * @return true si pudo insertar el canal
	 */
	public boolean addCanal(CanalOptico canal) {
		return canales.add(canal);
	}

	/**
	 * Retorna true si el canal existe
	 * 
	 * @param canal
	 *            CanalOptico que se busca
	 * @return true si el canal existe
	 */
	public boolean existeCanal(CanalOptico canal) {
		return canales.contains(canal);
	}

	/**
	 * Elimina un canal de la red
	 * 
	 * @param canal
	 *            Canal de la red
	 * @return true si pudo eliminar de la red
	 */
	public boolean removeCanal(CanalOptico canal) {
		return canales.remove(canal);
	}

	/**
	 * Retorna la cantidad de canales de la red
	 * 
	 * @return Numero de canales
	 */
	public int cantidadCanales() {
		return canales.size();
	}
}
