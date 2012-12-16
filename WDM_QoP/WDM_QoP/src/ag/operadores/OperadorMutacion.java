package ag.operadores;

import ag.Individuo;

/**
 * Interfaz que define la operacion de mutación.
 * 
 * @author mrodas
 * 
 */
public interface OperadorMutacion {
	/**
	 * Realiza una operación de mutación sobre un Individuo dado.
	 * 
	 * @param i
	 *            El Individuo a ser mutado
	 */
	public void mutar(Individuo i);
}
