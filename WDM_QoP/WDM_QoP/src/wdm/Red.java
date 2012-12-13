package wdm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
@Entity
@Table(name="Red")
public class Red {

	@OneToMany(cascade=CascadeType.ALL)
	private Set<Nodo> nodos = new HashSet<Nodo>();
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<CanalOptico> canales = new HashSet<CanalOptico>();
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -6192832626602644784L;
	
	@Id 
	@GeneratedValue 
	private int id; 

	/**
	 * Constructor principal
	 */
	public Red(){
		nodos.clear();
		canales.clear();
	}

	/* -----------------------Metodos delegados del conjunto----------------- */

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
		return nodos.contains(nodo);
	}

	/**
	 * Agrega un nodo a la red
	 * @param key	Etiqueta del nodo
	 * @param value	Nodo
	 * @return		true si cambio la red
	 */
	public boolean addNodo(Nodo value) {
		return nodos.add(value);
	}

	/**
	 * Elimina el nodo de la red, a partir de su clave.
	 * 
	 * @param nodo	Nodo a eliminar
	 * @return		true si cambio la red
	 */
	public boolean removeNodo(Nodo nodo) {
		if ( nodo != null ){
			nodo.romperEnlaces(this);
		}

		return nodos.remove(nodo);
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

	public Set<Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(Set<Nodo> nodos) {
		this.nodos = nodos;
	}

	public Set<CanalOptico> getCanales() {
		return canales;
	}

	public void setCanales(Set<CanalOptico> canales) {
		this.canales = canales;
	}
}
