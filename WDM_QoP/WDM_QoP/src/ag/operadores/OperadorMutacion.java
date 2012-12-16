package ag.operadores;

import ag.Individuo;

/**
 * Interfaz que define la operacion de mutaci�n.
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
