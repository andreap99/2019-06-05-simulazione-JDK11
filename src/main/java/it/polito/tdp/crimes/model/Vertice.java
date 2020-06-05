package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class Vertice {
	
	private int id;
	private LatLng posizioneCentro;
	private List<Event> eventi;
	private List<Vicino> vicini;
	
	public Vertice(int id) {
		this.id = id;
		this.eventi = new ArrayList<>();
		this.vicini = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public LatLng getPosizioneCentro() {
		return posizioneCentro;
	}
	
	public void setPosizione(LatLng pos) {
		this.posizioneCentro = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Vertice other = (Vertice) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Distretto "+id;
	}

	public void setEvents(List<Event> events) {
		this.eventi = events;
	}

	public int getEventsSize() {
		
		return eventi.size();
	}

	public List<Vicino> getVicini() {
		return vicini;
	}

	public void setVicini(List<Vicino> vicini) {
		this.vicini = vicini;
	}


	
	

}
