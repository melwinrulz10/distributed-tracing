package com.tracing.model.input;

import com.tracing.model.graph.Edge;
import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code InputParser} class represents input parser file where different
 * type of inputs are created from the input test file.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class InputParser {
	private static final int EXPECTED_INPUT_LINES = 11;
	private static final int NUM_OF_AVG_LATENCY_INPUT_LINES = 5;
	private static final int NUM_OF_SHORTEST_PATH_INPUT_LINES = 2;
	private static final int EXPECTED_MIN_ENTRIES_AVG_LATENCY_INPUT = 2;
	private static final int EXPECTED_ENTRIES_UPTO_HOPS_INPUT = 3;
	private static final int EXPECTED_ENTRIES_EXACT_HOPS_INPUT = 3;
	private static final int EXPECTED_ENTRIES_SHORTEST_PATH_INPUT = 2;
	private static final int EXPECTED_ENTRIES_UPTO_LATENCY_INPUT = 3;
	private static final int GRAPH_INPUT_INDEX = 0;
	private static final int AVG_LATENCY_INPUT_START_INDEX = 1;
	private static final int AVG_LATENCY_INPUT_END_INDEX = 5;
	private static final int UPTO_HOPS_INPUT_INDEX = 6;
	private static final int EXACT_HOPS_INPUT_INDEX = 7;
	private static final int SHORTEST_PATH_INPUT_START_INDEX = 8;
	private static final int SHORTEST_PATH_INPUT_END_INDEX = 9;
	private static final int UPTO_LATENCY_INPUT_INDEX = 10;
	private static final char CHARACTER_A = 'A';

	/**
	 * Parses a given input test file to generate a tracing input object.
	 *
	 * @param fileName input test file
	 * @return tracing input object
	 */
	public TracingInput parse(@NonNull final String fileName) {
		try {
			List<String> input = Files.readAllLines(Paths.get(fileName));
			if (input.size() != EXPECTED_INPUT_LINES) {
				throw new RuntimeException("Expected input lines length is " + EXPECTED_INPUT_LINES);
			}
			GraphInput graphInput = createGraphInput(input.get(GRAPH_INPUT_INDEX));
			AverageLatencyInput[] averageLatencyInput = new AverageLatencyInput[NUM_OF_AVG_LATENCY_INPUT_LINES];
			for (int i = AVG_LATENCY_INPUT_START_INDEX, j = 0; i <= AVG_LATENCY_INPUT_END_INDEX; i++) {
				averageLatencyInput[j++] = createAverageLatencyInput(input.get(i));
			}
			UptoHopsInput uptoHopsInput = createUptoHopsInput(input.get(UPTO_HOPS_INPUT_INDEX));
			ExactHopsInput exactHopsInput = createExactHopsInput(input.get(EXACT_HOPS_INPUT_INDEX));
			ShortestPathInput[] shortestPathInput = new ShortestPathInput[NUM_OF_SHORTEST_PATH_INPUT_LINES];
			for (int i = SHORTEST_PATH_INPUT_START_INDEX, j = 0; i <= SHORTEST_PATH_INPUT_END_INDEX; i++) {
				shortestPathInput[j++] = createShortestPathInput(input.get(i));
			}
			UptoLatencyInput uptoLatencyInput = createUptoLatencyInput(input.get(UPTO_LATENCY_INPUT_INDEX));

			return TracingInput.builder().graphInput(graphInput).averageLatencyInput(averageLatencyInput)
					.uptoHopsInput(uptoHopsInput).exactHopsInput(exactHopsInput).shortestPathInput(shortestPathInput)
					.uptoLatencyInput(uptoLatencyInput).build();
		} catch (IOException e) {
			throw new RuntimeException("Failed to read " + fileName, e);
		}
	}

	/**
	 * Parses a given input string to generate a graph input object.
	 *
	 * @param input input
	 * @return graph input object
	 */
	private GraphInput createGraphInput(String input) {
		List<Edge> edges = new ArrayList<>();
		String[] conns = input.split(", ");
		for (String conn : conns) {
			int v = conn.charAt(0) - CHARACTER_A;
			int w = conn.charAt(1) - CHARACTER_A;
			int weight = Integer.parseInt(conn.substring(2));
			edges.add(new Edge(v, w, weight));
		}
		return GraphInput.builder().edges(edges).build();
	}

	/**
	 * Parses a given input string to generate an average latency input object.
	 *
	 * @param input input
	 * @return average latency input object
	 */
	private AverageLatencyInput createAverageLatencyInput(String input) {
		List<Integer> vertices = Arrays.stream(input.split(",")).map((c) -> c.charAt(0) - CHARACTER_A)
				.collect(Collectors.toList());
		if (vertices.size() < EXPECTED_MIN_ENTRIES_AVG_LATENCY_INPUT) {
			throw new RuntimeException("Expected min input entries for average latency input is "
					+ EXPECTED_MIN_ENTRIES_AVG_LATENCY_INPUT);
		}
		return AverageLatencyInput.builder().vertices(vertices).build();
	}

	/**
	 * Parses a given input string to generate an upto hops input object.
	 *
	 * @param input input
	 * @return upto hops input object
	 */
	private UptoHopsInput createUptoHopsInput(String input) {
		String[] in = input.split(",");
		if (in.length != EXPECTED_ENTRIES_UPTO_HOPS_INPUT) {
			throw new RuntimeException(
					"Expected input entries for upto hops input is " + EXPECTED_ENTRIES_UPTO_HOPS_INPUT);
		}
		return UptoHopsInput.builder().src(in[0].charAt(0) - CHARACTER_A).dst(in[1].charAt(0) - CHARACTER_A)
				.hops(Integer.parseInt(in[2])).build();
	}

	/**
	 * Parses a given input string to generate an exact hops input object.
	 *
	 * @param input input
	 * @return exact hops input object
	 */
	private ExactHopsInput createExactHopsInput(String input) {
		String[] in = input.split(",");
		if (in.length != EXPECTED_ENTRIES_EXACT_HOPS_INPUT) {
			throw new RuntimeException(
					"Expected input entries for exact hops input is " + EXPECTED_ENTRIES_EXACT_HOPS_INPUT);
		}
		return ExactHopsInput.builder().src(in[0].charAt(0) - CHARACTER_A).dst(in[1].charAt(0) - CHARACTER_A)
				.hops(Integer.parseInt(in[2])).build();
	}

	/**
	 * Parses a given input string to generate a shortest path input object.
	 *
	 * @param input input
	 * @return shortest path input object
	 */
	private ShortestPathInput createShortestPathInput(String input) {
		String[] in = input.split(",");
		if (in.length != EXPECTED_ENTRIES_SHORTEST_PATH_INPUT) {
			throw new RuntimeException(
					"Expected input entries for shortest path input is " + EXPECTED_ENTRIES_SHORTEST_PATH_INPUT);
		}
		return ShortestPathInput.builder().src(in[0].charAt(0) - CHARACTER_A).dst(in[1].charAt(0) - CHARACTER_A)
				.build();
	}

	/**
	 * Parses a given input string to generate an upto latency input object.
	 *
	 * @param input input
	 * @return upto latency input object
	 */
	private UptoLatencyInput createUptoLatencyInput(String input) {
		String[] in = input.split(",");
		if (in.length != EXPECTED_ENTRIES_UPTO_LATENCY_INPUT) {
			throw new RuntimeException(
					"Expected input entries for upto latency input is " + EXPECTED_ENTRIES_UPTO_LATENCY_INPUT);
		}
		return UptoLatencyInput.builder().src(in[0].charAt(0) - CHARACTER_A).dst(in[1].charAt(0) - CHARACTER_A)
				.latency(Integer.parseInt(in[2])).build();
	}
}