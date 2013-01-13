package ag;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import wdm.qop.Servicio;

/**
 * Clase Solución que implementa al Individuo.
 * <p>
 * Conceptualmente esta clase es el Cromosoma del Algoritmo Genético. Tiene el
 * conjunto de genes que representan las partes de la solución: genes (Conjunto
 * de Servicios (tiene la solicitud, el camino primario y el camino
 * secundario)), su fitness y su costo.
 * </p>
 */
@Entity
@Table(name = "Solucion")
public class Solucion implements Individuo {

	@Id
	@GeneratedValue
	private long id;

	// Genes de la solución (Conjunto de Servicios)
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Servicio> genes;

	// Fitness de la Solución
	private double fitness;

	// Costo de la Solución
	private double costo;

	// Valor por kilometro.
	public static double a = 0.1;
	// Valor por cambio de longitud de onda
	public static double b = 2;

	public Solucion() {
		super();
		this.genes = new TreeSet<Servicio>();
		this.fitness = 0.0;
		this.costo = 0.0;
	}

	public Solucion(Set<Servicio> servicios) {
		super();
		genes = new TreeSet<Servicio>(servicios);
	}

	/**
	 * Calcula el costo en función de la Fórmula de Evaluación Definida.
	 * <p>
	 * Costo = suma_de_distancia x a + suma_de_cambios_LDO x b
	 * </p>
	 */
	@Override
	public double evaluar() {

		double total1 = 0.0;
		double total2 = 0.0;
		for (Servicio gen : this.genes) {
			total1 += gen.getPrimario().getDistancia();
			total2 += gen.getPrimario().getCambiosLDO();
		}

		// Fórmula de Costo de una Solucion
		this.costo = total1 * a + total2 * b;
		// Fitness de la Solución
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

	public TreeSet<Servicio> getGenes() {
		return (TreeSet<Servicio>) genes;
	}

	public void setGenes(TreeSet<Servicio> genes) {
		this.genes = genes;
	}

	/**
	 * Función que contrala si tiene las mismas solicitudes que la solicitud
	 * recibida.
	 * 
	 * @param solucion
	 * @return
	 */
	public boolean mismasSolicitudes(Solucion solucion) {
		int contador = 0;
		boolean retorno = false;

		if (this.getGenes().size() != solucion.getGenes().size()) {
			return retorno;
		}

		int size = this.getGenes().size();
		Iterator<Servicio> i1 = this.getGenes().iterator();
		Iterator<Servicio> i2 = solucion.getGenes().iterator();

		for (int i = 0; i < size; i++) {
			Servicio s1 = i1.next();
			Servicio s2 = i2.next();
			if (s1.getSolicitud().equals(s2.getSolicitud())) {
				contador++;
			} else {
				retorno = false;
				i = size;
			}
		}

		if (contador == size)
			retorno = true;

		return retorno;
	}
}
