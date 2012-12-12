package wdm.qop;

import wdm.Camino;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class SegmentoProtegido {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino primario;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Camino alternativo;
	
	@Transient
	private double pFalla;
	
	@Transient
	private double pRecuperacion;
	
	/**
	 * Constructor Principal
	 * @param primario
	 * @param alternativo
	 */
	public SegmentoProtegido(Camino primario, Camino alternativo) {
		super();
		this.primario = primario;
		this.alternativo = alternativo;
		
		this.pFalla = probabilidadFalla();
		this.pRecuperacion = probabilidadRecuperacion();
	}
	
	/**
	 * Retorna la probabilidad de falla del segmento
	 * @return
	 */
	private double probabilidadFalla(){
		return 0.0;
	}
	
	/**
	 * Retorna la probabilidad de recuperacion del segmento
	 * @return
	 */
	private double probabilidadRecuperacion(){
		return 0.0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setAlternativo(Camino alternativo) {
		this.alternativo = alternativo;
	}

	public double getpFalla() {
		return pFalla;
	}

	public double getpRecuperacion() {
		return pRecuperacion;
	}
}
