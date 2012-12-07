package wdm;

import java.util.HashSet;

public class Nodo {
	private String label = "";
	private boolean usaConversor = false;
	private final HashSet<CanalOptico> canales = new HashSet<CanalOptico>();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean usaConversor() {
		return usaConversor;
	}

	public void utilizarConversor() {
		this.usaConversor = true;
	}
	
	public void inicializar(){
		this.usaConversor = false;
	}
	
	public HashSet<Nodo> getVecinos(){
		HashSet<Nodo> vecinos = new HashSet<Nodo>();
		vecinos.clear();
		
		for(CanalOptico canal : canales){
			Nodo vecino = canal.getDestino();
			
			if ( ! vecinos.contains(vecino) ){
				vecinos.add(vecino);
			}
		}
		
		return vecinos;
	}
}
