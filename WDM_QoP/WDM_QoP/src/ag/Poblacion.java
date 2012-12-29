package ag;

import java.util.ArrayList;
import java.util.Iterator;
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
	 * Operador de selecci�n
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
	 * <p>
	 * La operacion de cruce se realiza con los individuos ya seleccionados.
	 * </p>
	 * 
	 * @param selectos
	 */
	public void cruzar(Set<Solucion> selectos) {
		Solucion primero = null;
		Solucion miPrimero = null;
		Solucion segundo = null;
		Solucion nuevo = null;
		Iterator<Solucion> i = selectos.iterator();

		if (i.hasNext()) {
			primero = i.next();
			miPrimero = primero;
			int index = 0;

			// cruces en pares desde el principio
			while (i.hasNext()) {
				segundo = i.next();
				this.seleccionar();
				nuevo = operadorCruce.cruzar(primero, segundo);
				this.hijos.set(index, nuevo);
				index++;
				primero = segundo;
			}
			// cruce entre el primero del grupo con el último.
			nuevo = operadorCruce.cruzar(miPrimero, segundo);
			this.hijos.set(index, nuevo);
		}
	}

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
	 * @return tamanho
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
