package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void intGraph() {
        Graph<Integer> list = new AdjacencyListGraph<>();
        Vertex<Integer> a = new Vertex<>("a", 8);
        list.addVertex(a);
        Vertex<Integer> b = new Vertex<>("b", 10);
        list.addVertex(b);
        Vertex<Integer> c = new Vertex<>("c", 2);
        list.addVertex(c);
        Vertex<Integer> d = new Vertex<>("d", 5);
        list.addVertex(d);
        Vertex<Integer> e = new Vertex<>("e", 10);
        list.addVertex(e);

        list.addEdge(a,b);
        list.addEdge(b,c);
        list.addEdge(a,d);
        list.addEdge(a,e);

        List<Vertex<Integer>> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        vertices.add(e);

        List<Vertex<Integer>> aNeighbours = new ArrayList<>();
        aNeighbours.add(b);
        aNeighbours.add(d);
        aNeighbours.add(e);

        List<Vertex<Integer>> bNeighbours = new ArrayList<>();
        bNeighbours.add(c);

        List<Vertex<Integer>> dNeighbours = new ArrayList<>();

        assertEquals(list.getVertices(),vertices);
        assertEquals(list.getNeighbors(a), aNeighbours);
        assertEquals(list.getNeighbors(b), bNeighbours);
        assertEquals(list.getNeighbors(d), dNeighbours);

    }

}
