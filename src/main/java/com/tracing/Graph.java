package com.tracing;

import com.tracing.model.graph.Edge;
import com.tracing.model.input.GraphInput;
import lombok.NonNull;

import java.util.*;

/**
 * The {@code Graph} class represents an edge-weighted graph with {@code V}
 * vertices. It supports both adjacency list and adjacency matrix
 * representation.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class Graph {
	private final static int MAX_ALPHA = 26;
	private final List<List<Edge>> adj = new ArrayList<>(MAX_ALPHA);
	private final List<List<Edge>> adjR = new ArrayList<>(MAX_ALPHA);
	private final int[][] adjM = new int[MAX_ALPHA][MAX_ALPHA];
	private final Map<Integer, Boolean[]> map = new HashMap<>();
	private final int numOfVertices;

	/**
	 * Initializes an edge-weighted graph with given edges.
	 *
	 * @param graphInput graph with edges input
	 */
	public Graph(@NonNull GraphInput graphInput) {
		for (int v = 0; v < MAX_ALPHA; v++) {
			adj.add(v, new ArrayList<>());
			adjR.add(v, new ArrayList<>());
		}
		Set<Integer> vertices = new HashSet<>();
		for (Edge e : graphInput.getEdges()) {
			vertices.add(e.from());
			vertices.add(e.to());
			adj.get(e.from()).add(e);
			adjM[e.from()][e.to()] = e.weight();
			adjR.get(e.to()).add(new Edge(e.to(), e.from(), e.weight()));
		}
		numOfVertices = vertices.size();
	}

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return number of vertices in the graph
	 */
	public int getNumOfVertices() {
		return numOfVertices;
	}

	/**
	 * Returns the edges from vertex {@code v}.
	 *
	 * @param v tail vertex
	 * @return edges from vertex {@code v} as an Iterable
	 */
	public Iterable<Edge> adj(int v) {
		return adj.get(v);
	}

	/**
	 * Returns the weight of edge from vertex {@code v} to vertex {@code w}.
	 *
	 * @param v tail vertex
	 * @param w head vertex
	 * @return weight of edge from vertex {@code v} to vertex {@code w}
	 */
	public int getWeight(int v, int w) {
		return adjM[v][w];
	}

	/**
	 * Computes possible paths from source vertex {@code src} to different vertices.
	 * in the reverse graph.
	 *
	 * @param src source vertex
	 */
	private void reverse(int src) {
		Boolean[] marked = new Boolean[numOfVertices];
		Arrays.fill(marked, Boolean.FALSE);
		Queue<Integer> vertQ = new LinkedList<Integer>();
		vertQ.add(src);
		while (!vertQ.isEmpty()) {
			int v = vertQ.poll();
			for (Edge e : adjR.get(v)) {
				int w = e.to();
				if (!marked[w]) {
					vertQ.add(w);
					marked[w] = true;
				}
			}
		}
		map.put(src, marked);
	}

	/**
	 * Returns if a path exists from source vertex to destination vertex.
	 *
	 * @param src source vertex
	 * @param dst destination vertex
	 * @return path exists from source vertex {@code src} to destination vertex
	 *         {@code dst} as a boolean
	 */
	public boolean hasPath(int src, int dst) {
		if (!map.containsKey(dst)) {
			reverse(dst);
		}
		return map.get(dst)[src];
	}
}