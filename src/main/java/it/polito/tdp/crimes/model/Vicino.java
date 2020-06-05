package it.polito.tdp.crimes.model;

public class Vicino implements Comparable<Vicino>{
	
	private Vertice v1;
	private Vertice v2; 
	private Double distanza;
	
	public Vicino(Vertice v1, Vertice v2, Double distanza) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.distanza = distanza;
	}
	
	

	public Double getDistanza() {
		return distanza;
	}



	public Vertice getV2() {
		return v2;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vicino other = (Vicino) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}



	@Override
	public int compareTo(Vicino other) {
		
		return this.distanza.compareTo(other.distanza);
	}
	
	
	

}
