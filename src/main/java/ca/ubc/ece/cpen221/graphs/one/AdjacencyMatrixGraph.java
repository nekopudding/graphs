package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/******************************************************************************
 *  Dependencies: Graph.java Vertex.java
 *
 *  A data type that represents a Graph using Adjacency Matrices.
 *
 *  Abstraction Function: Represents a graph as a 2D Matrix (Arraylist) of 0 and 1
 *      The entry (i,j) row i and column j is 1 iff there's an upstream edge from
 *      vertex i to vertex j.
 *
 *      Each vertex is mapped to an index value in the matrix.
 *
 *  Representation Invariant: the value of vertices must all be different, as such each
 *      vertex mapping to an index is a 1:1 ratio
 *
 ******************************************************************************/

public class AdjacencyMatrixGraph<T> implements Graph<T> {
    // TODO: Implement this class
    // This class should implement the Graph interface
    private List<List<Integer>> graph;
    private int numVertices;
    HashMap<Vertex<T>, Integer> vertexMap;

    public AdjacencyMatrixGraph() {
        graph = new ArrayList<>();
        numVertices = 0;
        vertexMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph
     * Precondition: v is not already a vertex in the graph
     * @param v the vertex to add to the graph
     *
     * Adds a new row and column to the graph in the process.
     */
    public void addVertex(Vertex<T> v){
        List<Integer> row = new ArrayList<>();
        numVertices++;
        for (int i = 0; i < numVertices; i++) {
            row.add(0); //the vertex initially does not have any edges
        }
        for (List<Integer> list : graph) {
            list.add(0); //increase the size of previous rows by 1
        }
        graph.add(row);
        vertexMap.put(v,numVertices-1); //assign an index to that vertex
    }

    /**
     * Adds an edge from v1 to v2, updating the value in the matrix
     *
     * Precondition: v1 and v2 are vertices in the graph
     *
     * @param v1 the upstream neighbor of v2
     * @param v2 the the downstream neighbor of v1
     */
    public void addEdge(Vertex<T> v1, Vertex<T> v2){
        int i = vertexMap.get(v1);
        int j = vertexMap.get(v2);

        List<Integer> row = graph.get(i);
        row.set(j, 1);
        graph.set(i,row);
    }

    /**
     * Check if there is an edge from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph
     *
     * Postcondition: return true iff an edge from v1 connects to v2, false otherwise
     *
     *
     * @param v1 is upstream vertex. Must be in the graph.
     * @param v2 is downstream vertex. Must be in the graph.
     * @return true iff an edge from v1 connects to v2, false otherwise
     */
    public boolean edgeExists(Vertex<T> v1, Vertex<T> v2){
        int i = vertexMap.get(v1);
        int j = vertexMap.get(v2);

        if (graph.get(i).get(j) == 1)
            return true;
        return false;
    }

    /**
     * Get an array containing all vertices adjacent to v.
     * <p>
     * Precondition: v is a vertex in the graph
     * </p>
     * <p>
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     * </p>
     */
    public List<Vertex<T>> getNeighbors(Vertex<T> v){
        int i = vertexMap.get(v);
        List<Integer> row = graph.get(i);
        List<Vertex<T>> neighbors = new ArrayList<>();

        for (Vertex<T> v1 : vertexMap.keySet()){
            if ( row.get(vertexMap.get(v1)) == 1)
                neighbors.add(v1);
        }

        return neighbors;
    }

    /**
     * Get all vertices in the graph.
     * <p>
     * Postcondition: returns a list containing all vertices in the graph,
     * sorted by label in non-descending order.
     * This method should return a list of size 0 iff the graph has no vertices.
     * </p>
     */
    public List<Vertex<T>> getVertices(){
        List<Vertex<T>> vertices = new ArrayList<>();
        for (Vertex<T> v : vertexMap.keySet()){
            vertices.add(v);
        }
        return vertices;
    }
}

/*
upstream vertices and common downstream vertices for task 3
Your tasks: distance and graph diameter

Shortest distance: compute all possible paths to v2 from v1 then find the shortest one
this requires there to be upstream vertices from v1
 */