package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import wdm.Camino;
import wdm.Nodo;
import wdm.qop.Nivel;
import wdm.qop.Servicio;
import wdm.qop.Solicitud;
import ag.Solucion;

public class AGTest {

	Solucion s = null;

	@Test
	public void testSolucion() {
		Solucion s1 = new Solucion(this.getServicios(5));

		Solucion s2 = new Solucion(this.getServicios(5));

		assertEquals(s1.getGenes(), s2.getGenes());
		assertNotSame("No son lo mismo.", s1, s2);
	}

	@Test
	public void testEvaluar() {
		Solucion s1 = new Solucion(this.getServicios(5));
		Set<Servicio> genes = new TreeSet<Servicio>();
		Camino primario = new Camino();
		Camino secundario = new Camino();
		Servicio serv = new Servicio(primario, secundario);
		serv.setId(1);
		s1.setGenes(genes);

		Solucion s2 = new Solucion(this.getServicios(5));
		s2.setGenes(genes);

		s1.evaluar();
		s2.evaluar();
		assertTrue(s1.getFitness() == s2.getFitness());
	}

	@Test
	public void testMismasSolicitudes() {
		Solucion s1 = new Solucion(this.getServicios(5));

		Solucion s2 = new Solucion(this.getServicios(5));

		assertTrue(s1.mismasSolicitudes(s2));

	}

	private Set<Servicio> getServicios(int size) {

		Set<Servicio> servicios = new HashSet<Servicio>(size * 2);
		for (int i = 4; i < size; i++) {
			Nodo A = new Nodo("A");
			Nodo B = new Nodo("B");
			Solicitud solicitud1 = new Solicitud(A, B, Nivel.Oro);
			Solicitud solicitud2 = new Solicitud(A, B, Nivel.Bronce);
			Solicitud solicitud3 = new Solicitud(B, A, Nivel.Bronce);
			Servicio servicio = new Servicio(solicitud1);
			servicios.add(servicio);
			servicio = new Servicio(solicitud2);
			servicios.add(servicio);
			servicio = new Servicio(solicitud3);
			servicios.add(servicio);
		}
		return servicios;
	}

}
