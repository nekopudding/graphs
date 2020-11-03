package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.graphs.one.Algorithms;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

import static ca.ubc.ece.cpen221.graphs.one.Algorithms.*;


public class Tests {
    @Test
    public void test_minDistance() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Vertex<Integer> v1 = new Vertex<Integer>("two", 2);
        Vertex<Integer> v2 = new Vertex<>("three", 3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(v1, v2);

        int expectedOut = 1;
        assertEquals(expectedOut, shortestDistance(g, v1, v2));
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
        g.addEdge(v1, v3);
        g.addEdge(v2, v4);
        g.addEdge(v3, v4);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2, 2));
        //System.out.println(startComputeDistance(g,v1,v4));
        assertEquals(expected, startComputeDistance(g, v1, v4));
    }

    @Test
    public void test_dfs() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Vertex<Integer> v1 = new Vertex<Integer>("a", 10);
        Vertex<Integer> v2 = new Vertex<>("b", 4);
        Vertex<Integer> v3 = new Vertex<>("c", 4);
        Vertex<Integer> v4 = new Vertex<>("d", 6);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v4);
        g.addEdge(v3, v4);

        List<Vertex<Integer>> path1 = new ArrayList<>();
        path1.add(v1);
        path1.add(v2);
        path1.add(v4);
        path1.add(v3);
        List<Vertex<Integer>> path2 = new ArrayList<>();
        path2.add(v2);
        path2.add(v4);
        List<Vertex<Integer>> path3 = new ArrayList<>();
        path3.add(v3);
        path3.add(v4);
        List<Vertex<Integer>> path4 = new ArrayList<>();
        path4.add(v4);

        Set<List<Vertex<Integer>>> expected = new HashSet<List<Vertex<Integer>>>();
        expected.add(path1);
        expected.add(path2);
        expected.add(path3);
        expected.add(path4);

        assertEquals(expected, depthFirstSearch(g));
    }

    @Test
    public void test_bfs() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Vertex<Integer> v1 = new Vertex<Integer>("a", 10);
        Vertex<Integer> v2 = new Vertex<>("b", 4);
        Vertex<Integer> v3 = new Vertex<>("c", 4);
        Vertex<Integer> v4 = new Vertex<>("d", 6);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v4);
        g.addEdge(v3, v4);
        g.addEdge(v2, v3);

        List<Vertex<Integer>> path1 = new ArrayList<>();
        path1.add(v1);
        path1.add(v2);
        path1.add(v3);
        path1.add(v4);
        List<Vertex<Integer>> path2 = new ArrayList<>();
        path2.add(v2);
        path2.add(v3);
        path2.add(v4);
        List<Vertex<Integer>> path3 = new ArrayList<>();
        path3.add(v3);
        path3.add(v4);
        List<Vertex<Integer>> path4 = new ArrayList<>();
        path4.add(v4);

        Set<List<Vertex<Integer>>> expected = new HashSet<>();
        expected.add(path1);
        expected.add(path2);
        expected.add(path3);
        expected.add(path4);

        assertEquals(expected, breadthFirstSearch(g));
    }

    @Test
    public void test_dfsEmpty() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Set<List<Vertex<Integer>>> expected = new HashSet<>();
        assertEquals(expected, depthFirstSearch(g));
    }

    @Test
    public void test_bfsEmpty() {
        Graph<Integer> g = new AdjacencyMatrixGraph<>();
        Set<List<Vertex<Integer>>> expected = new HashSet<>();
        assertEquals(expected, breadthFirstSearch(g));
    }

}
