package ag.operadores.impl;

import java.util.Iterator;

import wdm.Camino;
import wdm.Nodo;
import wdm.Salto;
import wdm.qop.Servicio;
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
	public Solucion cruzar(Solucion s1, Solucion s2) {

		/*
		 * Comprobamos que las solicitudes sea las mismas ¿Es necesaria esta
		 * comprobación?
		 */
		if (!s1.mismasSolicitudes(s2))
			throw new Error("Las solicitudes no coinciden");

		Solucion hijo = new Solucion(s1.getGenes());

		Camino auxiliar = null;
		Camino nuevoPrimario = null;
		Camino nuevoSecundario = null;
		int size1 = s1.getGenes().size();
		/*
		 * Cálculos del Nuevo Camino Primario y del Nuevo Camino Secundario.
		 */
		for (int i = 0; i < size1; i++) {
			Servicio gen1 = new Servicio(s1.getGenes().first().getSolicitud());
			Servicio gen2 = new Servicio(s2.getGenes().first().getSolicitud());
			gen1 = s1.getGenes().pollFirst();
			gen2 = s2.getGenes().pollFirst();
			Camino primario1 = gen1.getPrimario();
			Camino primario2 = gen2.getPrimario();

			auxiliar = new Camino(primario1.getOrigen());
			auxiliar.setDestino(primario1.getDestino());
			

			/*
			 * P1. Se copian los Caminos cuyos genes son iguales entre los genes
			 * de los padres. Se recorre el primario más corto de entre los
			 * padres.
			 */
			if (primario1.getSaltos().size() <= primario2.getSaltos().size()) {

				for (Salto salto : primario1.getSaltos()) {
					if (primario2.getSaltos().contains(salto)) {
						auxiliar.addSalto(salto);
					} else {
						auxiliar.addNull();
					}
				}

			} else {
				for (Salto salto : primario2.getSaltos()) {
					if (primario1.getSaltos().contains(salto)) {
						auxiliar.addSalto(salto);
					} else {
						auxiliar.addNull();
					}
				}
			}

			// se carga el nuevo Camino Primario.
			nuevoPrimario = new Camino(primario1.getOrigen());
			nuevoSecundario = new Camino(primario1.getOrigen());
			// El inicio auxiliar ya se asigna al primer Nodo de Origen.
			Nodo inicio = auxiliar.getOrigen();
			Nodo fin = null;
			int longitudDeOnda = -1;

			Iterator<Salto> saltos = auxiliar.getSaltos().iterator();

			while (saltos.hasNext()) {
				Salto salto = saltos.next();

				/*
				 * Caso null: los genes con valor null son los que se deben
				 * sustituir por nuevos caminos (a través de SPD).
				 */
				if (salto == null) {
					/*
					 * Primero: Se debe obtener el Nodo inicio y el Nodo fin
					 */
					while (salto == null && saltos.hasNext()) {
						salto = saltos.next();
					}
					if (saltos.hasNext()) { // sigue por el medio del Servicio
						fin = salto.getEnlace().getExtremoA();
						longitudDeOnda = salto.getEnlace().getLongitudDeOnda();
					} else {
						// Llegó al último Nodo y no hay camino
						fin = auxiliar.getDestino();
					}

					/*
					 * P2: Se utiliza el algoritmo Shortest Path Disjktra (SPD)
					 * desde el Nodo inicio al Nodo fin para completar los
					 * caminos faltantes.
					 */
					Camino subCamino = inicio.dijkstra(fin);
					// Se agrega el camino encontrado al Nuevo Camino Primario
					for (Salto saltoAux : subCamino.getSaltos()) {
						saltoAux.setEnlace(longitudDeOnda);
						longitudDeOnda = salto.getEnlace().getLongitudDeOnda();
						nuevoSecundario.addSalto(saltoAux);
					}
				} else {
					/*
					 * Caso not null: los genes not null (que son iguales entre
					 * los padres) se agregan directamente. LDO ya está
					 * asignado.
					 */
					salto.getEnlace().setLongitudDeOnda(longitudDeOnda);
					longitudDeOnda = salto.getEnlace().getLongitudDeOnda();
					nuevoPrimario.addSalto(salto);
					// se asigna como inicio del siguiente el fin de este salto.
					inicio = salto.getEnlace().getExtremoB();
				}
			}

			/*
			 * Cargar Nuevo Secundario: Realizar algoritmo Shortest Path
			 * Disjktra (SPD) desde el Nodo inicio al Nodo fin.
			 */
			Nodo nodoA = primario1.getOrigen();
			Nodo nodoB = primario1.getDestino();
			Camino subCamino = nodoA.dijkstra(nodoB);
			longitudDeOnda = -1;
			// Se agrega el camino encontrado al Nuevo Camino Secundario
			for (Salto salto : subCamino.getSaltos()) {
				salto.setEnlace(longitudDeOnda);
				longitudDeOnda = salto.getEnlace().getLongitudDeOnda();
				nuevoSecundario.addSalto(salto);
			}
			Servicio newServicio = new Servicio(nuevoPrimario, nuevoSecundario);
			/*
			 * Cargar Genes al hijo.
			 */
			hijo.getGenes().add(newServicio);
		}

		return hijo;
	}
}
