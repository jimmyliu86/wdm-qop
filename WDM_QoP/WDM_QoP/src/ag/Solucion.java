package ag;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase Solución que implementa al Individuo.
 * <p>
 * Conceptualmente esta clase es el Cromosoma del Algoritmo Genético. Tiene el
 * conjunto de genes que representan las partes de la solución, su fitness y su
 * costo.
 * </p>
 * <p>
 * TODO: La solución final se debe probar si persiste.
 * </p>
 */
@Entity
@Table(name="Solucion")
public class Solucion implements Individuo {

	@Id
	@GeneratedValue
	private long id;
	
	// Genes de la solución
	@OneToMany(cascade=CascadeType.ALL)
	private Set<Gen> genes;

	// Fitness de la Solución
	private double fitness;

	// Costo de la Solución
	private Double costo;

	public Solucion() {
		super();
		genes = new HashSet<>();
	}

	@Override
	public double evaluar() {
		// TODO Implementar el algoritmo de evaluación
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
	 *            de la solución
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
