package com.tracing;

import com.tracing.model.input.InputParser;
import com.tracing.model.input.TracingInput;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code TraceProcessorTest} class represents test class of TraceProcessor.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class TraceProcessorTest extends TestCase {
	private static final Path TEST_RESOURCES_PATH_PROBLEMS = Paths.get(System.getProperty("user.dir"), "src", "test",
			"resources", "problems");
	private static final Path TEST_RESOURCES_PATH_SOLUTIONS = Paths.get(System.getProperty("user.dir"), "src", "test",
			"resources", "solutions");
	private static final int AVG_LATENCY_INPUT_START_INDEX = 0;
	private static final int AVG_LATENCY_INPUT_END_INDEX = 4;
	private static final int UPTO_HOPS_INPUT_INDEX = 5;
	private static final int EXACT_HOPS_INPUT_INDEX = 6;
	private static final int SHORTEST_PATH_INPUT_START_INDEX = 7;
	private static final int SHORTEST_PATH_INPUT_END_INDEX = 8;
	private static final int UPTO_LATENCY_INPUT_INDEX = 9;
	private static final int ZERO = 0;
	private List<File> filesProblems;
	private List<File> filesSolutions;
	private TracingInput[] tracingInputs;
	private List<List<String>> solutions;

	public void setUp() throws Exception {
		super.setUp();
		int i = ZERO;
		filesProblems = Arrays.stream(new File(TEST_RESOURCES_PATH_PROBLEMS.toString()).listFiles()).sorted()
				.collect(Collectors.toList());
		InputParser inputParser = new InputParser();
		tracingInputs = new TracingInput[filesProblems.size()];
		for (File file : filesProblems) {
			tracingInputs[i++] = inputParser.parse(String.valueOf(file));
		}

		filesSolutions = Arrays.stream(new File(TEST_RESOURCES_PATH_SOLUTIONS.toString()).listFiles()).sorted()
				.collect(Collectors.toList());
		solutions = new ArrayList<>(filesSolutions.size());
		i = ZERO;
		for (File file : filesSolutions) {
			try {
				List<String> solution = Files.readAllLines(file.toPath());
				solutions.add(i++, solution);
			} catch (IOException e) {
				throw new RuntimeException("Failed to read the solution file from test resources folder.");
			}
		}
		if (tracingInputs.length != solutions.size()) {
			throw new RuntimeException("The number of problems files and solutions files do not match.");
		}
	}

	public void testFindAverageLatency() {
		int i = ZERO;
		for (List<String> solution : solutions) {
			TraceProcessor traceProcessor = new TraceProcessor();
			Graph graph = new Graph(tracingInputs[i].getGraphInput());
			for (int j = AVG_LATENCY_INPUT_START_INDEX; j <= AVG_LATENCY_INPUT_END_INDEX; j++) {
				long expected = Long.parseLong(solution.get(j));
				long actual = traceProcessor.findAverageLatency(graph, tracingInputs[i].getAverageLatencyInput()[j]);
				assertEquals("Error in " + filesProblems.get(i).getName() + " : Test Case - " + (j + 1), expected,
						actual);
			}
			i++;
		}
	}

	public void testFindUptoHops() {
		int i = ZERO;
		for (List<String> solution : solutions) {
			TraceProcessor traceProcessor = new TraceProcessor();
			Graph graph = new Graph(tracingInputs[i].getGraphInput());
			long expected = Long.parseLong(solution.get(UPTO_HOPS_INPUT_INDEX));
			long actual = traceProcessor.findUptoHops(graph, tracingInputs[i].getUptoHopsInput());
			assertEquals("Error in " + filesProblems.get(i).getName() + " : Test Case - " + (UPTO_HOPS_INPUT_INDEX + 1),
					expected, actual);
			i++;
		}
	}

	public void testFindExactHops() {
		int i = ZERO;
		for (List<String> solution : solutions) {
			TraceProcessor traceProcessor = new TraceProcessor();
			Graph graph = new Graph(tracingInputs[i].getGraphInput());
			long expected = Long.parseLong(solution.get(EXACT_HOPS_INPUT_INDEX));
			long actual = traceProcessor.findExactHops(graph, tracingInputs[i].getExactHopsInput());
			assertEquals(
					"Error in " + filesProblems.get(i).getName() + " : Test Case - " + (EXACT_HOPS_INPUT_INDEX + 1),
					expected, actual);
			i++;
		}
	}

	public void testFindShortestPath() {
		int i = ZERO;
		for (List<String> solution : solutions) {
			TraceProcessor traceProcessor = new TraceProcessor();
			Graph graph = new Graph(tracingInputs[i].getGraphInput());
			for (int j = SHORTEST_PATH_INPUT_START_INDEX; j <= SHORTEST_PATH_INPUT_END_INDEX; j++) {
				long expected = Long.parseLong(solution.get(j));
				long actual = traceProcessor.findShortestPath(graph, tracingInputs[i].getShortestPathInput()[j - 7]);
				assertEquals("Error in " + filesProblems.get(i).getName() + " : Test Case - " + (j + 1), expected,
						actual);
			}
			i++;
		}
	}

	public void testFindUptoLatency() {
		int i = ZERO;
		for (List<String> solution : solutions) {
			TraceProcessor traceProcessor = new TraceProcessor();
			Graph graph = new Graph(tracingInputs[i].getGraphInput());
			long expected = Long.parseLong(solution.get(UPTO_LATENCY_INPUT_INDEX));
			long actual = traceProcessor.findUptoLatency(graph, tracingInputs[i].getUptoLatencyInput());
			assertEquals(
					"Error in " + filesProblems.get(i).getName() + " : Test Case - " + (UPTO_LATENCY_INPUT_INDEX + 1),
					expected, actual);
			i++;
		}
	}
}