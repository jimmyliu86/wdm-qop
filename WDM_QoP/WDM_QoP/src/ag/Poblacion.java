package ag;

import java.util.Set;

import ag.operadores.OperadorCruce;
import ag.operadores.OperadorMutacion;
import ag.operadores.OperadorSeleccion;


/**
 * Clase Poblaci�n que implementar las operaciones propias de la Poblaci�n.
 * <p>
 * Administrar: Individuos, hijos, fitness, operador de cruce y operador de
 * mutacion, operador de Selecci�n.
 * </p>
 * 
 * @author mrodas
 * 
 */
public class Poblacion {

	/*
	 * Individuos de la poblaci�n
	 */
	private Set<Individuo> individuos;

	/*
	 * Hijos de los individuos selectos
	 */
	private Set<Individuo> hijos;

	/*
	 * Valor de calidad de un cromosoma
	 */
	private Set<Double> fitness;

	/*
	 * Operador de cruce
	 */
	private OperadorCruce operadorCruce;

	/*
	 * Operador de mutaci�n
	 */
	private OperadorMutacion operadorMutacion;

	/*
	 * Operador de selecci�n
	 */
	private OperadorSeleccion operadorSeleccion;

	/**
	 * Operaci�n de cruce de Individuos de un conjunto selecto de individuos.
	 * 
	 * @param selectos
	 */
	public void cruzar(Set<Individuo> selectos) {
		// TODO Implementar el cruce
	};

	/**
	 * Operaci�n de mutaci�n de Individuos de la Poblaci�n.
	 */
	public void mutar() {
		// TODO Implementar la mutaci�n
	};

	/**
	 * Operaci�n de seleccion de Individuos para cruzar.
	 * 
	 * @return individuos seleccionados
	 */
	public Set<Individuo> seleccionar() {
		// TODO Implementar la seleccion.
		return null;
	}

	/**
	 * Funci�n que obtiene el tama�o de la poblaci�n
	 * 
	 * @return tama�o
	 */
	public int getTama�o() {
		// TODO Implementar obtener tama�o de la poblacion
		return 0;
	}

	public Set<Individuo> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(Set<Individuo> individuos) {
		this.individuos = individuos;
	}

	public Set<Individuo> getHijos() {
		return hijos;
	}

	public void setHijos(Set<Individuo> hijos) {
		this.hijos = hijos;
	}

	public Set<Double> getFitness() {
		return fitness;
	}

	public void setFitness(Set<Double> fitness) {
		this.fitness = fitness;
	}

	public OperadorCruce getOperadorCruce() {
		return operadorCruce;
	}

	public void setOperadorCruce(OperadorCruce operadorCruce) {
		this.operadorCruce = operadorCruce;
	}

	public OperadorMutacion getOperadorMutacion() {
		return operadorMutacion;
	}

	public void setOperadorMutacion(OperadorMutacion operadorMutacion) {
		this.operadorMutacion = operadorMutacion;
	}

	public OperadorSeleccion getOperadorSeleccion() {
		return operadorSeleccion;
	}

	public void setOperadorSeleccion(OperadorSeleccion operadorSeleccion) {
		this.operadorSeleccion = operadorSeleccion;
	}
}
