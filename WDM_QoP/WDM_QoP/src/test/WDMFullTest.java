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

public class WDMFullTest {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	Red NSFNET;

	
	@Before
	public void setUp() throws Exception {
		NSFNET = em.find(Red.class, 1);
		
	}

	@Test
	public void testRed() {
		NSFNET.imprimirRed();
		assertNotNull(NSFNET);
	}

	@Test
	public void testInicializar() {
		Set<CanalOptico> canales1 = NSFNET.getCanales();
		NSFNET.inicializar();
		Set<CanalOptico> canales2 = NSFNET.getCanales();
		assertTrue(canales1.size() == canales2.size());
	}

}
