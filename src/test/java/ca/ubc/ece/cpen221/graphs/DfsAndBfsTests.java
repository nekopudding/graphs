package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.graphs.one.Algorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

import static ca.ubc.ece.cpen221.graphs.one.Algorithms.*;



public class DfsAndBfsTests {
    @Test
    public void test_minDistance() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Vertex<Integer> v1 = new Vertex<Integer>("two", 2);
        Vertex<Integer> v2 = new Vertex<>("three", 3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(v1, v2);

        int expectedOut = 1;
        assertEquals(expectedOut, shortestDistance(g,v1,v2));
    }

    @Test
    public void test_minDistance2() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Vertex<Integer> v1 = new Vertex<Integer>("two", 2);
        Vertex<Integer> v2 = new Vertex<>("three", 3);
        Vertex<Integer> v3 = new Vertex<>("four", 4);
        Vertex<Integer> v4 = new Vertex<>("five", 5);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v1,v3);
        g.addEdge(v2,v4);
        g.addEdge(v3,v4);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2, 2));
        //System.out.println(startComputeDistance(g,v1,v4));
        assertEquals(expected, startComputeDistance(g,v1,v4));
    }



}
