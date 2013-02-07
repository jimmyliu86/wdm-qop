package ag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ag.operadores.OperadorCruce;
import ag.operadores.OperadorSeleccion;
import ag.operadores.impl.MiCruce;
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
	private Collection<Individuo> individuos;

	/*
	 * Hijos de los individuos selectos
	 */
	private Collection<Individuo> hijos;

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
	public Poblacion(Collection<Individuo> individuos) {
		this.individuos = individuos;
		this.operadorSeleccion = new TorneoBinario();
		this.operadorCruce = new MiCruce();
		this.hijos = new ArrayList<Individuo>();

	}

	public void generarPoblacion() {
		int ind1 = 1;
		for (Individuo i : this.individuos) {
			Solucion s = (Solucion) i;
			s.random();
			s.setId(ind1);
			ind1++;
		}
	}

	/**
	 * Operación de cruce de Individuos de un conjunto selecto de individuos.
	 * <p>
	 * La operacion de cruce se realiza con los individuos ya seleccionados.
	 * </p>
	 * 
	 * @param selectos
	 */
	public void cruzar0(Collection<Individuo> selectos) {
		Individuo primero = null;
		Individuo miPrimero = null;
		Individuo segundo = null;
		Individuo nuevo = null;
		Iterator<Individuo> i = selectos.iterator();

		if (i.hasNext()) {
			primero = i.next();
			miPrimero = primero;

			// cruces en pares desde el principio
			while (i.hasNext()) {
				segundo = i.next();
				this.seleccionar();
				nuevo = operadorCruce.cruzar(primero, segundo);
				this.hijos.add(nuevo);
				primero = segundo;
			}
			// cruce entre el primero del grupo con el último.
			nuevo = operadorCruce.cruzar(miPrimero, segundo);
			this.hijos.add(nuevo);
		}
	}

	/*
	 * Nuevo Cruce Aleatorio.
	 */
	public void cruzar(Collection<Individuo> selectos) {

		if (selectos == null)
			throw new Error("No hay selección.");

		// Tamaño de población seleccionada
		int cantMejores = selectos.size();

		// Auxiliar de Individuos
		List<Individuo> individuos = new ArrayList<Individuo>(selectos);

		// Se inicializa la clase Random
		Random rand = new Random();
		rand.nextInt();

		for (int i = 0; i < cantMejores; i++) {

			// Se eligen a dos individuos (torneo "binario")
			int ind1 = rand.nextInt(cantMejores);
			int ind2 = rand.nextInt(cantMejores);

			// Nos aseguramos que no sean del mismo indice.
			int limite = 1;
			while (ind1 == ind2 && limite < 10) {
				ind2 = rand.nextInt(cantMejores);
				limite++;
			}

			Individuo individuo1 = individuos.get(ind1);
			Individuo individuo2 = individuos.get(ind2);
			Individuo hijo = null;
			System.out.println("&) Cruce N°"+i);
			System.out.println("++I1:"+individuo1);
			System.out.println("++I2:"+individuo2);
			// Se extrae los fitness de los correspondientes individuos
			hijo = operadorCruce.cruzar(individuo1, individuo2);
			this.hijos.add(hijo);
		}

	}

	public void evaluar() {
		for (Individuo i : this.individuos) {
			i.evaluar();
		}
	}

	/**
	 * Operación de seleccion de Individuos para cruzar.
	 * 
	 * @return individuos seleccionados
	 */
	public Collection<Individuo> seleccionar() {
		Collection<Individuo> selectos = this.operadorSeleccion
				.seleccionar(this);
		return selectos;
	}

	/**
	 * Función que obtiene el tamaño de la población
	 * 
	 * @return tamanho
	 */
	public int getTamanho() {
		return this.individuos.size();
	}

	public Collection<Individuo> getIndividuos() {
		return individuos;
	}

	public ArrayList<Individuo> getIndividuosToArray() {
		ArrayList<Individuo> a = new ArrayList<Individuo>(this.individuos);
		return a;
	}

	public void setIndividuos(List<Individuo> individuos) {
		this.individuos = individuos;
	}

	public Collection<Individuo> getHijos() {
		return hijos;
	}

	public void setHijos(List<Individuo> hijos) {
		this.hijos = hijos;
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

	@Override
	public String toString() {
		return "Poblacion [individuos="
				+ (individuos != null ? toString(individuos, individuos.size())
						: null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
