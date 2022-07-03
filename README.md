# distributed-tracing


Steps to run the application
----------------------------

 1. Run the application with absolute path of input files separated by space (" ") as command line arguments. 
 	
	eg: java -cp <java_application_jar> com.tracing.Main <absolute_path_1> <absolute_path_2> , 
		or
	<absolute_path_1> <absolute_path_2> in the section "Program arguments" of "Run\Edit Configurations..." option in IntelliJ IDE.
	    
 2. The output will be displayed on the console.


Project Files
-------------

src\main\java\com.tracing.model.graph
 1. Edge.java - The Edge class represents a weighted edge in an edge-weighted graph. Each edge consists of two vertices and a weight.


src\main\java\com.tracing.model.input
 1. AverageLatencyInput - The AverageLatencyInput class represents input for average latency cases with list of vertices.
 2. ExactHopsInput - The ExactHopsInput class represents input for exact hops case with source vertex, destination  vertex and number of hops.
 3. GraphInput - The GraphInput class represents input for graph with edges and their latency values.
 4. InputParser - The InputParser class represents input parser file where different type of inputs are created from the input test file.
 5. ShortestPathInput - The ShortestPathInput class represents input for shortest path case with source vertex and destination vertex.
 6. TracingInput - The TracingInput class represents complete input from the input test file.
 7. UptoHopsInput - The UptoHopsInput class represents input for upto hops case with source vertex, destination vertex and number of hops.
 8. UptoLatencyInput - The UptoLatencyInput class represents input for upto latency case with source vertex, destination vertex and amount of latency.


src\main\java\com.tracing
 1. Graph.java - The Graph class represents an edge-weighted graph with V vertices. It supports both adjacency list and adjacency matrix representation.
 2. Main.java - The Main class represents the main class for executing the application. Shared input is given in the "\src\main\resources\input.txt" file and output will be displayed on the console for the 10 input scenarios.
 3. ShortestPath.java - The ShortestPath class represents the shortest path in an edge-weighted graph to all vertices from a source vertex. The shortest path is implemented using the Dijkstra Shortest Path algorithm where edges would have non-negative weights.
 4. TraceProcessor - The TraceProcessor class represents a utility class for finding number of paths in an edge-weighted graph from a source vertex to a destination vertex for a given number of hops and amount of latency.


src\main\resources
 1. input.txt - The shared input file.
 
 
src\test\java\com.tracing
 1. TraceProcessorTest - The TraceProcessorTest class represents test class of TraceProcessor.
 
 
src\test\resources\problems
 1. test_input1.txt - The test input file 1.
 2. test_input2.txt - The test input file 2.
 
src\test\resources\solutions
 1. solution1.txt - Solution of test input file 1.
 2. solution2.txt - Solution of test input file 2.
