package com.tracing;

import com.tracing.model.input.AverageLatencyInput;
import com.tracing.model.input.InputParser;
import com.tracing.model.input.ShortestPathInput;
import com.tracing.model.input.TracingInput;

/**
 * The {@code Main} class represents the main class for executing the
 * application. Shared input is given in the "\src\main\resources\input.txt"
 * file and output will be displayed on the console for the 10 input scenarios.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

public class Main {

	/**
	 * Main function for executing the application.
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("The input test files should be provided as argument!");
			System.exit(1);
		}

		for (String filePath : args) {
			long result = 0;
			InputParser inputParser = new InputParser();
			TracingInput tracingInput = inputParser.parse(filePath);
			Graph graph = new Graph(tracingInput.getGraphInput());
			TraceProcessor traceProcessor = new TraceProcessor();
			for (AverageLatencyInput averageLatencyInput : tracingInput.getAverageLatencyInput()) {
				result = traceProcessor.findAverageLatency(graph, averageLatencyInput);
				printResult(result);
			}
			result = traceProcessor.findUptoHops(graph, tracingInput.getUptoHopsInput());
			printResult(result);
			result = traceProcessor.findExactHops(graph, tracingInput.getExactHopsInput());
			printResult(result);
			for (ShortestPathInput shortestPathInput : tracingInput.getShortestPathInput()) {
				result = traceProcessor.findShortestPath(graph, shortestPathInput);
				printResult(result);
			}
			result = traceProcessor.findUptoLatency(graph, tracingInput.getUptoLatencyInput());
			printResult(result);
		}
	}

	/**
	 * Function for printing the result.
	 *
	 * @param result result
	 */
	private static void printResult(long result) {
		if (result == 0) {
			System.out.println("NO SUCH TRACE");
		} else {
			System.out.println(result);
		}
	}
}