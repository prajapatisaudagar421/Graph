package com.graph;

public class Main {
public static void main(String[] args) {
	Graph graph = new Graph();
	graph.addNode("A");
	graph.addNode("B");
	graph.addNode("C");
	graph.addNode("D");
	graph.addEdges("A", "B");
	graph.addEdges("B", "C");
	graph.addEdges("C", "D");
	//graph.addEdges("D", "A");
	graph.print();
//	graph.depthFirst("A");
//	graph.breadthFirst("A");
	System.out.println(graph.topologicalSorting());
	System.out.println(graph.hasCycle());
}
}
