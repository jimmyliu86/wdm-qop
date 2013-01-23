package wdm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Salto implements Comparable<Salto>{
	
	@Transient
	private CanalOptico canal;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Enlace enlace;
	
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
		this.enlace = null;
	}

	public Enlace getEnlace() {
		return enlace;
	}

	public void setEnlace(Enlace enlace) {
		this.enlace = enlace;
	}
	
	public int setEnlace(int ldO){
		if(ldO < 0 ) this.enlace = canal.getEnlaceLibre(true);
		else 		 this.enlace = canal.getEnlaceLibre(true,ldO);
		
		enlace.bloquear();
		
		return enlace.getLongitudDeOnda();
	}

	@Override
	public int compareTo(Salto b) {
		
		return this.secuencia - b.secuencia;
	}
	
	
}
