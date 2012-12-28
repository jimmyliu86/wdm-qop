package ag.operadores.impl;

import java.util.ArrayList;

import wdm.CanalOptico;
import ag.Gen;
import ag.Solucion;
import ag.operadores.OperadorCruce;

public class MiCruce implements OperadorCruce {

	// TODO Implementar Metodo especifico de cruce.
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
		// se copian todos los genes cuyos caminos son iguales
		for (int i = 0; i < s1.getGenes().size(); i++) {
			gen1 = s1.getGenes().get(i);
			gen2 = s2.getGenes().get(i);
			ArrayList<CanalOptico> primarioGen1 = gen1.getPrimario();
			ArrayList<CanalOptico> primarioGen2 = gen2.getPrimario();

			auxiliar = new ArrayList<>(primarioGen1.size());
			// Se recorre el primario más corto
			if (primarioGen1.size() <= primarioGen2.size()) {

				for (CanalOptico co : primarioGen1) {
					if (primarioGen2.contains(co)) {
						auxiliar.add(co);
					} else {
						auxiliar.add(null);
					}
				}
			} else {
				for (CanalOptico co : primarioGen2) {
					if (primarioGen1.contains(co)) {
						auxiliar.add(co);
					} else {
						auxiliar.add(null);
					}
				}
			}
		}

		// se carga el nuevo Camino Primario.
		nuevoPrimario = new ArrayList<>(auxiliar.size());
		for (int j = 0; j < auxiliar.size(); j++) {
			if (auxiliar.get(j) == null) {
				// Realizar algoritmo Shortest Path Disjktra (SPD) para restante
			} else {
				nuevoPrimario.add(auxiliar.get(j));
			}
		}
		return hijo;
	}
}
