package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
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
	public Poblacion p;

	@Before
	public void setUp() throws Exception {
		NSFNET = em.find(Red.class, 247);
		NSFNET.inicializar();
		boolean cargar = false;
		if (cargar)
			cargarPrueba("prueba", 2);

	}

	// @Test
	public void testRed() {
		System.out.println("Prueba RED.");
		NSFNET.imprimirRed();
		System.out.println("FIN Prueba RED.");
		assertNotNull(NSFNET);
		// cargarPrueba("prueba2");
	}

	@Test
	public void testInicializarRed() {
		Set<CanalOptico> canales1 = NSFNET.getCanales();
		NSFNET.inicializar();
		Set<CanalOptico> canales2 = NSFNET.getCanales();
		assertTrue(canales1.size() == canales2.size());
	}

	@Test
	public void testProbabilidadNiveles() {

		double total = probNiveles[0] + probNiveles[1] + probNiveles[2];
		assertTrue(total == 1.0);
	}

	@Test
	public void testSolucionEvaluacion() {

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
		// System.out.println(prueba1.getSolicitudes());
		// Se imprime Solución a las Solicitudes
		// System.out.println(solucion);

		// Evaluaciones
		assertNotNull(solucion);
		assertNotNull(solucion.getGenes());
		assertTrue(prueba1.getSolicitudes().size() == solucion.getGenes()
				.size());

		System.out.println("FIN Prueba Solucion.");
	}

	@Test
	public void testCruce() {

		System.out.println("Prueba Cruce ... Obteniendo Caso de Prueba. ");

		// 0. Obtener Poblacion Inicial
		this.obtenerPoblacion();

		System.out.println("Prueba Cruce... Selección. ");
		// p.evaluar();
		// 1. Se realiza la Seleccion
		Collection<Individuo> seleccionados = p.seleccionar();
		//Poblacion p2 = new Poblacion(seleccionados);
		// System.out.println(p2.toString());
		assertTrue("No son del mismo tamaño:" + seleccionados.size() + " y "
				+ p.getTamanho(), seleccionados.size() == p.getTamanho());

		System.out.println("---------------------------- ");
		// 2. Se realiza el Cruce.
		System.out.println("Prueba Cruce ... Cruzando... ");
		p.cruzar(seleccionados);

		System.out.println("... FIN Prueba Cruce.");

	}

	/*
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

	/*
	 * Funcion para obtener una cantidad de Individuos para la población Inicial
	 */
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

	/*
	 * Obtiene la población Inicial a partir de la Prueba cargada
	 */
	private void obtenerPoblacion() {

		// 0. Obtener Poblacion Inicial
		Set<Individuo> individuos = this.obtenerPrueba(4);
		assertTrue(individuos.size() == 4);

		System.out.println("----Población Inicial.---- ");
		// 1. Se crea la Poblacion Inicial
		p = new Poblacion(individuos);
		Poblacion.setRed(NSFNET);
		p.generarPoblacion();
		System.out.println(p.toString());
	}

}
