package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import wdm.Camino;
import wdm.Red;
import wdm.qop.Caso;
import wdm.qop.Nivel;
import wdm.qop.Servicio;
import wdm.qop.Solicitud;
import ag.Solucion;

public class AGTest {

	Solucion s = null;

	@Test
	public void testSolucion() {
		Solucion s1 = new Solucion(this.getSolicitudes());

		Solucion s2 = new Solucion(this.getSolicitudes());

		assertEquals(s1.getGenes(), s2.getGenes());
		assertNotSame("No son lo mismo.", s1, s2);
	}

	@Test
	public void testEvaluar() {
		Solucion s1 = new Solucion(this.getSolicitudes());
		Set<Servicio> genes = new TreeSet<Servicio>();
		Camino primario = new Camino();
		Camino secundario = new Camino();
		Servicio serv = new Servicio(primario, secundario, Nivel.Oro);
		serv.setId(1);
		s1.setGenes(genes);

		Solucion s2 = new Solucion(this.getSolicitudes());
		s2.setGenes(genes);

		s1.evaluar();
		s2.evaluar();
		System.out.println(" $ " + s1.getFitness() + " - " + s2.getFitness()
				+ " $ ");
		assertTrue(s1.getFitness() == s2.getFitness());
	}

	@Test
	public void testMismasSolicitudes() {
		Solucion s1 = new Solucion(getSolicitudes());

		Solucion s2 = new Solucion(getSolicitudes());

		assertTrue(s1.mismasSolicitudes(s2));

	}

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	Red NSFNET;
	double[] probNiveles = { 0.4, 0.3, 0.3 };

	@Before
	public void setUp() throws Exception {
		NSFNET = em.find(Red.class, 123);
		NSFNET.inicializar();
	}

	private Set<Solicitud> getSolicitudes() {
		Caso prueba1 = em.find(Caso.class, "prueba1");
		Set<Solicitud> solicitudes = prueba1.getSolicitudes();
		return solicitudes;
	}

}
