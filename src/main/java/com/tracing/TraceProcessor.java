package com.tracing;

import com.tracing.model.graph.Edge;
import com.tracing.model.input.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The {@code TraceProcessor} class represents a utility class for finding
 * number of paths in an edge-weighted graph from a source vertex {@code src} to
 * a destination vertex {@code dst} for a given number of hops and amount of
 * latency.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class TraceProcessor {

	/**
	 * Find the average latency from the path of given list of vertices in the graph
	 * {@code G}.
	 *
	 * @param graph               graph
	 * @param averageLatencyInput average latency input object
	 * @return average latency
	 */
	public long findAverageLatency(Graph graph, AverageLatencyInput averageLatencyInput) {
		long result = 0;
		int src, dst;
		List<Integer> vertices = averageLatencyInput.getVertices();

		for (int j = 0; j < vertices.size() - 1; j++) {
			src = vertices.get(j);
			dst = vertices.get(j + 1);
			if (src >= graph.getNumOfVertices() || dst >= graph.getNumOfVertices()) {
				return 0;
			}

			result += graph.getWeight(src, dst);

			if (graph.getWeight(src, dst) == 0) {
				return 0;
			}
			if (j == vertices.size() - 2) {
				return result;
			}
		}
		return result;
	}

	/**
	 * Find the number of paths from the source vertex {@code src} to the
	 * destination vertex {@code dst} in the graph {@code G} for all values upto the
	 * given {@code hops} value.
	 *
	 * @param graph         graph
	 * @param uptoHopsInput upto hops input object
	 * @return number of paths
	 */
	public long findUptoHops(Graph graph, UptoHopsInput uptoHopsInput) {
		long result = 0;
		int src = uptoHopsInput.getSrc();
		int dst = uptoHopsInput.getDst();
		int hops = uptoHopsInput.getHops();

		if (src >= graph.getNumOfVertices() || dst >= graph.getNumOfVertices()) {
			return 0;
		}

		Queue<Integer> vertQ = new LinkedList<Integer>();
		Queue<Integer> distQ = new LinkedList<Integer>();
		vertQ.add(src);
		distQ.add(0);

		while (!vertQ.isEmpty()) {
			int v = vertQ.remove();
			int count = distQ.remove();

			if (graph.hasPath(v, dst)) {
				for (Edge e : graph.adj(v)) {
					int w = e.to();
					if (count + 1 <= hops) {
						vertQ.add(w);
						distQ.add(count + 1);
						if (w == dst) {
							result++;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Find the number of paths from the source vertex {@code src} to the
	 * destination vertex {@code dst} in the graph {@code G} for an exact
	 * {@code hops} value.
	 *
	 * @param graph          graph
	 * @param exactHopsInput exact hops input object
	 * @return number of paths
	 */
	public long findExactHops(Graph graph, ExactHopsInput exactHopsInput) {
		long result = 0;
		int src = exactHopsInput.getSrc();
		int dst = exactHopsInput.getDst();
		int hops = exactHopsInput.getHops();

		if (src >= graph.getNumOfVertices() || dst >= graph.getNumOfVertices()) {
			return 0;
		}

		Queue<Integer> vertQ = new LinkedList<Integer>();
		Queue<Integer> distQ = new LinkedList<Integer>();
		vertQ.add(src);
		distQ.add(0);

		while (!vertQ.isEmpty()) {
			int v = vertQ.remove();
			int count = distQ.remove();

			if (graph.hasPath(v, dst)) {
				for (Edge e : graph.adj(v)) {
					int w = e.to();
					if (count + 1 < hops) {
						vertQ.add(w);
						distQ.add(count + 1);
					} else if (count + 1 == hops) {
						if (w == dst) {
							result++;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Find shortest trace from the source vertex {@code src} to the destination
	 * vertex {@code dst} in the graph {@code G}.
	 *
	 * @param graph             graph
	 * @param shortestPathInput shortest path input object
	 * @return shortest trace
	 */
	public long findShortestPath(Graph graph, ShortestPathInput shortestPathInput) {
		long result = 0;
		int src = shortestPathInput.getSrc();
		int dst = shortestPathInput.getDst();

		if (src >= graph.getNumOfVertices() || dst >= graph.getNumOfVertices()) {
			return 0;
		}

		ShortestPath sp = new ShortestPath(graph, src);
		result = sp.distTo(dst);
		if (result == Integer.MAX_VALUE) {
			return 0;
		}
		return result;
	}

	/**
	 * Find number of paths from the source vertex {@code src} to the destination
	 * vertex {@code dst} in the graph {@code G} for all values less than the given
	 * {@code latency} latency value.
	 *
	 * @param graph            graph
	 * @param uptoLatencyInput upto latency input object
	 * @return number of paths
	 */
	public long findUptoLatency(Graph graph, UptoLatencyInput uptoLatencyInput) {
		long result = 0;
		int src = uptoLatencyInput.getSrc();
		int dst = uptoLatencyInput.getDst();
		int latency = uptoLatencyInput.getLatency();

		if (src >= graph.getNumOfVertices() || dst >= graph.getNumOfVertices()) {
			return 0;
		}

		Queue<Integer> vertQ = new LinkedList<Integer>();
		Queue<Integer> distQ = new LinkedList<Integer>();
		vertQ.add(src);
		distQ.add(0);

		while (!vertQ.isEmpty()) {
			int v = vertQ.remove();
			int count = distQ.remove();

			if (graph.hasPath(v, dst)) {
				for (Edge e : graph.adj(v)) {
					int w = e.to();
					if (count + graph.getWeight(v, w) < latency) {
						vertQ.add(w);
						distQ.add(count + graph.getWeight(v, w));
						if (w == dst) {
							result++;
						}
					}
				}
			}
		}
		return result;
	}
}