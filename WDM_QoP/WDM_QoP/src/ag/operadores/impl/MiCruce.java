package ag.operadores.impl;

import ag.Gen;
import ag.Solucion;
import ag.operadores.OperadorCruce;

public class MiCruce implements OperadorCruce {

	@Override
	public Solucion cruzar(Solucion s1, Solucion s2) {
		// TODO Implementar Metodo especifico de cruce
		Solucion hijo = new Solucion();

		//Gen[] genes1 = s1.genesToArray();
		//Gen[] genes2 = s2.genesToArray();

		// Comprobamos que la solicitudes sea la misma
		if (s1.getSolicitudes() != s2.getSolicitudes())
			throw new Error("Las solicitudes no coinciden");

		//se copian todos los genes que son iguales
		for (Gen g1 : s1.getGenes()) {
			for (Gen g2 : s2.getGenes()) {
				g1.getPrimario().equals(g2.getPrimario());
				hijo.getGenes().add(g1);
			}
		}

		return hijo;
	}

}
