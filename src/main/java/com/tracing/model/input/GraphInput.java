package com.tracing.model.input;

import java.util.List;

import com.tracing.model.graph.Edge;

import lombok.Builder;
import lombok.Value;

/**
 * The {@code GraphInput} class represents input for graph with edges and their
 * latency values.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public final class GraphInput {
	List<Edge> edges;
}