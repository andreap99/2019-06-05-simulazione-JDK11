package it.polito.tdp.crimes.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafo(2017);
		System.out.println(model.getVertexNumber()+"    "+model.getEdgesNumber());
		//System.out.println(model.output());
		model.getSimulazione(1, 1, 2017, 15);
		
		//System.out.println(model.getEdges());

	}
}
