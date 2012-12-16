package ag.operadores;

import java.util.Set;

import ag.Individuo;

/**
 * Interfaz OperadorCruce que define la implementación de una operación de
 * cruce.
 * 
 * @author mrodas
 * 
 */
public interface OperadorCruce {
	/**
	 * Realiza una operación de cruce entre dos Individuos, produciendo un
	 * conjunto de Individuos hijos.
	 * 
	 * @param i1
	 *            Primer Individuo a cruzar
	 * @param i2
	 *            Segundo Individuo a cruzar
	 * @return Individuos hijos
	 */
	public Set<Individuo> cruzar(Individuo i1, Individuo i2);

}
