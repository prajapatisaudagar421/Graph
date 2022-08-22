
package com.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	private class Node {
		private String label;

		public Node(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}
	private Map<String, Node> nodes = new HashMap<>();
	private Map <Node ,List<Node>> list = new HashMap<>();
	public void addNode(String label) {
		Node node = new Node(label);
		nodes.putIfAbsent(label, node);
		list.putIfAbsent(node, new ArrayList<>());
	}
	public void addEdges(String from, String to) {
		Node fromNode = nodes.get(from);
		Node toNode = nodes.get(to);
		if(fromNode == null || toNode == null) {
			throw new IllegalArgumentException();
		}
		list.get(fromNode).add(toNode);
	}
	public  void removeNode(String label) {
		Node node = nodes.get(label);
		if(node == null) {
			return;
		}
		for (Node n : list.keySet()) {
			list.get(n).remove(node);
		}
		list.remove(node);
		nodes.remove(node);
		
	}
	public void removeEdge(String from, String to) {
		Node fromNode = nodes.get(from);
		Node toNode = nodes.get(to);
		if(fromNode == null || toNode == null) {
			throw new IllegalArgumentException();
		}
		list.get(fromNode).remove(toNode);
	}
	public void print() {
		for (Node source : list.keySet()) {
			List target = list.get(source);
			if (!target.isEmpty()) {
				System.out.println(source +" is connected to "+target);
			}
		}
	}
	public void depthFirst(String label) {
		Node node = nodes.get(label);
		if(node == null) {
			return;
		}
		HashSet<Node> visited = new HashSet<>();
		Stack<Node> stack = new Stack<>();
		stack.push(node);
		while (!stack.isEmpty()) {
			Node current = stack.pop();
			if(visited.contains(current)) {
				continue;
			}
			System.out.println(current);
			visited.add(current);
			for (Node neigbour : list.get(current)) {
				if(!visited.contains(neigbour)) {
					stack.push(neigbour);
				}
			}
		}
	}
	public void breadthFirst(String label) {
		Node node = nodes.get(label);
		if(node == null) {
			return;
		}
		HashSet<Node> visited = new HashSet<>();
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(node);
		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if(visited.contains(current)) {
				continue;
			} 
			System.out.println(current);
			visited.add(current);
			for (Node neigbour : list.get(current)) {
				if(!visited.contains(neigbour)) {
					queue.add(neigbour);
				}
			}
		}
		
	}
	public List<Node> topologicalSorting(){
		HashSet<Node> visited = new HashSet<>();
		Stack<Node> stack = new Stack<>();
		for (Node node : nodes.values()) {
			topologicalSorting(node,visited,stack);
		}
		ArrayList<Node> sorted = new ArrayList<>();
		while (!stack.isEmpty()) {
			sorted.add(stack.pop());
		}
		return sorted;
	}
	private void topologicalSorting(Node node, HashSet<Node> visited, Stack<Node> stack) {
		if(visited.contains(node)) {
			return;
		}
		visited.add(node);
		for (Node neigbour : list.get(node)) {
			topologicalSorting(neigbour, visited, stack);
		}
		stack.push(node);
	}
	
	public boolean hasCycle() {
		HashSet<Node> all = new HashSet<>();
		all.addAll(nodes.values());
		HashSet<Node> visited = new HashSet<>();
		HashSet<Node> visiting = new HashSet<>();
		while (!all.isEmpty()) {
			Node current = all.iterator().next();
			if(hasCycle(current, all,visiting,visited)) {
				return true;
			}
		}
		return false;
	}
	private boolean hasCycle(Node node, HashSet<Node> all, HashSet<Node> visiting, HashSet<Node> visited) {
		all.remove(node);
		visiting.add(node);
		for (Node neigbour : list.get(node)) {
			if(visited.contains(neigbour)) {
				continue;
			}
			if(visiting.contains(neigbour)) {
				return true;
			}
			if(hasCycle(neigbour, all, visiting, visited)) {
				return true;
			}
		}
		visiting.remove(node);
		visited.add(node);
		return false;
	}

}
