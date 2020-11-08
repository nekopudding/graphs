package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Dependencies: Graph.java Vertex.java
 *
 *  A data type that represents a Graph using Adjacency Matrices.
 *
 ******************************************************************************/

public class AdjacencyMatrixGraph<T> implements Graph<T> {

    public void addVertex(Vertex<T> v){

    }

    /**
     * Adds an edge from v1 to v2.
     * <p>
     * Precondition: v1 and v2 are vertices in the graph
     * </p>
     *
     * @param v1 Vertex v1 that will be upstream neighbour of v2. Must be a vertex in the graph.
     *
     * @param v2 Vertex v2 that will be downstream neighbour of v1. Must be a vertex in the graph.
     *
     * @return mutates List<Vertex<T>> in the graph hashmap mapped
     * to key v1 by adding v2 to the list.
     */
    public void addEdge(Vertex<T> v1, Vertex<T> v2){

    }

    /**
     * Check if there is an edge from v1 to v2.
     * <p>
     * Precondition: v1 and v2 are vertices in the graph
     * </p>
     * <p>
     * Postcondition: return true iff an edge from v1 connects to v2
     * </p>
     *
     * @param v1 is upstream vertex. Must be in the graph.
     * @param v2 is downstream vertex. Must be in the graph.
     * @return true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex<T> v1, Vertex<T> v2){
       return true;
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
        return null;
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
        return null;
    }
    // TODO: Implement this class
    // This class should implement the Graph interface
}
