package ag.operadores;

import ag.Solucion;

/**
 * Interfaz OperadorCruce que define la implementaci�n de una operaci�n de
 * cruce.
 * 
 * @author mrodas
 * 
 */
public interface OperadorCruce {
	/**
	 * Realiza una operaci�n de cruce entre dos Individuos, produciendo un
	 * conjunto de Individuos hijos.
	 * 
	 * @param i1
	 *            Primer Individuo a cruzar
	 * @param i2
	 *            Segundo Individuo a cruzar
	 * @return Individuos hijos
	 */
	public Solucion cruzar(Solucion i1, Solucion i2);

}
