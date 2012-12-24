package ag.operadores;

import ag.Individuo;

/**
 * Interfaz que define la operacion de mutaci�n.
 * <p>
 * OBSERVACION: No se utiliza un operador de mutaci�n, debido a las
 * caracter�sticas aleatorias del cruce implementado.
 * </p>
 * 
 * @author mrodas
 * 
 */
public interface OperadorMutacion {
	/**
	 * Realiza una operaci�n de mutaci�n sobre un Individuo dado.
	 * 
	 * @param i
	 *            El Individuo a ser mutado
	 */
	public void mutar(Individuo i);
}
