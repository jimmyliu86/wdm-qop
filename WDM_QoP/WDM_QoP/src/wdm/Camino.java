package wdm;

import java.util.LinkedList;

public class Camino {
	private final Nodo origen;
	private final LinkedList<Enlace> saltos = new LinkedList<Enlace>();
	
	public Camino(Nodo origen){
		this.origen = origen;
		this.saltos.clear();
	}
	
	public void addSalto(Enlace salto){
		saltos.add(salto);
	}
	
	public Nodo getDestino(){
		return saltos.getLast().getDestino();
	}
}
