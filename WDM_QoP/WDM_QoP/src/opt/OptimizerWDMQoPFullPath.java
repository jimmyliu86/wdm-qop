package opt;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Nodo;
import wdm.Red;
import wdm.Salto;
import wdm.qop.Nivel;
import wdm.qop.Servicio;
import wdm.qop.Solicitud;


/**
 * Clase de prueba de persistencia.
 * @author albert
 *
 */
public class OptimizerWDMQoPFullPath {
	private static String [] nodos = {"A","B","C","D","E","F","G","H"};
	private static String [] aristas = {"AB","AC","BC","BH","CD","DE","DG","EF","FG","GH"};
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	
	public static void main(String args []){
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("genRedes")) genRedes();
			if(args[0].equalsIgnoreCase("testNetwork")) testNetworkPersistence();
		}
	}
	
	
	public static void genRedes(){
		/*arpanet*/
		int [] [] arpanet_enlaces = {

		{1,2,1},{1,6,1},{2,3,1},{3,4,1},{3,8,1},{4,5,1},{5,6,1},{6,7,1},{6,11,1},{7,8,1},{8,20,1},
		{8,9,1},{9,10,1},{10,21,1},{10,12,1},{11,12,1},{12,13,1},{13,14,1},{14,15,1},{15,16,1},{16,17,1},{17,18,1},
		{18,19,1},{18,21,1},{19,20,1},
		};
		persistNet(21,arpanet_enlaces, "arpanet");
		
		/*BELLCOREYERSE*/
		int [] [] bellcoreyerse_enlaces = {

		{1,2,1},{1,6,1},{1,7,1},{1,8,1},{1,13,1},{2,5,1},{2,8,1},{3,4,1},{3,5,1},{4,5,1},{4,11,1},
		{5,8,1},{5,9,1},{6,7,1},{6,14,1},{6,15,1},{7,13,1},{8,9,1},{8,12,1},{10,13,1},{10,11,1},{10,12,1},
		{11,12,1},{12,13,1},{13,14,1},{13,15,1},{14,15,1}
		
		};
		persistNet(15,bellcoreyerse_enlaces, "BELLCOREYERSE");
		
		/*chinaNet*/
		int [] [] chinaNet_enlaces = {

		{1,2,1},{2,3,1},{2,8,1},{3,4,1},{4,5,1},{5,6,1},{5,7,1},{7,8,1},{8,9,1},{8,12,1},{8,13,1},
		{9,10,1},{9,11,1},{10,11,1},{10,23,1},{10,24,1},{11,23,1},{11,12,1},{12,23,1},{12,22,1},{12,27,1},{12,21,1},
		{12,20,1},{12,13,1},{13,20,1},{13,14,1},{14,15,1},{14,16,1},{14,17,1},{15,20,1},{15,29,1},{15,19,1},{15,16,1},
		{16,19,1},{17,18,1},{18,19,1},{18,31,1},{19,30,1},{19,35,1},{19,31,1},{20,28,1},{21,22,1},{21,28,1},{22,23,1},
		{22,26,1},{23,24,1},{23,25,1},{24,25,1},{24,41,1},{25,41,1},{25,54,1},{25,26,1},{26,39,1},{26,27,1},{27,58,1},
		{27,38,1},{28,38,1},{28,29,1},{29,37,1},{29,30,1},{30,36,1},{31,32,1},{31,33,1},{32,33,1},{33,35,1},{34,35,1},
		{34,61,1},{35,36,1},{35,62,1},{35,61,1},{36,37,1},{37,38,1},{38,58,1},{39,40,1},{39,56,1},{39,57,1},{39,59,1},
		{40,56,1},{40,41,1},{41,42,1},{41,54,1},{42,52,1},{42,43,1},{43,50,1},{43,44,1},{44,49,1},{44,45,1},{45,46,1},
		{46,47,1},{46,49,1},{47,48,1},{48,49,1},{48,51,1},{49,50,1},{50,51,1},{50,53,1},{50,52,1},{51,53,1},{52,54,1},
		{53,55,1},{54,56,1},{55,56,1},{56,57,1},{57,58,1},{58,65,1},{58,66,1},{59,65,1},{59,60,1},{60,64,1},{60,62,1},
		{60,61,1},{61,63,1},{62,64,1},{62,63,1},{63,64,1},{64,68,1},{64,67,1},{65,66,1},{66,68,1},{66,67,1},{67,68,1}

		};
		persistNet(68,chinaNet_enlaces, "chinaNet");
		
		/*eufrance*/
		int [] [] eufrance_enlaces = {

		{1,2,1},{1,4,1},{2,5,1},{3,4,1},{3,9,1},{4,5,1},{4,7,1},{5,6,1},{5,7,1},{6,7,1},{6,16,1},
		{7,18,1},{7,8,1},{8,15,1},{8,14,1},{8,9,1},{9,13,1},{9,10,1},{10,12,1},{10,11,1},{11,12,1},{12,13,1},
		{12,26,1},{13,14,1},{13,25,1},{14,15,1},{14,25,1},{15,16,1},{15,18,1},{15,24,1},{16,17,1},{16,18,1},{17,19,1},
		{18,22,1},{18,23,1},{18,24,1},{18,35,1},{19,20,1},{19,22,1},{20,21,1},{20,22,1},{21,23,1},{23,41,1},{24,38,1},
		{25,26,1},{25,34,1},{26,27,1},{26,28,1},{27,32,1},{28,32,1},{28,29,1},{29,30,1},{30,31,1},{32,33,1},{33,36,1},
		{33,35,1},{34,35,1},{35,36,1},{35,37,1},{35,38,1},{37,38,1},{37,39,1},{38,39,1},{39,40,1},{40,41,1},{40,43,1},
		{41,42,1},{41,43,1},{42,43,1},
		
		};
		persistNet(43,eufrance_enlaces, "eufrance");
		
		/*eugerman*/
		int [] [] eugerman_enlaces = {

		{1,4,1},{1,6,1},{2,4,1},{2,3,1},{3,5,1},{4,5,1},{5,9,1},{6,8,1},{6,7,1},{7,8,1},{7,13,1},
		{7,12,1},{7,9,1},{8,13,1},{9,12,1},{9,14,1},{9,10,1},{10,11,1},{11,15,1},{12,13,1},{12,14,1},{14,15,1},
		{14,17,1},{15,16,1},{16,17,1},
		};
		persistNet(17,eugerman_enlaces, "eugerman");
	}
	
	public static void testNetworkPersistence(){
		emf = Persistence.createEntityManagerFactory("tesis");
		em = emf.createEntityManager();
		
		HashMap<String,Nodo> nodoMap = new HashMap<String,Nodo>();
		
		/* *** Se crea integramente la Red con sus nodos y enlaces*** */
		/* Creacion de Nodos */
		
		Red red = new Red();
		em.getTransaction().begin();
		for(String label: nodos){
			Nodo nodo = new Nodo();
			nodo.setLabel(label);
			nodoMap.put(label, nodo);
			red.addNodo(nodo);
		}
		em.persist(red);
		em.getTransaction().commit();
		
		/*Creando Canales Opticos apartir de su especificacion (coSpec)*/
		em.getTransaction().begin();
		for(String coSpec : aristas){
			Nodo a = nodoMap.get(""+coSpec.charAt(0));
			Nodo b = nodoMap.get(""+coSpec.charAt(1));
			
			CanalOptico canal = new CanalOptico(a,b,1,3);
			
			em.persist(canal);
			red.addCanal(canal);
		}
		em.persist(red);
		em.getTransaction().commit();
		
		for ( CanalOptico canal: red.getCanales()){
			em.getTransaction().begin();
			canal.getExtremoA().addCanal(canal);
			em.getTransaction().commit();
			em.getTransaction().begin();
			canal.getExtremoB().addCanal(canal);
			em.getTransaction().commit();
		}
		em.persist(red);
		
		/*
		Red red = em.find(Red.class,1);
		for(Nodo n : red.getNodos()){
			nodoMap.put(n.getLabel(),n);
		}		
		*/
		/*
		 * Creando Solicitud A-F Oro
		 * Creando Servicio para la solicitud {A,F,Oro}
		 * Buscando el camino m�s corto desde A a F (primario)
		 * Buscando el segundo camino de A a F.
		 */
		
		Nodo origen = nodoMap.get("A");
		Nodo destino = nodoMap.get("F");
		
		Solicitud solicitud = new Solicitud(origen, destino, Nivel.Oro);
		em.persist(solicitud);
		
		/*
		Solicitud solicitud = em.find(Solicitud.class, new Long(48));
		Nodo origen = solicitud.getOrigen();
		Nodo destino = solicitud.getDestino();
		*/
		
		Servicio servicio = new Servicio(solicitud); 
		
		
		System.out.println("Buscando camino primario");
		Camino primario = origen.dijkstra(destino);
		primario.bloquearNodos();
		
		System.out.println("Primario : " + primario.getDistancia());
		System.out.print("A");
		Nodo actual = origen;
		for( Salto s : primario.getSaltos()){
			actual = s.getCanal().getOtroExtremo(actual);
			System.out.print("-"+actual.getLabel());
		}
		
		System.out.println();
		
		System.out.println("Buscando camino alternativo");
		Camino alternativo = origen.dijkstra(destino);
		System.out.println("Alternativo : " + alternativo.getDistancia());
		System.out.print("A");
		actual = origen;
		for( Salto s : alternativo.getSaltos()){
			actual = s.getCanal().getOtroExtremo(actual);
			System.out.print("-"+actual.getLabel());
		}
		
		servicio.setPrimario(primario);
		servicio.setAlternativo(alternativo);
		
		em.getTransaction().begin();
		em.persist(servicio);
		em.getTransaction().commit();
	}
	
	public static void persistNet(int nodos, int [] [] enlaces, String nombre){
		
		HashMap<String,Nodo> nodoMap = new HashMap<String,Nodo>();
		Red red = new Red();
		red.setNombre(nombre);
		
		em.getTransaction().begin();
		for(int i = 1; i <= nodos; i++){
			Nodo nodo = new Nodo();
			nodo.setLabel(""+i);
			nodoMap.put(""+i, nodo);
			red.addNodo(nodo);
		}
		em.persist(red);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		for(int i = 0; i < enlaces.length; i++){
			Nodo a = nodoMap.get(""+enlaces[i][0]);
			Nodo b = nodoMap.get(""+enlaces[i][1]);
			CanalOptico canal = new CanalOptico(a,b,1,3);
			a.addCanal(canal);
			b.addCanal(canal);
			canal.setCosto(enlaces[i][2]);
			red.addCanal(canal);			
		}
		em.persist(red);
		em.getTransaction().commit();
	}
}
