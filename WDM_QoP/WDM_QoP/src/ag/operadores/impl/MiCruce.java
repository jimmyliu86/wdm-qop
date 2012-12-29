package ag.operadores.impl;

import java.util.ArrayList;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Nodo;
import wdm.Salto;
import ag.Gen;
import ag.Solucion;
import ag.operadores.OperadorCruce;

/**
 * Implementación específica del Cruce mencionado en paper de cnunez.
 * <p>
 * Este cruce se define asi: dada dos soluciones de entrada (s1 y s2), en una
 * primera parte se copian los Caminos que son iguales entre los genes de los
 * padres.
 * </p>
 * <p>
 * Luego, en una segunda parte, se utiliza el algoritmo SPD para completar los
 * caminos faltantes.
 * </p>
 * <p>
 * Finalmente, en una tercera parte se asignan las longitudes de onda, la
 * primera de forma aleatoria, y luego se trata de mantener dicha longitud de
 * onda. En el caso que no se encuentra disponible la longitud de onda se
 * procederá a elegir otro randomicamente. Así sucecivamente hasta llegar al
 * final del camino.
 * </p>
 */
public class MiCruce implements OperadorCruce {

	@Override
	public Solucion cruzar(Solucion s1, Solucion s2) {

		// Comprobamos que las solicitudes sea las mismas
		if (!s1.mismasSolicitudes(s2))
			throw new Error("Las solicitudes no coinciden");

		Solucion hijo = new Solucion(s1.getSolicitudes());
		Gen gen1 = new Gen();
		Gen gen2 = new Gen();
		ArrayList<CanalOptico> auxiliar = null;
		ArrayList<CanalOptico> nuevoPrimario = null;
		ArrayList<CanalOptico> nuevoSecundario = null;
		/*
		 * Cálculos del Nuevo Camino Primario y del Nuevo Camino Secundario.
		 */
		for (int i = 0; i < s1.getGenes().size(); i++) {
			gen1 = s1.getGenes().get(i);
			gen2 = s2.getGenes().get(i);
			ArrayList<CanalOptico> primarioGen1 = gen1.getPrimario();
			ArrayList<CanalOptico> primarioGen2 = gen2.getPrimario();

			auxiliar = new ArrayList<CanalOptico>(primarioGen1.size());
			// Se recorre el primario más corto
			if (primarioGen1.size() <= primarioGen2.size()) {

				for (CanalOptico co : primarioGen1) {
					if (primarioGen2.contains(co))
						auxiliar.add(co);
					else
						auxiliar.add(null);
				}

			} else {
				for (CanalOptico co : primarioGen2) {
					if (primarioGen1.contains(co))
						auxiliar.add(co);
					else
						auxiliar.add(null);
				}
			}

			// se carga el nuevo Camino Primario.
			nuevoPrimario = new ArrayList<CanalOptico>(auxiliar.size());
			nuevoSecundario = new ArrayList<CanalOptico>(auxiliar.size());
			Nodo inicio = null;
			Nodo fin = null;

			for (int j = 0; j < auxiliar.size(); j++) {
				/*
				 * Caso null: los genes con valor null son los que se deben
				 * sustituir por nuevos caminos (a través de SPD).
				 */
				if (auxiliar.get(j) == null) {
					/*
					 * Primero: Se debe obtener el Nodo inicio y el Nodo fin
					 */
					if (j > 0)
						inicio = auxiliar.get(j - 1).getExtremoB();
					else
						inicio = s1.getSolicitudes().get(j).getOrigen();

					while (auxiliar.get(j) == null) {
						j++;
					}
					// Nodo Fin
					fin = auxiliar.get(j).getExtremoA();

					/*
					 * Segundo: Realizar algoritmo Shortest Path Disjktra (SPD)
					 * desde el Nodo inicio al Nodo fin.
					 */
					Camino subCamino = inicio.dijkstra(fin);
					// Se agrega el camino encontrado al Nuevo Camino Primario
					for (Salto salto : subCamino.getSaltos()) {
						nuevoPrimario.add(salto.getCanal());
					}

				} else {
					/*
					 * Caso not null: los genes not null son iguales entre los
					 * padres y por ende se agregan directamente.
					 */
					nuevoPrimario.add(auxiliar.get(j));
				}
			}

			/*
			 * Cargar Nuevo Secundario: Realizar algoritmo Shortest Path
			 * Disjktra (SPD) desde el Nodo inicio al Nodo fin.
			 */
			Nodo nodoA = s1.getSolicitudes().get(i).getOrigen();
			Nodo nodoB = s1.getSolicitudes().get(i).getDestino();
			Camino subCamino = nodoA.dijkstra(nodoB);
			// Se agrega el camino encontrado al Nuevo Camino Primario
			for (Salto salto : subCamino.getSaltos()) {
				nuevoSecundario.add(salto.getCanal());
			}
			Gen nuevoGen = new Gen(nuevoPrimario, nuevoSecundario);
			/*
			 * Cargar Genes al hijo.
			 */
			hijo.getGenes().add(i, nuevoGen);
		}
		return hijo;
	}
}
