package wdm;

import java.util.HashSet;
import wdm.qop.Servicio;

public class Enlace {
	private final int ldo;
	private final int fibra;
	private final CanalOptico canal;
	
	private Servicio servicio;
	private final HashSet<Servicio> reservas = new HashSet<Servicio>();
	
	private boolean bloqueado = false;
	
	public Enlace(int ldo, int fibra, CanalOptico canal){
		this.ldo = ldo;
		this.fibra = fibra;
		this.canal = canal;
	}
	
	public int getLongitudDeOnda(){
		return this.ldo;
	}
	
	public int getFibra(){
		return fibra;
	}
	
	public Nodo getOrigen(){
		return canal.getOrigen();
	}
	
	public Nodo getDestino(){
		return canal.getDestino();
	}

	public void reservar( Servicio servicio ){
		if ( ! reservas.contains(servicio) ){
			reservas.add(servicio);
		}
	}
	
	public boolean estaReservado(){
		return (reservas.size() > 0);
	}

	public void eliminarReserva(Servicio servicio){
		reservas.remove(servicio);
	}
	
	public void eliminarReservas(){
		reservas.clear();
	}
	
	public void bloquear(){
		this.bloqueado = true;
	}
	
	public void desbloquear(){
		this.bloqueado = false;
	}
	
	public boolean estaBloqueado(){
		return this.bloqueado;
	}
	
	public void utilizar(Servicio servicio){
		this.servicio = servicio;
	}
	
	public void liberar(){
		this.servicio = null;
	}
	
	public boolean estaLibre(){
		return this.servicio != null;
	}
	
	public void inicializar(){
		this.bloqueado = false;
		this.reservas.clear();
		this.servicio = null;
	}
}
