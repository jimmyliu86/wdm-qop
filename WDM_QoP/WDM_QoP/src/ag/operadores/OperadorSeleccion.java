package ag.operadores;

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
	public Individuo[] seleccionar(Poblacion poblacion);
}
