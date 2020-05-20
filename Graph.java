package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

class Graph {
	
	// initiate hashmaps for storage
	HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
	HashMap<Integer, LinkedList<Integer>> degreeDist = new HashMap<>();	
	HashMap<Integer, Integer> cComponent = new HashMap<>();
	
	
	/*
	 * method for adding an edge from two vertices.
	 * Takes two integers representing the vertex location in the map.
	 */
	public void addEdge(int u, int v) {
		// check if u is already in the map, if so add the v vertex to the corresponding LL.
		if (!map.containsKey(u)) {				
			LinkedList<Integer> l = new LinkedList<>();
			l.add(v);
			map.put(u, l);
		}
		else {
			LinkedList<Integer> l = map.get(u);
			if (!l.contains(v)) {
				l.add(v);
				map.put(u, l);
			}
		}
		// same procedure as with u, since I am assuming we are creating undirected graphs.
		if (!map.containsKey(v)) {
			LinkedList<Integer> k = new LinkedList<>();
			k.add(u);
			map.put(v, k);
		}
		else {
			LinkedList<Integer> k = map.get(v);
			if (!k.contains(u)) {
				k.add(u);
				map.put(v, k);
			}
		}
	}
	
	/*
	 * method for testing how accurate the adjacencylist map is. Clearly meant for testing on
	 * smaller graphs.
	 */
	void displayGraph() {
		for (Map.Entry m: map.entrySet()) {
			System.out.println(m.getKey() + "-->" + m.getValue());
		}
	}
	
	/*
	 * method to get the node degree of the given vertex.
	 * returns the size of the respective LL.
	 */
	private int nodeDegree(int u) {
		if (map.containsKey(u)) {
			int degree = map.get(u).size();		// finds the size of the LL at the map location u.
			return degree;
		}
		else {
			return -1;
		}
	}
	
	
	void degreeDistribution() {
		for (int n = 0; n < map.size(); n++) {
			int degree = nodeDegree(n);
			if(!degreeDist.containsKey(degree)) {
				LinkedList<Integer> l = new LinkedList<>();
				l.add(n);
				degreeDist.put(degree, l);
			}
			else {
				LinkedList<Integer> l = degreeDist.get(degree);
				l.add(n);
				degreeDist.put(degree, l);
			}
		}
		System.out.format("%s%15s", "Degree", "Distribution");
		for (Map.Entry<Integer, LinkedList<Integer>> iterate : degreeDist.entrySet()) {
			int key = iterate.getKey();
			LinkedList<Integer> value = iterate.getValue();
			double dd = (double)value.size() / map.size();		// cast into a double
			System.out.format("\n%d%16f", key, dd);
		}
		System.out.println();
		
	}
	
	/*
	 * method takes an integer and a boolean list and finds all elements that are connected to it
	 * using the DFS algorithm. Returns a LL consisting of the components.
	 */
	public LinkedList<Integer> DFS(int v, boolean[] visited){
		LinkedList<Integer> component = new LinkedList<Integer>(); 
		// create a stack for DFS
		Stack<Integer> stack = new Stack<>();
		// push the source vertex into the stack
		stack.push(v);
		
		while(!stack.empty()) {
			
			// pop a vertex and print it
			v = stack.peek();
			stack.pop();
			
			// if given vertex has NOT been visited before, add component and mark it as visited.
			if(!visited[v]) {
				component.add(v);
				visited[v] = true;
			}
			// get all adjacent vertices of the popped vertex, if NOT visited, push to stack.
			for (int x : map.get(v)) {
				if(!visited[x]) {
					stack.push(x);
				}
			}
		}
		return component;
	}

	/*
	 * method for finding the number of connected components distribution in the graph.
	 * Uses the DFS method to update the cComponent map with the number of connected
	 * components of that particular size. Returns void.
	 */
	void connectedComponents() {
		// Use the same boolean for each time we use DFS to know where we have been
		boolean[] visited = new boolean[map.size()];
		
		// iterate over all vertices to fill the visited array.
		for ( int v = 0; v < map.size(); v++) {
			if(!visited[v]) {
				LinkedList<Integer> component = DFS(v, visited);
				int cSize = component.size();
				// get size and add to relevant map location.
				if (!cComponent.containsKey(cSize)) {
					cComponent.put(cSize, 1);
				}
				else {
					int old = cComponent.get(cSize);
					cComponent.replace(cSize,  old + 1);
				}
			}
		}
	}
	
		
	/*
	 * method to find the number of connected components in graph G.
	 */
	public int ccNumber() {
		int tot = 0;
		// iterate cComponents and add up all values in the current position. 
		for (int value : cComponent.values()) {
			tot = tot + value;
		}
		return tot;
	}
	
	void CompDist() {
		int size = ccNumber();
		System.out.format("%s%17s", "Size", "Distribution");
		for (Map.Entry<Integer, Integer> iterate : cComponent.entrySet()) {
			int key = iterate.getKey();
			int value = iterate.getValue();
			double dd = (double)value / size;
			System.out.format("\n%d%16f", key, dd);
		}
		System.out.println();
	}
	
	/*
	 * main program reads from System.in to produce a graph and calculate various 
	 * properties of the graph. Meant to be run in an unix tool.
	 */
	public static void main(String[] args) throws NoSuchElementException {
		
		long startTime = System.currentTimeMillis();
		Graph g = new Graph();
		
		Scanner sc = new Scanner(System.in);
		int i = 0;
		HashMap<String, Integer> vertex = new HashMap<>();
		
		try {			// build the graph based on scanned entries from System.in
			while (sc.hasNextLine()) {
				// first two values are vertices in the graph
				String u = sc.next();
				String v = sc.next();
				
				// if not found in the vertex map, add it put the value as i
				if (!vertex.containsKey(u)) {
					vertex.put(u, i);
					i++;	// update i
				}
				if (!vertex.containsKey(v)) {
					vertex.put(v, i);
					i++;
				}
				
				int uNum = vertex.get(u);
				int vNum = vertex.get(v);
				
				// add the edge using the integer value of i at the location of the string
				g.addEdge(uNum,  vNum);
			
			}
		}
		catch(Exception e) {}
		g.connectedComponents();
		
		System.out.println("------------------------------------");
		System.out.println("The node degree distribution of G is represented by:");
		
		g.degreeDistribution();
		
		System.out.println("------------------------------------");
		System.out.println("The component size distribution of G is represented by:");
		g.CompDist();
		
		System.out.println("------------------------------------");
		System.out.println("The total number of components in G is " + g.ccNumber());
		
		long stopTime = System.currentTimeMillis();
		System.out.println(stopTime - startTime);
	}
	
}
