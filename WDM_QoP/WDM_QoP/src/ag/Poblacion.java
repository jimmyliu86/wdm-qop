package ag;

import java.util.ArrayList;
import java.util.Set;

import ag.operadores.OperadorCruce;
import ag.operadores.OperadorSeleccion;
import ag.operadores.impl.TorneoBinario;

/**
 * Clase Población que implementar las operaciones propias de la Población.
 * <p>
 * Administrar: Individuos, hijos, fitness, operador de cruce y operador de
 * Selección.
 * </p>
 * 
 * @author mrodas
 * 
 */
public class Poblacion {

	/*
	 * Individuos de la población
	 */
	private ArrayList<Individuo> individuos;

	/*
	 * Hijos de los individuos selectos
	 */
	private ArrayList<Individuo> hijos;

	/*
	 * Valor de calidad de un cromosoma
	 */
	private ArrayList<Double> fitness;

	/*
	 * Operador de cruce
	 */
	private OperadorCruce operadorCruce;

	/*
	 * Operador de selección
	 */
	private OperadorSeleccion operadorSeleccion;

	/**
	 * Constructor de la Población.
	 * 
	 * @param individuos
	 */
	public Poblacion(ArrayList<Individuo> individuos) {
		this.individuos = individuos;
		this.operadorSeleccion = new TorneoBinario();
	}

	/**
	 * Operación de cruce de Individuos de un conjunto selecto de individuos.
	 * 
	 * @param selectos
	 */
	public void cruzar(Set<Individuo> selectos) {
		// TODO Implementar el cruce
	};

	/**
	 * Operación de seleccion de Individuos para cruzar.
	 * 
	 * @return individuos seleccionados
	 */
	public Individuo[] seleccionar() {
		return this.operadorSeleccion.seleccionar(this);
	}

	/**
	 * Función que obtiene el tamaño de la población
	 * 
	 * @return tamaño
	 */
	public int getTamanho() {
		return this.individuos.size();
	}

	public ArrayList<Individuo> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Individuo> individuos) {
		this.individuos = individuos;
	}

	public ArrayList<Individuo> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<Individuo> hijos) {
		this.hijos = hijos;
	}

	public ArrayList<Double> getFitness() {
		return fitness;
	}

	public void setFitness(ArrayList<Double> fitness) {
		this.fitness = fitness;
	}

	public OperadorCruce getOperadorCruce() {
		return operadorCruce;
	}

	public void setOperadorCruce(OperadorCruce operadorCruce) {
		this.operadorCruce = operadorCruce;
	}

	public OperadorSeleccion getOperadorSeleccion() {
		return operadorSeleccion;
	}

	public void setOperadorSeleccion(OperadorSeleccion operadorSeleccion) {
		this.operadorSeleccion = operadorSeleccion;
	}
}
