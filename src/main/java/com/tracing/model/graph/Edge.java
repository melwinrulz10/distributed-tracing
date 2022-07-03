package com.tracing.model.graph;

/**
 * The {@code Edge} class represents a weighted edge in an edge-weighted graph.
 * Each edge consists of two vertices (integer type) and a weight (integer
 * type).
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class Edge implements Comparable<Edge> {
	private final int v, w;
	private final int weight;

	/**
	 * Initializes an edge between 2 vertices {@code v} and {@code w} with the given
	 * {@code weight}.
	 *
	 * @param v      tail vertex
	 * @param w      head vertex
	 * @param weight weight of the edge
	 */
	public Edge(int v, int w, int weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	/**
	 * Returns the tail vertex of the edge.
	 * 
	 * @return tail vertex of the edge
	 */
	public int from() {
		return v;
	}

	/**
	 * Returns the head vertex of the edge.
	 * 
	 * @return head vertex of the edge
	 */
	public int to() {
		return w;
	}

	/**
	 * Returns the weight of the edge.
	 *
	 * @return weight of the edge
	 */
	public int weight() {
		return weight;
	}

	/**
	 * Compares two edges by weight.
	 *
	 * @param that other edge
	 * @return -1, 0, or 1 depending on whether the weight of this edge is less
	 *         than, equal to, or greater than the other edge
	 */
	public int compareTo(Edge that) {
		return Integer.compare(this.weight, that.weight);
	}
}