package opt;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Nodo;
import wdm.Red;
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
	private static String [] aristas = {"AB","AC","BD","BC","CD","DE","DH","DG","EF","GH"};
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public static void main(String args []){
		OptimizerWDMQoPFullPath opt = new OptimizerWDMQoPFullPath();
		
		opt.test();
	}
	
	public void test(){
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
		
		Camino primario = origen.busquedaAnchura(destino);
		primario.bloquearEnlaces();
				
		Camino alternativo = origen.busquedaAnchura(destino);
		alternativo.reservarEnlaces(servicio);
		
		
		em.getTransaction().begin();
		em.persist(servicio);
		em.getTransaction().commit();
	}
}
