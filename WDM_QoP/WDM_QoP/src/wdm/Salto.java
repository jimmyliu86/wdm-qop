package wdm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Salto{
	
	@ManyToOne
	private Enlace enlace;
	
	private int secuencia;
	
	@Id
	@GeneratedValue
	private long id;
	
	public Salto(){}
	
	public Salto(int secuencia, Enlace e){
		this.enlace = e;
		this.secuencia = secuencia;
	}
	
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	
	@Override
	public int hashCode() {
		return secuencia;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Enlace getEnlace() {
		return enlace;
	}

	public void setEnlace(Enlace enlace) {
		this.enlace = enlace;
	}
}
