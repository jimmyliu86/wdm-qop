package wdm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import wdm.qop.Exclusividad;
import wdm.qop.Servicio;

/**
 * Clase Camino, representa un camino por su nodo origen, y una lista de
 * enlaces que debe seguir
 * @author albert
 *
 */
@Entity
public class Camino {
	
	public static String BUFFER_DEBUG = "";
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo origen;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Nodo destino;
	
	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("secuencia ASC")
	private Set<Salto> saltos;
	
	private int distancia = 0;
	
	public Camino(){}
	
	/**
	 * Constructor principal
	 * @param origen
	 * @param destino
	 */
	public Camino(Nodo origen){
		this.origen = origen;
		this.destino = origen;
		this.saltos = new TreeSet<Salto>();
		this.saltos.clear();
		this.distancia = 0;
	}
	
	/**
	 * Constructor apartir de un camin existente
	 * @param c	Camino existente
	 */
	public Camino(Camino c){
		this.origen = c.origen;
		
		this.saltos = new TreeSet<Salto>();
		this.saltos.addAll(c.saltos);
		this.destino = c.destino;
		this.distancia = c.distancia;
	}
	
	/**
	 * Metodo para agregar un salto al camino
	 * @param salto
	 */
	public void addSalto(Salto salto){
		saltos.add(salto);
		
		if (destino == null) destino = origen;
		
		if (salto != null) {
			destino = salto.getCanal().getOtroExtremo(destino);
			distancia += salto.getCanal().getCosto();
		}
	}
	
	/**
	 * Utilizado para obtener el destino del camino
	 * @return El ultimo nodo visitado en el camino
	 */
	public Nodo getDestino(){
		return this.destino;
	}
	
	/**
	 * Utilizado para obtener la longitud del camino en saltos
	 * @return La cantidad de saltos, es decir el tama�o de la lista saltos
	 */
	public int getDistancia(){
		return this.distancia;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Set<Salto> getSaltos(){
		return saltos;
	}
	
	public void setSaltos(Set<Salto> saltos){
		this.saltos = saltos;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}
	
	public void bloquearCanales(){
		for(Salto salto : saltos){
			salto.getCanal().bloquear();
		}
	}
	
	public void desbloquearCanales(){
		for(Salto salto : saltos){
			salto.getCanal().desbloquear();
		}
	}
	
	public void bloquearNodos(){
		Nodo actual = this.origen;
		actual.bloquear();
		
		for(Salto salto : saltos){
			CanalOptico canal = salto.getCanal();
			Nodo anterior = actual;
			actual = canal.getOtroExtremo(actual);
			
			if(actual == null) System.out.println("c"+canal.getId()+"-"+anterior);
			
			actual.bloquear();
		}
	}
	
	public void desbloquearNodos(){
		Nodo actual = this.origen;
		
		actual.desbloquear();
		for(Salto salto : saltos){
			actual = salto.getCanal().getOtroExtremo(actual);
			
			actual.desbloquear();
		}
	}
	
	public void desbloquearEnlaces(){
		for(Salto salto : saltos){
			Enlace e = salto.getEnlace();
			if ( e != null ) e.desbloquear();
		}
	}

	public Nodo getOrigen() {
		return origen;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	
	public void anexar(Camino c){		
		if ( ! c.origen.equals(this.destino) ) return;
		
		int secuencia = this.saltos.size()+1;
		Nodo actual = this.destino;
		
		for(Salto s: c.saltos){
			actual = s.getCanal().getOtroExtremo(actual);
			Salto newSalto = new Salto(secuencia++,s.getCanal());
			this.saltos.add(newSalto);
		}
		
		this.distancia = distancia + c.distancia;
		this.destino = c.destino;
	}
	
	public void setEnlaces(){
		int ldo = -1;
		for(Salto salto: saltos){
			ldo = salto.setEnlace(ldo);
		}
	}
	
	public void fijarEnlaces(){
		for(Salto salto: saltos){
			Enlace e = salto.getEnlace();
			
			/*
			 * Si el servicio utiliza una fibra extra
			 */
			if(e.getFibra() >= e.getCanal().getFibras()){
				salto.getCanal().agregarFibraExtra();
				
				/*
				 * Se actualiza la referencia al enlace extra.
				 */
				e = e.getCanal().getEnlace(e.getFibra(), e.getLongitudDeOnda());
				salto.setEnlace(e);
			}
			
			e.bloquear();
		}		
	}
	
	public void fijarReservas(Servicio s){
		for(Salto salto: saltos){
			Enlace e = salto.getEnlace();
			
			/*
			 * Si el servicio utiliza una fibra extra
			 */
			if(e.getFibra() >= e.getCanal().getFibras()){
				salto.getCanal().agregarFibraExtra();
				
				/*
				 * Se actualiza la referencia al enlace extra.
				 */
				e = e.getCanal().getEnlace(e.getFibra(), e.getLongitudDeOnda());
				salto.setEnlace(e);
			}
			
			e.reservar(s);
		}
	}
	
	@Override
	public String toString(){
		String camino = origen.toString();
		Nodo actual = origen;
		
		for(Salto s: saltos){
			actual = s.getCanal().getOtroExtremo(actual);
			camino = camino + "-" + actual;
		}
		
		return camino;
	}

	public void setReservas(Servicio servicio, Exclusividad exclusividad) {
		int ldo = -1;
		for(Salto salto : saltos){
			ldo = salto.setReserva(ldo,servicio,exclusividad);
		}
	}

	public void eliminarReservas(Servicio s) {
		for(Salto salto: saltos){
			salto.getEnlace().eliminarReserva(s);
		}
	}
		
	public Set<Enlace> getEnlaces(){
		HashSet<Enlace> enlaces = new HashSet<Enlace>();
		
		for(Salto s: saltos){
			enlaces.add(s.getEnlace());
		}
		
		return enlaces;
	}
	
	public boolean usaCanal(CanalOptico c){
		for(Salto salto : saltos){
			if(salto.getEnlace().getCanal().equals(c)) return true;
		}
		
		return false;
	}
	
    public int getCambiosLDO() {
        int retorno = 0;
        int ldo1 = 0;
        int ldo2 = 0;
        Iterator<Salto> isaltos = this.saltos.iterator();
        // al menos un enlace Salto debe existir.
        if (isaltos.hasNext()) {

                Salto salto = isaltos.next();
                if (salto.getEnlace() == null)
                        throw new Error("GetCambiosLDO: Enlace Nulo. ID:"+salto.getId());
                ldo1 = salto.getEnlace().getLongitudDeOnda();
                while (isaltos.hasNext()) {

                        ldo2 = isaltos.next().getEnlace().getLongitudDeOnda();
                        if (ldo1 != ldo2)
                                retorno++;

                        ldo1 = ldo2;
                }
        }

        return retorno;
    }
    
    public void agregarFibras(Exclusividad exclusividad){
    	for(Salto s: saltos){
    		CanalOptico c = s.getCanal();
    		
    		if ( ! c.libreSegunExclusividad(exclusividad) ){
    			c.agregarFibraExtra();
    		}
    	}
    }
}
