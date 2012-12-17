package ag;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase Soluci�n que implementa al Individuo. TODO: La soluci�n final se debe
 * persistir.
 */
public class Solucion implements Individuo {

	// Genes de la soluci�n
	private Set<Gen> genes;

	// Fitness de la Soluci�n
	private double fitness;

	// Costo de la Soluci�n
	private Double costo;

	public Solucion() {
		super();
		genes = new HashSet<>();
	}

	@Override
	public double evaluar() {
		// TODO Implementar el algoritmo de evaluaci�n
		return 0;
	}

	/**
	 * Obtener Fitness.
	 * 
	 * @return the fitness
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * Asignar Fitness.
	 * 
	 * @param fitness
	 *            de la soluci�n
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Gen[] genesToArray() {
		return (Gen[]) genes.toArray();
	}

	/**
	 * @return the costo
	 */
	public double getCosto() {
		return costo;
	}

	/**
	 * @param costo
	 *            the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}

}
