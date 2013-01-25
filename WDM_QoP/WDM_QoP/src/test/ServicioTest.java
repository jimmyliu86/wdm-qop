package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import wdm.Nodo;
import wdm.Red;
import wdm.qop.Nivel;
import wdm.qop.Servicio;
import wdm.qop.Solicitud;

public class ServicioTest {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tesis");
	private static EntityManager em = emf.createEntityManager();
	private static Red red = em.find(Red.class, 247);
	
	//Oficina Albert
	private String dirbase = "C:\\Users\\amadeoa\\Tesis\\graph\\ServicioTest";
	
	private Nodo getNodo(String label){
		for(Nodo n: red.getNodos()){
			if(n.getLabel().equalsIgnoreCase(label)) return n;
		}
		
		return null;
	}
	
	private Servicio crearServicio(String inicio, String fin, Nivel nivel){
		Nodo origen = getNodo(inicio);
		Nodo destino = getNodo(fin);
		Solicitud solicitud = new Solicitud(origen, destino, nivel);
		Servicio s = new Servicio(solicitud);
		
		s.random();
		red.utilizacion(dirbase, "_"+inicio+"_"+fin+"_"+nivel);
		red.drawServicio(s,dirbase,"s"+inicio+"_"+fin+"_"+nivel);
		
		return s;
	}
	
	private void crearHTML(Set<Servicio> servicios, String nombreRed){
		File template = new File(dirbase + "\\template.html");
		File index = new File(dirbase + "\\index.html");
		
		try {
			FileWriter fw = new FileWriter(index, false);
			BufferedReader br = new BufferedReader(new FileReader(template));
			
			String linea = br.readLine();
			while (linea != null){
				int i = 0;
				if ( linea.matches(".*COMPLETAR.*") ){
					fw.write("nombreRed = '" + nombreRed + "'\n");
					 
					for(Servicio s: servicios){
						String origen = s.getSolicitud().getOrigen().getLabel();
						String destino = s.getSolicitud().getDestino().getLabel();
						String calidad = s.getSolicitud().getNivel().toString();
						
						fw.write("servicios["+i+"] = {origen: '" + origen +
												  "', destino: '" +destino +
												  "', calidad: '"+ calidad +"'}\n"
						);
						
						i++;
					}
					
				} else {
					fw.write(linea + "\n");
				}
				
				linea = br.readLine();
			}
			
			fw.flush();
			fw.close();
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Test
	public void testRandom() {
		TreeSet<Servicio> servicios = new TreeSet<Servicio>();
		
		servicios.add(crearServicio("12", "46", Nivel.Oro));
		servicios.add(crearServicio("34", "45", Nivel.Plata1));
		servicios.add(crearServicio("47", "18", Nivel.Plata1));
		servicios.add(crearServicio("63", "10", Nivel.Bronce));
		
		crearHTML(servicios, red.getNombre());
	}
}
