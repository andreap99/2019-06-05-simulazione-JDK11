package it.polito.tdp.crimes.model;

public class Agente {
	
	public boolean libero;
	public Vertice distretto;
	
	public Agente(boolean libero, Vertice distretto) {
		this.libero = libero;
		this.distretto = distretto;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}

	public Vertice getDistretto() {
		return distretto;
	}

	public void setDistretto(Vertice distretto) {
		this.distretto = distretto;
	}
	
	

}
