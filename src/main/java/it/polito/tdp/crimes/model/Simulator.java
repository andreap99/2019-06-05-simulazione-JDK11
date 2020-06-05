package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {
	
	//PARAMETRI SIMULAZIONE
	private Graph<Vertice, DefaultWeightedEdge> grafo;
	private int N;
	private LocalDateTime inizio;
	
	
	//STATO DEL SISTEMA
	private List<Agente> agenti = new ArrayList<>();
	private Map<Integer, Vertice> vertici = new HashMap<>();
	
	//OUTPUT DA CALCOLARE
	private int malGestiti;
	private int nonAssegnati;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> queue;
	
	//INIZIALIZZAZIONE
	public void init(Graph<Vertice, DefaultWeightedEdge> grafo2, List<Event> eventiSimulazione, int N, Vertice v, Set<Vertice> set) {
		this.N = N;
		this.grafo = grafo2;
		this.queue = new PriorityQueue<>();
		this.malGestiti = 0;
		this.nonAssegnati = 0;
		Collections.sort(eventiSimulazione);
		for(Vertice z : set)
			this.vertici.put(z.getId(), z);
		for(int i=0; i<N; i++) {
			agenti.add(new Agente(true, v));
		}
		for(Event e : eventiSimulazione) {
			this.queue.add(new Evento(e, null, EventType.INPUT, e.getReported_date()));
		}
		
	}
	
	public String run() {
		
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
		
		return String.format("Numero di eventi mal gestiti = %d\nNumero di eventi non assegnati = %d",
				this.malGestiti, this.nonAssegnati);
		
	}
	
	public void processEvent(Evento e) {
		
		Event reato = e.getEvento();
		
		switch(e.getTipo()) {
		
		case INPUT:
			
			Agente selezionato = null;
			
			for(Agente a : agenti) {
				if(a.getDistretto().getId()==reato.getDistrict_id() && a.isLibero()){
					selezionato = a;
				}
			}
			for(Vicino n : vertici.get(reato.getDistrict_id()).getVicini()) {
				for(Agente a : agenti) {
					if(a.getDistretto().getId()==n.getV2().getId() && a.isLibero()){
						selezionato = a;
					}
				}
			}
			for(Agente a : agenti) {
				if(a.isLibero())
					selezionato = a;
			}
			
			if(selezionato == null) {
				System.err.println("No agenti disponibili");
				this.nonAssegnati++;
				return;
			}
			//errore qui
			Vertice v1 = vertici.get(reato.getDistrict_id());
			Vertice v2 = selezionato.getDistretto();
			
			if(v1.getId()==v2.getId()) {//mi trovo gia sul posto
				this.queue.add(new Evento(reato, selezionato, EventType.ARRIVO, e.getTime()));
			}
			else {
				// ci si muove ad una velocità di 60km/h cioè 1km/min, quindi la distanza è pari al tempo impiegato
				Double distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(v1,v2));
				LocalDateTime t = e.getTime().plus(distanza.longValue(), ChronoUnit.MINUTES);
				this.queue.add(new Evento(reato, selezionato, EventType.ARRIVO, t));
				selezionato.setLibero(false);
			}
			break;
		
			
		case ARRIVO:
			Duration d = Duration.between(reato.getReported_date(), e.getTime());
			if(!d.minusMinutes(15).isNegative())
				this.malGestiti++;
			LocalDateTime p = e.getTime().plus(this.getDurata(reato), ChronoUnit.HOURS);
			this.queue.add(new Evento(reato, e.getAgente(), EventType.FINE, p));
			break;
			
		case FINE:
			e.getAgente().setLibero(true);
			e.getAgente().setDistretto(vertici.get(reato.getDistrict_id()));
			break;
		
		}
		
	}

	private int getDurata(Event reato) {
		
		if(reato.getOffense_category_id().compareToIgnoreCase("all_other_crimes")==0) {
			double perc = Math.random()*100;
			if(perc<50)
				return 1;
			else
				return 2;
		}else
			return 2;
		}

}
