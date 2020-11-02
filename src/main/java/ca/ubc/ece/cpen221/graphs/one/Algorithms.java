package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Algorithms {

    /**
     * *********************** Algorithms ****************************
     *
     * Please see the README for this machine problem for a more detailed
     * specification of the behavior of each method that one should
     * implement.
     */

    /**
     * This is provided as an example to indicate that this method and
     * other methods should be implemented here.
     * <p>
     * You should write the specs for this and all other methods.
     *
     * @param graph
     * @param a
     * @param b
     * @return
     */
    public static <T> int shortestDistance(Graph<T> graph, Vertex<T> a, Vertex<T> b) {
        // TODO: Implement this method and others
        // Note that this method can be invoked as follows:
        //      Algorithms.<String>shortestDistance(g, a, b)
        // when the graph contains vertices of type Vertex<String>.
        // The compiler can also perform type inference so that we can simply use:
        //      Algorithms.shortestDistance(g, a, b)
        return 0;
    }

    /**
     * Perform a complete depth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a DFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param
     * @return
     */
    public static <T> Set<List<Vertex<T>>> depthFirstSearch(Graph<T> graph) {
        Set<List<Vertex<T>>> dfsPath = new HashSet<List<Vertex<T>>>();
        List<Vertex<T>> allVertices = new ArrayList<>();
        allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices) {
            List<Vertex<T>> dfsSubPath = new ArrayList<>();
            dfsRecursive(v, dfsSubPath);
            dfsPath.add(dfsSubPath);
        }
        return dfsPath;

    }

    public static <T> void dfsRecursive(Vertex<T> vertex, List<Vertex<T>> dfsSubPath) {
        dfsSubPath.add(vertex);
        List<Vertex<T>> neighbor = new ArrayList<>();
        neighbor.sort(Comparator.comparing(Vertex::getLabel));
        if (!neighbor.isEmpty()) {
            for (Vertex<T> n : neighbor) {
                if (!dfsSubPath.contains(n)) {
                    dfsRecursive(n, dfsSubPath);
                }
            }
        }
    }

    /**
     * Perform a complete breadth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a BFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param
     * @return
     */
    public static <T> Set<List<Vertex<T>>> breadthFirstSearch(Graph<T> graph) {
        // TODO: Implement this method
        return null; // this should be changed
    }

    /**
     * You should write the spec for this method
     */
    public static <T> int diameter(Graph<T> graph) {
        // TODO: Implement this method
        return -1; // this should be changed
    }

    /**
     * Returns a list containing all of the common upstream vertices of a and b.
     *
     * @param graph directed graph containing vertex a and vertex b.
     * @param a : Vertex a. graph must contain a.
     * @param b : Vertex b. graph must contain b.
     *
     * @return List<Vertex<T>> a list of vertices such that a and b are both neighbours for all
     * vertices in the list. Returns an empty list if there are no such vertices.
     *
     */
    public static <T> List<Vertex<T>> upstreamVertices(Graph<T> graph, Vertex<T> a, Vertex<T> b){
        List<Vertex<T>> commonVertices = new ArrayList<>();
        List<Vertex<T>> allVertices = graph.getVertices();
        for (Vertex<T> v : allVertices){
            List<Vertex<T>> neighbours = graph.getNeighbors(v);
            if (neighbours.contains(a) && neighbours.contains(b)){
                commonVertices.add(v);
            }
        }
        return commonVertices;
    }

    /**
     *Returns a list containing all of the common downstream vertices of a and b.
     *
     *@param graph directed graph containing vertex a and vertex b.
     *@param a : Vertex a. graph must contain a.
     *@param b : Vertex b. graph must contain b.
     *
     *@return List<Vertex<T>> a list of vertices v such that v is a neighbour of both a and b.
     * returns an empty list if no such vertices exist.
     */
    public static <T> List<Vertex<T>> downstreamVertices(Graph<T> graph, Vertex<T> a, Vertex<T> b){
        List<Vertex<T>> commonVertices = new ArrayList<>();
        List<Vertex<T>> aNeighbours = graph.getNeighbors(a);
        List<Vertex<T>> bNeighbours = graph.getNeighbors(b);

        for (Vertex<T> v : aNeighbours){
            if (bNeighbours.contains(v)){
                commonVertices.add(v);
            }
        }
        return commonVertices;
    }

}
