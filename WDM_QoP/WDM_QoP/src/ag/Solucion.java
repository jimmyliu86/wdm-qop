package ag;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import wdm.qop.Solicitud;

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
@Table(name = "Solucion")
public class Solucion implements Individuo {

	@Id
	@GeneratedValue
	private long id;

	// Genes de la solución
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Gen> genes;

	// Demandas realizadas
	private Set<Solicitud> solicitudes;

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
		// Calculamos el costo de la solución
		double total = 0.0;
		for (Gen gen : this.genes) {
			total += gen.getCosto();
		}

		this.costo = total;
		this.fitness = 1 / this.costo;
		return this.fitness;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Gen> getGenes() {
		return genes;
	}

	public void setGenes(Set<Gen> genes) {
		this.genes = genes;
	}

	public Set<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Set<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

}
