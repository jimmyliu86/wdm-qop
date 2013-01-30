package ag.operadores.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ag.Individuo;
import ag.Poblacion;
import ag.operadores.OperadorSeleccion;

public class TorneoBinario implements OperadorSeleccion {

	@Override
	public Set<Individuo> seleccionar(Poblacion poblacion) {

		if (poblacion == null)
			throw new Error("La poblacion no existe.");

		// Tama�o de población seleccionada
		int cantMejores = poblacion.getTamanho();
		System.out.println("cantidad:" +cantMejores);
		ArrayList<Individuo> individuos = poblacion.getIndividuosToArray();

		// Cromosomas seleccionados
		Set<Individuo> respuesta = new HashSet<Individuo>();

		// Se inicializa la clase Random
		Random rand = new Random();
		rand.nextInt();

		for (int i = 0; i < cantMejores; i++) {

			// Se eligen a dos individuos (torneo "binario")

			int ind1 = rand.nextInt(cantMejores);
			int ind2 = rand.nextInt(cantMejores);

			Individuo individuo1 = individuos.get(ind1);
			Individuo individuo2 = individuos.get(ind2);

			/*
			 * Nos aseguramos que los individuos seleccionados sean distintos.
			 */
			int cont = 0;
			while (individuo1.equals(individuo2) && cont<5) {
				cont ++;
				ind2 = rand.nextInt(cantMejores);
				individuo2 = individuos.get(ind2);
			}

			// Se extrae los fitness de los correspondientes individuos
			double fitness1 = individuo1.evaluar();
			double fitness2 = individuo2.evaluar();

			// Competencia
			if (fitness1 >= fitness2) {
				// Ganó individuo 1
				System.out.println("#Respuesta"+i+": "+individuo1);
				respuesta.add(individuo1);
			} else {
				// Ganó individuo 2
				System.out.println("#Respuesta"+i+": "+individuo2);
				respuesta.add(individuo2);
			}
		}

		return respuesta;
	}

}
