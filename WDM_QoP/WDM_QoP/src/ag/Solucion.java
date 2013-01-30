package ag;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Salto;
import wdm.qop.Servicio;
import wdm.qop.Solicitud;

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

	public Solucion(Set<Solicitud> solicitudes) {
		super();

		Set<Servicio> servicios = new TreeSet<Servicio>();
		for (Solicitud s : solicitudes) {
			Servicio servicio = new Servicio(s);
			servicio.setDisponible(true);
			servicios.add(servicio);
		}

		this.genes = new TreeSet<Servicio>(servicios);
		this.fitness = 0.0;
		this.costo = 0.0;
	}

	/**
	 * Calcula el costo en función de la Fórmula de Evaluación Definida.
	 * <p>
	 * Costo = suma_de_distancia x a + suma_de_cambios_LDO x b
	 * </p>
	 */
	@Override
	public double evaluar() {
		int size = this.cantidadEnlaces();
		double total_Distancia = 0.0;
		double total_LDO = 0.0;
		int contador = 0;

		Set<CanalOptico> auxiliar = new HashSet<CanalOptico>();

		for (Servicio gen : this.genes) {

			Camino primario = gen.getPrimario();

			if (primario == null) {
				contador += 10;
			} else {

				for (Salto s : primario.getSaltos()) {

					CanalOptico ca = s.getCanal();
					boolean inserto = auxiliar.add(ca);
					// inserto es false cuando ya existía en auxiliar
					if (!inserto)
						contador++;
				}
				total_LDO += primario.getCambiosLDO();
			}

			
		}

		// se descuentan los enlaces cuyos canales opticos ya existen.
		total_Distancia = size - contador;

		// Fórmula de Costo de una Solucion
		this.costo = total_Distancia * a + total_LDO * b;
		// Fitness de la Solución
		this.fitness = 1 / this.costo;

		return this.fitness;
	}

	/**
	 * Obtiene la cantidad total de enlaces utilizados en la solución.
	 * Independientemente si pasa o no por el mismo Canal Optico.
	 * 
	 * @return
	 */
	public int cantidadEnlaces() {
		int size = 0;
		for (Servicio gen : this.genes) {
			if (gen != null) {
				if (gen.getPrimario() == null)
					size -= 10;
				else
					size += gen.getPrimario().getDistancia();

			}

		}
		return size;
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

	public void setGenes(Set<Servicio> genes) {
		this.genes = (TreeSet<Servicio>) genes;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solucion other = (Solucion) obj;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = genes.size();
		return "[Solucion [fitness=" + fitness + ", costo=" + costo
				+ ", \ngenes="
				+ (genes != null ? toString(genes, maxLen) : null) + "]";
	}

	private String toString(Set<Servicio> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n[");
		int i = 0;
		for (Iterator<Servicio> iterator = collection.iterator(); iterator
				.hasNext(); i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next().toString());
		}
		builder.append("]");
		return builder.toString();
	}

}
