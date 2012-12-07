package wdm;

public class Nodo {
	private String label = "";
	private boolean usaConversor = false;

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
}
