package ag.operadores;

import ag.Individuo;

/**
 * Interfaz que define la operacion de mutación.
 * <p>
 * OBSERVACION: No se utiliza un operador de mutación, debido a las
 * características aleatorias del cruce implementado.
 * </p>
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
