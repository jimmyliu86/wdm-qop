package opt;

import wdm.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase de prueba de persistencia.
 * @author albert
 *
 */
public class OptimizerWDMQoPFullPath {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public static void main(String args []){
		OptimizerWDMQoPFullPath opt = new OptimizerWDMQoPFullPath();
		
		opt.test();
	}
	
	public void test(){
		emf = Persistence.createEntityManagerFactory("tesis");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Nodo origen = new Nodo();
		origen.setLabel("Origen");
		
		Nodo destino = new Nodo();
		destino.setLabel("Destino");
		
		Red red = new Red();
		red.addNodo(origen);
		red.addNodo(destino);
		
		CanalOptico canal = new CanalOptico(origen,destino,1,3);
		
		System.out.println("destino: " + canal.getDestino());
		
		red.addCanal(canal);

		em.persist(red);
		em.flush();
		em.getTransaction().commit();
	}
}
