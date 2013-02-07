package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import wdm.CanalOptico;
import wdm.Red;
import wdm.qop.Caso;
import ag.Individuo;
import ag.Poblacion;
import ag.Solucion;

public class WDMFullTest {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	Red NSFNET;
	double[] probNiveles = { 0.4, 0.3, 0.3 };

	@Before
	public void setUp() throws Exception {
		NSFNET = em.find(Red.class, 247);
		NSFNET.inicializar();

	}

	// @Test
	public void testRed() {
		System.out.println("Prueba RED.");
		NSFNET.imprimirRed();
		System.out.println("FIN Prueba RED.");
		assertNotNull(NSFNET);
		// cargarPrueba("prueba2");
	}

	// @Test
	public void testInicializar() {
		Set<CanalOptico> canales1 = NSFNET.getCanales();
		NSFNET.inicializar();
		Set<CanalOptico> canales2 = NSFNET.getCanales();
		assertTrue(canales1.size() == canales2.size());
	}

	// @Test
	public void testProbNiveles() {

		double total = probNiveles[0] + probNiveles[1] + probNiveles[2];
		assertTrue(total == 1.0);
	}

	// @Test
	public void testSolucion() {

		System.out.println("Prueba Solucion.");

		// 0. Ya se cuenta con la red NSFNET.
		// 1. Obtener el caso de prueba (Solicitudes
		Caso prueba1 = em.find(Caso.class, "test1");
		// 2. Crear las soluciones con las Solicitudes
		Solucion solucion = new Solucion(prueba1.getSolicitudes());
		solucion.random();
		// 3. Se evaluarn las Soluciones.
		solucion.evaluar();

		// Se imprimen Solicitudes
		System.out.println(prueba1.getSolicitudes());
		// Se imprime Solución a las Solicitudes

		System.out.println("FIN Prueba Solucion.");
		// Evaluaciones
		assertNotNull(solucion);
		assertNotNull(solucion.getGenes());
		assertTrue(prueba1.getSolicitudes().size() == solucion.getGenes()
				.size());

		System.out.println(solucion);
	}

	// @Test
	public void algoritmoGenetico() {

		// 2. Crear las soluciones con las Solicitudes

		Set<Individuo> individuos = this.obtenerPrueba(4);
		assertTrue(individuos.size() == 4);

		System.out.println("Prueba Algoritmo Genetico.");
		Poblacion p = new Poblacion(individuos);
		p.generarPoblacion();
		System.out.println("Evaluando...");
		p.evaluar();
		System.out.println("Fin Evaluacion.");
		// System.out.println("Seleccionando...");
		Collection<Individuo> seleccionados = p.seleccionar();
		// System.out.println("Fin Seleccion.");

		int i1 = 0;
		for (Individuo i : seleccionados) {
			i1++;
			System.out.println("@Individuo " + i1 + " :" + i.toString());
		}
		System.out.println("Cantidad Selecionados:" + seleccionados.size());
		System.out.println("FIN Prueba Algoritmo Genetico.");

		// assertTrue(seleccionados.size() == 2);

		System.out.println("Cruzando...");
		p.cruzar(seleccionados);
		System.out.println("FIN Cruzando...");
		assertNotNull("$Hay cruce.", p);
	}

	@Test
	public void testCruce() {

		// 2. Crear las soluciones con las Solicitudes

		System.out.println("Prueba Cruce ... Obteniendo Caso de Prueba. ");
		Set<Individuo> individuos = this.obtenerPrueba(4);
		assertTrue(individuos.size() == 4);

		System.out.println("Prueba Cruce ... Continua. ");
		Poblacion p = new Poblacion(individuos);
		p.generarPoblacion();
		
		System.out.println("Prueba Cruce ... Población Inicial. ");
		System.out.println(p.toString());
		p.evaluar();
		Collection<Individuo> seleccionados = p.seleccionar();
		Poblacion p2 = new Poblacion(seleccionados);
		assertTrue("No son del mismo tamaño:" + seleccionados.size() + " y "
				+ p.getTamanho(), seleccionados.size() == p.getTamanho());
		
		System.out.println("Prueba Cruce ... Seleccionados. ");
		System.out.println(p2.toString());
		
		System.out.println("---------------------------- ");
		System.out.println("Prueba Cruce ... Cruzando... ");
		p.cruzar(seleccionados);
		System.out.println("... FIN Prueba Cruce.");
	}

	/**
	 * Función para cargar un Caso de prueba en la Base de Datos.
	 * 
	 * @param nombre
	 */
	private void cargarPrueba(String nombre, int cantidad) {

		for (int i = 1; i <= cantidad; i++) {
			Caso prueba = new Caso(NSFNET, 2, probNiveles);
			String nombreUtil = nombre + i;
			prueba.setNombre(nombreUtil);
			em.getTransaction().begin();
			em.persist(prueba);
			em.getTransaction().commit();
		}
	}

	private Set<Individuo> obtenerPrueba(int cantidad) {
		Set<Individuo> individuos = new HashSet<Individuo>(cantidad);

		Caso prueba1 = em.find(Caso.class, "test1");

		for (int i = 0; i < cantidad; i++) {
			Solucion solucion = new Solucion(prueba1.getSolicitudes());
			System.out.println(">" + i + ">" + solucion.getGenes().toString());

			individuos.add(solucion);
		}

		return individuos;
	}

}
