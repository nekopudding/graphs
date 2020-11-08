package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdjacencyListGraphTests {

    @Test
    public void stringGraph() {
        Graph<String> graph = new AdjacencyListGraph<>();
        Vertex<String> first = new Vertex<String>("first", "Somebody once told me");
        graph.addVertex(first);
        Vertex<String> second = new Vertex<String>("second", "the world is gonna roll me");
        graph.addVertex(second);
        graph.addEdge(first, second);
        Vertex<String> third = new Vertex<String>("third", "I ain't the sharpest tool in the shed");
        graph.addVertex(third);
        graph.addEdge(second, third);
        graph.addEdge(third, first);
        Vertex<String> isolated = new Vertex<String>("iso", "She was looking kinda dumb");
        graph.addVertex(isolated);

        assertTrue(graph.edgeExists(first, second));
        assertTrue(!graph.edgeExists(first,third));
        assertTrue(!graph.edgeExists(isolated, third));
    }

}
