package com.tracing.model.input;

import lombok.Builder;
import lombok.Value;

/**
 * The {@code TracingInput} class represents complete input from the input test
 * file.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public class TracingInput {
	GraphInput graphInput;
	AverageLatencyInput[] averageLatencyInput;
	UptoHopsInput uptoHopsInput;
	ExactHopsInput exactHopsInput;
	ShortestPathInput[] shortestPathInput;
	UptoLatencyInput uptoLatencyInput;
}