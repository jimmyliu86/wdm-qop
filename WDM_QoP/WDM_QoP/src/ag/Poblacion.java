package ag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	private Set<Individuo> individuos;

	/*
	 * Hijos de los individuos selectos
	 */
	private Set<Individuo> hijos;

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
	public Poblacion(Set<Individuo> individuos) {
		this.individuos = individuos;
		this.operadorSeleccion = new TorneoBinario();
		this.operadorCruce = new MiCruce();
		this.hijos = new HashSet<Individuo>();

	}

	/**
	 * Operación de cruce de Individuos de un conjunto selecto de individuos.
	 * <p>
	 * La operacion de cruce se realiza con los individuos ya seleccionados.
	 * </p>
	 * 
	 * @param selectos
	 */
	public void cruzar(Set<Individuo> selectos) {
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
	public Set<Individuo> seleccionar() {
		Set<Individuo> selectos = this.operadorSeleccion.seleccionar(this);
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

	public Set<Individuo> getIndividuos() {
		return individuos;
	}
	
	public ArrayList<Individuo> getIndividuosToArray() {
		ArrayList<Individuo> a = new ArrayList<Individuo>(this.individuos);
		return a;
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
