package ag;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import wdm.qop.Servicio;

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

	// Genes de la solución (caminos a cada demanda - Servicios)
	@OneToMany(cascade = CascadeType.ALL)
	private ArrayList<Servicio> genes;

	// Fitness de la Solución
	private double fitness;

	// Costo de la Solución
	private Double costo;

	public Solucion() {
		super();
		genes = new ArrayList<Servicio>();
		// solicitudes = new ArrayList<Solicitud>();
	}

	public Solucion(ArrayList<Servicio> servicios) {
		super();
		int size = servicios.size();
		genes = new ArrayList<Servicio>(size);
	}

	@Override
	public double evaluar() {
		// Calculamos el costo de la solución
		double total = 0.0;
		for (Servicio gen : this.genes) {
			total += gen.getPrimario().getDistancia();
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

	public ArrayList<Servicio> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Servicio> genes) {
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
		for (int i = 0; i < this.getGenes().size(); i++) {
			if (this.getGenes().get(i).getSolicitud()
					.equals(solucion.getGenes().get(i).getSolicitud()))
				contador++;
		}
		boolean retorno = false;
		if (contador == this.getGenes().size())
			retorno = true;
		return retorno;
	}
}
