package ag.operadores.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ag.Individuo;
import ag.Poblacion;
import ag.Solucion;
import ag.operadores.OperadorSeleccion;

public class TorneoBinario implements OperadorSeleccion {

	@Override
	public Collection<Individuo> seleccionar(Poblacion poblacion) {

		if (poblacion == null)
			throw new Error("La poblacion no existe.");

		// Tamaño de población seleccionada
		int cantMejores = poblacion.getTamanho();

		// Auxiliar de Individuos
		ArrayList<Individuo> individuos = poblacion.getIndividuosToArray();

		// Cromosomas seleccionados
		List<Individuo> respuesta = new ArrayList<Individuo>();

		// Se inicializa la clase Random
		Random rand = new Random();
		rand.nextInt();

		for (int i = 0; i < cantMejores; i++) {

			// Se eligen a dos individuos (torneo "binario")
			int ind1 = rand.nextInt(cantMejores);
			int ind2 = rand.nextInt(cantMejores);

			// Nos aseguramos que no sean del mismo indice.
			int limite = 1;
			while (ind1 != ind2 && limite < 5) {
				ind2 = rand.nextInt(cantMejores);
				limite++;
			}

			Individuo individuo1 = individuos.get(ind1);
			Individuo individuo2 = individuos.get(ind2);

			// Se extrae los fitness de los correspondientes individuos
			double fitness1 = individuo1.evaluar();
			double fitness2 = individuo2.evaluar();
			boolean valor = true;

			// Competencia
			boolean evaluacion = false;
			int comparacion = comparar(individuo1, individuo2);

			if (comparacion < 0)
				evaluacion = true;
			else if (comparacion > 0)
				evaluacion = false;
			else {
				if (fitness1 >= fitness2) {
					evaluacion = true;
				} else {
					evaluacion = false;
				}
			}

			if (evaluacion) {
				// Ganó individuo 1
				valor = respuesta.add(individuo1);
			} else {
				// Ganó individuo 2
				valor = respuesta.add(individuo2);
			}
			if (!valor) {
				System.out.println("$$1#" + individuo1);
				System.out.println("$$2#" + individuo2);
				throw new Error("No funciona el equals de Solucion."
						+ respuesta);
			}
		}

		return respuesta;
	}

	private int comparar(Individuo individuo1, Individuo individuo2) {
		int respuesta = 0;
		int oro = ((Solucion) individuo1).getContadorFailOro();
		oro -= ((Solucion) individuo2).getContadorFailOro();
		int plata = ((Solucion) individuo1).getContadorFailPlata();
		plata -= ((Solucion) individuo2).getContadorFailPlata();

		if (oro == 0)
			if (plata == 0)
				respuesta = 0;
			else
				respuesta = plata;
		else
			respuesta = oro;

		return respuesta;
	}

}
