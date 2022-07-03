package com.tracing.model.input;

import lombok.Builder;
import lombok.Value;

/**
 * The {@code ShortestPathInput} class represents input for shortest path case
 * with source {@code src} vertex and destination {@code dst} vertex.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public class ShortestPathInput {
	int src;
	int dst;
}