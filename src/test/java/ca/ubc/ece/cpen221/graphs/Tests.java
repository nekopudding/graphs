package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
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

    @Test
    public void test_downstreamVertices() {
        Graph<Integer> list = new AdjacencyListGraph<>();
        Vertex<Integer> a = new Vertex<>("eight", 8);
        list.addVertex(a);
        Vertex<Integer> b = new Vertex<>("ten", 10);
        list.addVertex(b);
        Vertex<Integer> c = new Vertex<>("two", 2);
        list.addVertex(c);
        Vertex<Integer> d = new Vertex<>("five", 5);
        list.addVertex(d);
        Vertex<Integer> e = new Vertex<>("six", 6);
        list.addVertex(e);
        Vertex<Integer> f = new Vertex<>("one", 1);
        list.addVertex(f);
        Vertex<Integer> g = new Vertex<>("nine", 9);
        list.addVertex(g);
        Vertex<Integer> h = new Vertex<>("thirty", 30);
        list.addVertex(h);

        list.addEdge(a,b);
        list.addEdge(b,c);
        list.addEdge(d,b);
        list.addEdge(a,e);
        list.addEdge(b,f);
        list.addEdge(e,f);
        list.addEdge(f,g);
        list.addEdge(c,g);
        list.addEdge(c,h);
        list.addEdge(f,h);

        List<Vertex<Integer>> be = new ArrayList<>();
        be.add(f);
        assertEquals(be, Algorithms.downstreamVertices(list,b,e));
        List<Vertex<Integer>> cf = new ArrayList<>();
        cf.add(g);
        cf.add(h);
        assertEquals(cf, Algorithms.downstreamVertices(list,c,f));
        assertEquals(new ArrayList<>(), Algorithms.downstreamVertices(list,a,c));
    }

    @Test
    public void test_upstreamVertices() {
        Graph<Integer> list = new AdjacencyListGraph<>();
        Vertex<Integer> a = new Vertex<>("eight", 8);
        list.addVertex(a);
        Vertex<Integer> b = new Vertex<>("ten", 10);
        list.addVertex(b);
        Vertex<Integer> c = new Vertex<>("two", 2);
        list.addVertex(c);
        Vertex<Integer> d = new Vertex<>("five", 5);
        list.addVertex(d);
        Vertex<Integer> e = new Vertex<>("six", 6);
        list.addVertex(e);
        Vertex<Integer> f = new Vertex<>("one", 1);
        list.addVertex(f);
        Vertex<Integer> g = new Vertex<>("nine", 9);
        list.addVertex(g);
        Vertex<Integer> h = new Vertex<>("thirty", 30);
        list.addVertex(h);

        list.addEdge(a,b);
        list.addEdge(b,c);
        list.addEdge(d,b);
        list.addEdge(a,e);
        list.addEdge(b,f);
        list.addEdge(e,f);
        list.addEdge(f,g);
        list.addEdge(c,g);
        list.addEdge(c,h);
        list.addEdge(f,h);


        List<Vertex<Integer>> gh = new ArrayList<>();
        gh.add(f);
        gh.add(c);
        assertEquals(gh, Algorithms.upstreamVertices(list,g,h));
        assertEquals(new ArrayList<>(), Algorithms.upstreamVertices(list,a,c));
    }


}
