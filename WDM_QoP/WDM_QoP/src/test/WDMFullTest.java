package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import wdm.CanalOptico;
import wdm.Red;
import wdm.qop.Caso;
import ag.Solucion;

public class WDMFullTest {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	Red NSFNET;
	double[] probNiveles = { 0.4, 0.3, 0.3 };

	@Before
	public void setUp() throws Exception {
		NSFNET = em.find(Red.class, 1);
	}

	@Test
	public void testRed() {
		NSFNET.imprimirRed();
		assertNotNull(NSFNET);
		//cargarPrueba("prueba2");
	}

	@Test
	public void testInicializar() {
		Set<CanalOptico> canales1 = NSFNET.getCanales();
		NSFNET.inicializar();
		Set<CanalOptico> canales2 = NSFNET.getCanales();
		assertTrue(canales1.size() == canales2.size());
	}

	@Test
	public void testProbNiveles() {

		assertTrue(probNiveles.length == 3);
		double total = probNiveles[0] + probNiveles[1] + probNiveles[2];
		assertTrue(total == 1.0);
	}

	@Test
	public void algoritmoGenetico() {
		// 0. Ya se cuenta con la red NSFNET.
		// 1. Obtener el caso de prueba (Solicitudes)
		Caso prueba1 = em.find(Caso.class, "prueba2");
		// 2. Crear las soluciones con las Solicitudes
		Solucion solucion = new Solucion(prueba1.getSolicitudes());
		solucion.evaluar();
		System.out.println(prueba1.getSolicitudes().toString());
		System.out.println(solucion.toString());

		// 3.
		assertTrue(prueba1.getSolicitudes().size() == 3);
	}

	/**
	 * Función para cargar un Caso de prueba en la Base de Datos.
	 * 
	 * @param nombre
	 */
	private void cargarPrueba(String nombre) {
		Caso prueba = new Caso(NSFNET, 3, probNiveles);
		// Un nombre = prueba1
		prueba.setNombre(nombre);

		em.getTransaction().begin();
		em.persist(prueba);
		em.getTransaction().commit();
	}

}
