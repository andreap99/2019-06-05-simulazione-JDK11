package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<Vertice, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.dao.getAllDistricts());
		this.assegnaPosizioni(anno);
		for(Vertice v1 : this.grafo.vertexSet()) {
			for(Vertice v2 : this.grafo.vertexSet()) {
				if(v1.getId()<v2.getId()) {
					Graphs.addEdgeWithVertices(grafo, v1, v2,
							LatLngTool.distance(v1.getPosizioneCentro(),v2.getPosizioneCentro(), LengthUnit.KILOMETER));
				}
			}
		}
	}

	private void assegnaPosizioni(int anno) {
		for(Vertice v : this.grafo.vertexSet()) {
			LatLng pos = this.calcolaPosizioneCentro(this.dao.getEvents(v, anno));
			v.setPosizione(pos);
			v.setEvents(this.dao.getEvents(v, anno));
		}
	}
	
	public LatLng calcolaPosizioneCentro(List<Event> events) {
		double lat = 0.0;
		double lng = 0.0;
		for(Event e : events) {
			lat += e.getGeo_lat();
			lng += e.getGeo_lon();
		}
		LatLng pos = new LatLng(lat/events.size(), lng/events.size());
		return pos;
	}
	
	public int getVertexNumber() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdgesNumber() {
		return this.grafo.edgeSet().size();
	}

	public List<Integer> getAnni() {
		
		return this.dao.getYears();
	}

	public String output() {
		
		String s = "";
		
		for(Vertice v : this.grafo.vertexSet()) {
			List<Vicino> near = new ArrayList<>();
			s += "Elenco vicini distretto "+v.getId()+":\n";
			for(Vertice v2 : this.grafo.vertexSet()) {
				if(v2.getId() != v.getId()) {
					Vicino n = new Vicino(v, v2, this.grafo.getEdgeWeight(this.grafo.getEdge(v, v2)));
					near.add(n);
				}
			}
			Collections.sort(near);
			v.setVicini(near);
			for(Vicino x : near) {
				s += x.getV2().toString() + " distanza: "+ x.getDistanza()+" Km\n";
			}
		}
		
		return s;
	}
	
	public String getSimulazione(int giorno, int mese, int anno, int N) {
		Vertice v = this.getMinimaCriminalita();
		if(v==null) {
			System.out.println("ERRORE");
		}
		List<Event> eventiSimulazione = this.dao.getEventsSimulator(giorno, mese, anno);
		Simulator sim = new Simulator();
		sim.init(this.grafo, eventiSimulazione, N, v, this.grafo.vertexSet());
		String s = sim.run();
		return s;
	}

	private Vertice getMinimaCriminalita() {
		int minimo = 1000000;
		Vertice migliore = null;
		for(Vertice v : this.grafo.vertexSet()) {
			if(v.getEventsSize()<minimo) {
				minimo = v.getEventsSize();
				migliore = v;
			}
		}
		return migliore;
	}
	
	public Set<Vertice> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public String getEdges(){
		String s = "";
		for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
			s += d + " " + this.grafo.getEdgeWeight(d) + "\n";
		}
		return s;
	}
	
}
