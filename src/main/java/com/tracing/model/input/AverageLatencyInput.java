package com.tracing.model.input;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * The {@code AverageLatencyInput} class represents input for average latency
 * cases with list of vertices.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public class AverageLatencyInput {
	List<Integer> vertices;
}