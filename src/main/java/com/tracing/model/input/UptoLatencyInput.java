package com.tracing.model.input;

import lombok.Builder;
import lombok.Value;

/**
 * The {@code UptoLatencyInput} class represents input for upto latency case
 * with source {@code src} vertex, destination {@code dst} vertex and amount of
 * {@code latency}.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public class UptoLatencyInput {
	int src;
	int dst;
	int latency;
}