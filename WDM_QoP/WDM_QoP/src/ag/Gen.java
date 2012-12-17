package ag;

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
	 * Canal Optico principal que es solución para una Solicitud realizada.
	 */
	private CanalOptico primario;

	/*
	 * Canal Optico de respaldo que es posible solución para una Solicitud
	 * realizada.
	 */
	private CanalOptico secundario;

	/**
	 * @return Canal Optico Primario.
	 */
	public CanalOptico getPrimario() {
		return primario;
	}

	/**
	 * @param primario
	 *            Canal Optico Primario.
	 */
	public void setPrimario(CanalOptico primario) {
		this.primario = primario;
	}

	/**
	 * @return Canal Optico Secundario.
	 */
	public CanalOptico getSecundario() {
		return secundario;
	}

	/**
	 * @param secundario
	 *            Canal Optico Secundario.
	 */
	public void setSecundario(CanalOptico secundario) {
		this.secundario = secundario;
	}

}
