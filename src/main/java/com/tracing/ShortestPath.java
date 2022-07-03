package com.tracing;

import com.tracing.model.graph.Edge;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * The {@code ShortestPath} class represents the shortest path in an
 * edge-weighted graph to all vertices from a source vertex {@code s}. The
 * shortest path is implemented using the Dijkstra Shortest Path algorithm where
 * edges would have non-negative weights.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class ShortestPath {
	private Edge[] edgeTo;
	private int[] distTo;
	private PriorityQueue<Node> pq;

	/**
	 * Calculate shortest path from the source vertex {@code s} to every other
	 * vertex in the graph {@code G}.
	 *
	 * @param graph graph
	 * @param src   source vertex
	 */
	public ShortestPath(Graph graph, int src) {
		edgeTo = new Edge[graph.getNumOfVertices()];
		distTo = new int[graph.getNumOfVertices()];
		pq = new PriorityQueue<Node>(graph.getNumOfVertices());

		for (int v = 0; v < graph.getNumOfVertices(); v++) {
			distTo[v] = Integer.MAX_VALUE;
		}

		distTo[src] = 0;
		pq.add(new Node(src, 0));
		while (!pq.isEmpty()) {
			Node v = pq.poll();
			for (Edge e : graph.adj(v.getVertex())) {
				relax(e);
			}
			if (distTo[src] == 0) {
				distTo[src] = Integer.MAX_VALUE;
			}
		}
	}

	/**
	 * Returns the length of shortest path from the source vertex {@code s} to
	 * vertex {@code v}.
	 * 
	 * @param dst destination vertex
	 * @return length of shortest path from the source vertex {@code s} to vertex
	 *         {@code v}
	 */
	public int distTo(int dst) {
		return distTo[dst];
	}

	/**
	 * Returns shortest path from the source vertex {@code s} to vertex {@code v}.
	 *
	 * @param dst destination vertex
	 * @return shortest path from the source vertex {@code s} to vertex {@code v} as
	 *         an Iterable of edges
	 */
	public Iterable<Edge> pathTo(int dst) {
		Stack<Edge> path = new Stack<Edge>();
		for (Edge e = edgeTo[dst]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	/**
	 * Update distance to head vertex {@code w} with latest shortest value and
	 * update the priority queue.
	 * 
	 */
	private void relax(Edge e) {
		int v = e.from(), w = e.to();
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			pq.add(new Node(w, distTo[w]));
		}
	}
}

/**
 * The class {@code Node} is used to create Priority Queue for computing the
 * shortest path from source vertex to all destination vertices in the graph
 * {@code G}. A custom class implementing Comparable is used as key of Priority
 * Queue to calculate the shortest distance using weight of the edges.
 *
 */
class Node implements Comparable<Node> {
	private int vertex;
	private int dist;

	/**
	 * Initializes a node.
	 *
	 * @param vertex vertex
	 * @param dist   distance
	 */
	Node(int vertex, int dist) {
		this.vertex = vertex;
		this.dist = dist;
	}

	/**
	 * Returns the vertex.
	 *
	 * @return vertex
	 */
	public int getVertex() {
		return vertex;
	}

	/**
	 * Returns the distance.
	 *
	 * @return vertex
	 */
	public int getDistance() {
		return dist;
	}

	/**
	 * Compares two nodes by distance.
	 *
	 * @param that other node
	 * @return -1, 0, or 1 depending on whether the distance of this node is less
	 *         than, equal to, or greater than the other node
	 */
	@Override
	public int compareTo(Node that) {
		return Integer.compare(this.dist, that.dist);
	}
}