package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

class Graph {
	HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
	HashMap<Integer, LinkedList<Integer>> degreeDist = new HashMap<>();
	HashMap<Integer, Integer> cComponent = new HashMap<>();
	
	public void addEdge(int u, int v) {
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
	
	void displayGraph() {
		for (Map.Entry m: map.entrySet()) {
			System.out.println(m.getKey() + "-->" + m.getValue());
		}
	}
	
	private int nodeDegree(int u) {
		if (map.containsKey(u)) {
			int degree = map.get(u).size();
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
	
	public int size() {
		return map.size();
	}
	
	public LinkedList<Integer> DFS(int v, boolean[] visited){
		LinkedList<Integer> component = new LinkedList<Integer>(); 
		Stack<Integer> stack = new Stack<>();
		
		stack.push(v);
		
		while(!stack.empty()) {
			
			v = stack.peek();
			stack.pop();
			
			if(!visited[v]) {
				component.add(v);
				visited[v] = true;
			}
			for (int x : map.get(v)) {
				if(!visited[x]) {
					stack.push(x);
				}
			}
		}
		return component;
	}
	
	void connectedComponents() {
		boolean[] visited = new boolean[map.size()];
		
		for ( int v = 0; v < map.size(); v++) {
			if(!visited[v]) {
				LinkedList<Integer> component = DFS(v, visited);
				int cSize = component.size();
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
	void display_components() {
		System.out.println(cComponent);
	}
		
	
	public int ccNumber() {
		int tot = 0;
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
	
	public static void main(String[] args) throws NoSuchElementException {
		long startTime = System.currentTimeMillis();
		Graph g = new Graph();
		
		Scanner sc = new Scanner(System.in);
		int i = 0;
		HashMap<String, Integer> vertex = new HashMap<>();
		//ArrayList<String> vertex = new ArrayList<String>();
		
		try {			// build the graph bases on scanned entries from System.in
			while (sc.hasNextLine()) {
				String u = sc.next();
				String v = sc.next();
			
				if (!vertex.containsKey(u)) {
					vertex.put(u, i);
					i++;
				}
				if (!vertex.containsKey(v)) {
					vertex.put(v, i);
					i++;
				}
				
				int uNum = vertex.get(u);
				int vNum = vertex.get(v);
			
				g.addEdge(uNum,  vNum);
			
			}
		}
		catch(Exception e) {}
		g.connectedComponents();
		//g.displayGraph();
		
		System.out.println("------------------------------------");
		System.out.println(g.size());
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
