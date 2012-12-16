package ag;

import java.util.Set;

import ag.operadores.OperadorCruce;
import ag.operadores.OperadorMutacion;
import ag.operadores.OperadorSeleccion;


/**
 * Clase Población que implementar las operaciones propias de la Población.
 * <p>
 * Administrar: Individuos, hijos, fitness, operador de cruce y operador de
 * mutacion, operador de Selección.
 * </p>
 * 
 * @author mrodas
 * 
 */
public class Poblacion {

	/*
	 * Individuos de la población
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
	 * Operador de mutación
	 */
	private OperadorMutacion operadorMutacion;

	/*
	 * Operador de selección
	 */
	private OperadorSeleccion operadorSeleccion;

	/**
	 * Operación de cruce de Individuos de un conjunto selecto de individuos.
	 * 
	 * @param selectos
	 */
	public void cruzar(Set<Individuo> selectos) {
		// TODO Implementar el cruce
	};

	/**
	 * Operación de mutación de Individuos de la Población.
	 */
	public void mutar() {
		// TODO Implementar la mutación
	};

	/**
	 * Operación de seleccion de Individuos para cruzar.
	 * 
	 * @return individuos seleccionados
	 */
	public Set<Individuo> seleccionar() {
		// TODO Implementar la seleccion.
		return null;
	}

	/**
	 * Función que obtiene el tamaño de la población
	 * 
	 * @return tamaño
	 */
	public int getTamaño() {
		// TODO Implementar obtener tamaño de la poblacion
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
