package ag.operadores.impl;

import java.util.Random;

import ag.Individuo;
import ag.Poblacion;
import ag.operadores.OperadorSeleccion;

public class TorneoBinario implements OperadorSeleccion {

	@Override
	public Individuo[] seleccionar(Poblacion poblacion) {
		if (poblacion == null)
			throw new Error("La población no existe.");

		// Tamaño de población seleccionada
		int cantMejores = poblacion.getTamanho();

		// Cromosomas seleccionados
		Individuo[] mejores = new Individuo[cantMejores];

		// Se inicializa la clase Random
		Random rand = new Random();
		rand.nextInt();

		for (int i = 0; i < cantMejores; i++) {

			// Se eligen a dos individuos (torneo "binario")

			int ind1 = rand.nextInt(cantMejores);
			int ind2 = rand.nextInt(cantMejores);

			Individuo individuo1 = poblacion.getIndividuos().get(ind1);
			Individuo individuo2 = poblacion.getIndividuos().get(ind2);

			/*
			 * Nos aseguramos que los individuos seleccionados sean distintos.
			 */
			while (individuo1.equals(individuo2)) {
				ind2 = rand.nextInt(cantMejores);
				individuo2 = poblacion.getIndividuos().get(ind2);
			}

			// Se extrae los fitness de los correspondientes individuos
			double fitness1 = individuo1.evaluar();
			double fitness2 = individuo2.evaluar();

			// Competencia
			if (fitness1 >= fitness2) {
				// Ganó individuo 1
				mejores[i] = individuo1;
			} else {
				// Ganó individuo 2
				mejores[i] = individuo2;
			}
		}

		return mejores;
	}

}
