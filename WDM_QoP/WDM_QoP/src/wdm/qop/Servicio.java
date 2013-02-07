package wdm.qop;

import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import wdm.Camino;
import wdm.CanalOptico;
import wdm.Nodo;
import wdm.Salto;

@Entity
public class Servicio implements Comparable<Servicio>{
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private  Solicitud solicitud;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino primario;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino alternativo;
	
	private double pFalla;
	
	private double pRecuperacion;
	
	@Transient
	private boolean disponible = false;
	
	public static String BUFFER_DEBUG = "";
	
	public Servicio(){}
	
	/**
	 * Constructor principal
	 * @param solicitud	Solicitud a la que se desea proveerle un servicio
	 */
	public Servicio(Solicitud solicitud) {
		super();
		this.solicitud = solicitud;
	}

	public Servicio(Camino primario, Camino alternativo, Solicitud solicitud) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		this.solicitud = solicitud;
	}
	
	/**
	 * Constructor usado para crear hijos
	 * 
	 * @param primario
	 * @param alternativo
	 * @param nivel
	 */
	public Servicio(Camino primario, Camino alternativo, Nivel nivel) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		this.solicitud = new Solicitud(primario.getOrigen(),
				primario.getDestino(), nivel);
	}

	/**
	 * Getter de la solicitud
	 * @return	Solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Obtiene la probabilidad de falla del servicio
	 * @return	Probabilidad de Falla
	 */
	public double getpFalla() {
		return ((int)(pFalla*10000.0))/100.0;
	}

	/**
	 * Obtiene la probabilidad de recuperacion del servicio.
	 * @return	Probabilidad de servicio
	 */
	public double getpRecuperacion() {
		return ((int)(pRecuperacion*10000.0))/100.0;
	}


	/**
	 * Funcion de Simulacion, retorna true si el servicio esta disponible
	 * @return	Disponibildad del servicio
	 */
	public boolean estaDisponible() {
		return disponible;
	}

	/**
	 * Setter de la disponibilidad del servicio
	 * @param disponible
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Camino getPrimario() {
		return primario;
	}

	public void setPrimario(Camino primario) {
		this.primario = primario;
	}

	public Camino getAlternativo() {
		return alternativo;
	}
	
	public void setPrimario(){
		if (primario == null) return;
		
		if (solicitud.getNivel() != Nivel.Bronce){
			primario.setEnlaces();
		} else {
			primario.setReservas(this, Exclusividad.SinReservasBronce);
		}
	}
	
	public void setAlternativo(){
		if (alternativo == null) return;
		
		if(solicitud.getNivel() == Nivel.Oro){
			alternativo.setEnlaces();
		} else {
			alternativo.setReservas(this, Exclusividad.NoExclusivo);
		}
	}
	
	public void fijarRecursos(){
		if (primario == null) return;
		
		if (solicitud.getNivel() != Nivel.Bronce){
			primario.fijarEnlaces();
		} else {
			primario.fijarReservas(this);
		}
		
		if (alternativo == null) return;
		
		if(solicitud.getNivel() == Nivel.Oro){
			alternativo.fijarEnlaces();
		} else {
			alternativo.fijarReservas(this);
		}
	}
	
	public void liberarRecursos(){
		if(primario == null) return;
		
		if ( solicitud.getNivel() != Nivel.Bronce ){
			primario.desbloquearEnlaces();
		} else {
			primario.eliminarReservas(this);
		}
		
		if (alternativo == null ) return;
		
		if( solicitud.getNivel() == Nivel.Oro ) {
			alternativo.desbloquearEnlaces();
		} else {
			alternativo.eliminarReservas(this);
		}
	}

	public void setAlternativo(Camino alternativo) {
		this.alternativo = alternativo;
	}
	
	public void buscarAlternativo() {
		if (solicitud.getEsquema() == EsquemaRestauracion.FullPath){
			primario.bloquearNodos();
			primario.getDestino().desbloquear();
			alternativo = primario.getOrigen().dijkstra(primario.getDestino(),solicitud.getExclusividadAlternativo(), true );
			primario.desbloquearNodos();
		} else if(solicitud.getEsquema() == EsquemaRestauracion.Segment){
			primario.bloquearCanales();
			alternativo = primario.getOrigen().dijkstra(primario.getDestino(),solicitud.getExclusividadAlternativo(), true );
			primario.desbloquearCanales();	
		}
		
	}
	
	public void randomizar() {
		liberarRecursos();
		
		Camino original = primario;
				
		/* 
		 * Se busca un subcamino de distancia entre 1 y el 40% de longitud del camino original (4 saltos en promedio).
		 * El subcamino tendra una distancia aleatoria (subcaminoDistancia).
		 * Y el nodo origen tambien sera aleatorio.
		 * 
		 * El camino nuevo tiene tres partes :
		 * Parte A : Origen - Medio1
		 * Parte B : Medio1 - Medio2 (Subcamino nuevo donde no se utilizan los nodos intermedios del camino original) 
		 * Parte C : Medio2 - Fin
		 * 
		 * El nuevo subcamino nuevo se hallar� bloqueando los canales originales del sub camino, y buscando
		 * otro camino optimo. Luego se desbloquearan los canales originales del sub camino.
		 */
		double cantSaltos = original.getSaltos().size();
		int subCaminoDistancia = 1 + (int) (Math.random()*cantSaltos*0.25);
		int nodoIndex = (int) (Math.random()*(cantSaltos-(double)subCaminoDistancia));

		Iterator<Salto> iterSaltos = original.getSaltos().iterator();
		
		/*
		 * Se crea primeramente la parte A del camino nuevo
		 */
		Camino caminoMutante = new Camino(original.getOrigen());
		Nodo actual = original.getOrigen();
		actual.bloquear();
		while(nodoIndex > 0) {
			Salto salto = iterSaltos.next();
			caminoMutante.addSalto(new Salto(salto.getSecuencia(), salto.getCanal()));
			actual = salto.getCanal().getOtroExtremo(actual);
			actual.bloquear();
			nodoIndex--;
		}
		Nodo medio1 = actual;
		Camino subCaminoViejo = new Camino(medio1);


		/*
		 * Se bloquean los canales intermedios entre Medio1 y Medio2
		 */
		int secuencia = 1;
		while(subCaminoDistancia > 0){
			CanalOptico canal = iterSaltos.next().getCanal();
			actual = canal.getOtroExtremo(actual);
			
			subCaminoDistancia--;
			
			canal.bloquear();			
			subCaminoViejo.addSalto(new Salto(secuencia++, canal));
		}
		Nodo medio2 = actual;
		medio2.desbloquear();
				
		Camino parteC = new Camino(actual);
		secuencia = 1;
		while(iterSaltos.hasNext()){
			CanalOptico canal = iterSaltos.next().getCanal();
			actual = canal.getOtroExtremo(actual);
			actual.bloquear();
			parteC.addSalto(new Salto(secuencia++, canal));
		}
		parteC.getOrigen().desbloquear();
		
		/*Se calcula la parte B del camino nuevo*/
		Camino subCaminoNuevo = medio1.dijkstra(medio2, solicitud.getExclusividadPrimario());
		subCaminoViejo.desbloquearCanales();
		primario.desbloquearNodos();
		/*
		 * Si no se puede encontrar un camino alternativo sin utilizar
		 * los canales originales, se ignora la mutacion.
		 */
		if(subCaminoNuevo == null) {
			/*
			 * Se vuelve a fijar los recursos de los
			 * caminos primario y alternativo originales.
			 */
			
			fijarRecursos();
			
			return;
		}
						
		caminoMutante.anexar(subCaminoNuevo);
		caminoMutante.anexar(parteC);
		
		primario = caminoMutante;	
		setPrimario();
		
		if(solicitud.getNivel() != Nivel.Bronce){
			buscarAlternativo();
			setAlternativo();
		} else {
			alternativo = null;
		}
	}
	
	public void random() {
		Nodo origen = solicitud.getOrigen();
		Nodo destino = solicitud.getDestino();
		
		primario = origen.dijkstra(destino, solicitud.getExclusividadPrimario());
		
		setPrimario();
		
		if(solicitud.getNivel() != Nivel.Bronce){
			buscarAlternativo();
			setAlternativo();
		}
		
		this.randomizar();
	}
	
	@Override
	public String toString() {
		return "s"+solicitud.getOrigen()+"_"+solicitud.getDestino();
	}

	@Override
	public int compareTo(Servicio arg0) {
		return solicitud.compareTo(arg0.solicitud);
	}
}
