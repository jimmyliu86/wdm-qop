package ag.operadores;

import java.util.Set;

import ag.Individuo;
import ag.Poblacion;

/**
 * Interfaz que define la operaci�n de selecci�n.
 * 
 * @author mrodas
 * 
 */
public interface OperadorSeleccion {
	/**
	 * Realiza la operaci�n de selecci�n sobre una poblaci�n de individuos.
	 * 
	 * @param poblacion
	 *            La poblaci�n sobre la cual realizar la selecci�n
	 * @return Individuos seleccionados
	 */
	public Set<Individuo> seleccionar(Poblacion poblacion);
}
