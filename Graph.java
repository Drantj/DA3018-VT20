package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Graph {
	HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
	
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
	
	public static void main(String[] args) throws NoSuchElementException {
		Graph g = new Graph();
		
		Scanner sc = new Scanner(System.in);
		int i = 0;
		ArrayList<String> vertex = new ArrayList<String>();
		
		try {
			while (sc.hasNextLine()) {
				String u = sc.next();
				String v = sc.next();
			
				if (!vertex.contains(u)) {
					vertex.add(u);
				}
				else {
					i++;
				}
				if (!vertex.contains(v)) {
					vertex.add(v);
				}
				else {
					i++;
				}
			
				int uNum = vertex.indexOf(u);
				int vNum = vertex.indexOf(v);
			
				g.addEdge(uNum,  vNum);
			
			}
		}
		catch(Exception e) {}
		
		System.out.println(i);
		g.displayGraph();
		
	}
	
}
