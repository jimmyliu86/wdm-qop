package ag.operadores.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import wdm.Camino;
import wdm.Nodo;
import wdm.Salto;
import wdm.qop.Exclusividad;
import wdm.qop.Nivel;
import wdm.qop.Servicio;
import ag.Individuo;
import ag.Poblacion;
import ag.Solucion;
import ag.operadores.OperadorCruce;

/**
 * Implementación específica del Cruce mencionado en paper de cnunez.
 * <p>
 * Este cruce se define asi: dada dos soluciones de entrada (s1 y s2), en una
 * primera parte (P1) se copian los Caminos cuyos genes son iguales entre los
 * genes de los padres.
 * </p>
 * <p>
 * Luego, en una segunda parte (P2), se utiliza el algoritmo SPD para completar
 * los caminos faltantes.
 * </p>
 * <p>
 * Finalmente, en una tercera parte (P3) se asignan las longitudes de onda, la
 * primera de forma aleatoria, y luego se trata de mantener dicha longitud de
 * onda. En el caso que no se encuentra disponible la longitud de onda se
 * procederá a elegir otro randomicamente. Así sucecivamente hasta llegar al
 * final del camino.
 * </p>
 */
public class MiCruce implements OperadorCruce {

	@Override
	public Individuo cruzar(Individuo i1, Individuo i2) {
		Solucion s1 = (Solucion) i1;
		Solucion s2 = (Solucion) i2;

		System.out.println("---------------------------");
		System.out.println("@Padre1@" + s1 + "@Padre1@.");
		System.out.println("@Padre2@" + s2 + "@Padre2@.");

		Collection<Servicio> hijoAux = new ArrayList<Servicio>();
		Solucion hijo = new Solucion();

		Set<Nodo> primeros = new HashSet<Nodo>();
		List<Nodo> iguales = new ArrayList<Nodo>();

		Camino nuevoPrimario = null;
		Iterator<Servicio> iterador1 = s1.getGenes().iterator();
		Iterator<Servicio> iterador2 = s2.getGenes().iterator();

		Servicio gen1 = null;
		Servicio gen2 = null;

		/*
		 * Cálculos del Nuevo Camino Primario y del Nuevo Camino Secundario.
		 */
		while (iterador1.hasNext() && iterador2.hasNext()) {

			iguales.clear();
			gen1 = iterador1.next();
			gen2 = iterador2.next();
			Camino primario1 = gen1.getPrimario();
			Camino primario2 = gen2.getPrimario();
			Nivel nivel = gen1.getSolicitud().getNivel();

			Exclusividad exclusividadPrimario = gen1.getSolicitud()
					.getExclusividadPrimario();

			Nodo nodo = primario1.getOrigen();
			primeros.add(nodo);
			/*
			 * P1. Se copian los Nodos iguales entre los genes de los padres.
			 */
			for (Salto salto : primario1.getSaltos()) {
				nodo = salto.getCanal().getOtroExtremo(nodo);
				primeros.add(nodo);
			}

			nodo = primario2.getOrigen();

			for (Salto salto : primario2.getSaltos()) {
				nodo = salto.getCanal().getOtroExtremo(nodo);
				if (primeros.contains(nodo)) {
					iguales.add(nodo);
				}
			}

			Nodo inicio = primario1.getOrigen();

			// se carga el nuevo Camino Primario.
			nuevoPrimario = new Camino(inicio);

			for (Nodo next : iguales) {

				if (nuevoPrimario.contiene(next))
					continue;

				Camino subCamino = inicio.dijkstra(next, exclusividadPrimario);

				if (subCamino == null) {
					String dir = "C:\\Users\\mrodas\\Desktop\\Descargas\\tesis";
					Poblacion.getRed().drawServicio(gen1, dir, "cruce_error_a");
					Poblacion.getRed().drawServicio(gen2, dir, "cruce_error_b");
					Poblacion.getRed().utilizacion(dir, "");

					System.err.println("Desde " + inicio + " hasta " + next);
					System.err.println(nuevoPrimario);

					System.exit(1);
				}

				nuevoPrimario.anexar(subCamino);
				subCamino.bloquearNodos();
				inicio = next;
			}

			nuevoPrimario.desbloquearNodos();

			Servicio newServicio = new Servicio(gen1.getSolicitud());
			newServicio.setPrimario(nuevoPrimario);
			newServicio.setPrimario();

			/*
			 * Cargar Nuevo Secundario: Realizar algoritmo Shortest Path
			 * Disjktra (SPD) desde el Nodo inicio al Nodo fin.
			 */
			if (nivel != Nivel.Bronce) {
				newServicio.buscarAlternativo();
				newServicio.setAlternativo();
			}
			/*
			 * Cargar Genes al hijo.
			 */
			hijoAux.add(newServicio);
		}

		Collection<Servicio> aux = new TreeSet<Servicio>(hijoAux);
		hijo.setGenes(aux);

		System.out.println(" #Hijo#" + hijo + "#Hijo.#");
		System.out.println("---------------------------");

		return hijo;
	}
}
