package wdm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Salto{
	
	@ManyToOne
	private CanalOptico canal;
	
	private int secuencia;
	
	@Id
	@GeneratedValue
	private long id;
	
	public Salto(){}
	
	public Salto(int secuencia, CanalOptico c){
		this.canal = c;
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

	public CanalOptico getCanal() {
		return canal;
	}

	public void setCanal(CanalOptico c) {
		this.canal = c;
	}
}
