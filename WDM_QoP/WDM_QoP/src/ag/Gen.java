package ag;

import java.util.ArrayList;

import wdm.CanalOptico;

/**
 * Clase Gen que representa la solución particular para una Solicitud realizada.
 * <p>
 * Consiste en un canal primario + un canal secundario.
 * </p>
 * 
 * @author mrodas
 * 
 */
public class Gen {

	/*
	 * Conjunto de Canales Opticos primarios que representan la solución para
	 * una Solicitud realizada.
	 */
	private ArrayList<CanalOptico> primario;

	/*
	 * Canal Optico de respaldo que es posible solución para una Solicitud
	 * realizada.
	 */
	private ArrayList<CanalOptico> secundario;

	public Gen() {
		this.primario = null;
		this.secundario = null;
	}

	public Gen(ArrayList<CanalOptico> primario,
			ArrayList<CanalOptico> secundario) {
		super();
		this.primario = primario;
		this.secundario = secundario;
	}

	/**
	 * @return Canal Optico Primario.
	 */
	public ArrayList<CanalOptico> getPrimario() {
		return primario;
	}

	/**
	 * @param primario
	 *            Canal Optico Primario.
	 */
	public void setPrimario(ArrayList<CanalOptico> primario) {
		this.primario = primario;
	}

	/**
	 * @return Canal Optico Secundario.
	 */
	public ArrayList<CanalOptico> getSecundario() {
		return secundario;
	}

	/**
	 * @param secundario
	 *            Canal Optico Secundario.
	 */
	public void setSecundario(ArrayList<CanalOptico> secundario) {
		this.secundario = secundario;
	}

	public double getCosto() {
		double total = 0.0;
		for (CanalOptico co : this.primario) {
			total += co.getCosto();
		}
		return total;
	}

}
