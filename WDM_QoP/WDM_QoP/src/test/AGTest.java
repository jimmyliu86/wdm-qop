package test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import wdm.Red;
import wdm.qop.Caso;
import wdm.qop.Servicio;
import ag.Individuo;
import ag.Poblacion;
import ag.Solucion;

public class AGTest {

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

	@Test
	public void algoritmoGenetico() {

		System.out.println("Prueba Algoritmo Genetico.");
		// 0. Obtener Poblacion Inicial
		System.out.println("Población Inicial...");
		this.obtenerPoblacion();
		p.evaluar();
		this.imprimirGrafo("Primer_Mejor");
		int generacion = 0;
		
		while (generacion < 200) {

			System.out.println(" * Generación Nº " + generacion);
			// System.out.println("Evaluando...");
			// p.evaluar();
			// System.out.println("Fin Evaluacion.");
			System.out.println("Seleccionando...");
			Collection<Individuo> seleccionados = p.seleccionar();
			System.out.println("Fin Seleccion.");

			System.out.println("Cruzando...");
			p.cruzar(seleccionados);
			System.out.println("FIN Cruzando...");
			System.out.println("Imprimiendo...");
			System.out.println(p.toString());
			System.out.println("Fin Impresion.");
			generacion++;
			p.siguienteGeneracion();
		}
		p.evaluar();
		this.imprimirGrafo("Mejor_Camino");

		System.out.println("FIN Prueba Algoritmo Genetico.");
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
	
	private void imprimirGrafo(String id) {
		Solucion mejor = p.getMejor();
		String dir = "C:\\Users\\mrodas\\Desktop\\Descargas\\tesis";
		int i = 0;
		for (Servicio s : mejor.getGenes() ) {
			Poblacion.getRed().drawServicio(s, dir, id+"_"+s.getId()+"_"+i+"");
			i++;
		}
		
		Poblacion.getRed().utilizacion(dir, "");
	}

}
