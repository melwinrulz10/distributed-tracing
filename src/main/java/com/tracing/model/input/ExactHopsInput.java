package com.tracing.model.input;

import lombok.Builder;
import lombok.Value;

/**
 * The {@code ExactHopsInput} class represents input for exact hops case with
 * source {@code src} vertex, destination {@code dst} vertex and number of
 * {@code hops}.
 *
 * @author Melwin Mathew
 * @version 1.0
 * @since 01-07-2022
 */

@Builder
@Value
public class ExactHopsInput {
	int src;
	int dst;
	int hops;
}