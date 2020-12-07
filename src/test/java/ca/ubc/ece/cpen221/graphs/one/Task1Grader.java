package ca.ubc.ece.cpen221.graphs.one;


/* import that graph classes */
import ca.ubc.ece.cpen221.graphs.core.*;

/* import the JUnit classes */
import static org.junit.Assert.*;

import com.google.gson.*;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* import standard Java collections */
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;
import java.util.Collection;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class Task1Grader {
    private Graph testClass;
    private String testFile;
    private HashMap<String, Vertex> vMap = new HashMap<>();
    private HashMap<Vertex, Set<Vertex>> eMap = new HashMap<>();
    private Graph testGraph;
    private Random rng = new Random();

    private class Path {
        Vertex v1;
        Vertex v2;
        int distance;

        Path(Vertex v1, Vertex v2, int distance) {
            this.v1 = v1;
            this.v2 = v2;
            this.distance = distance;
        }
    }

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1); // max 1 second per method

    public Task1Grader(Graph testClass, String filename) throws IllegalAccessException, InstantiationException {
        this.testClass = testClass;
        this.testFile = filename;
        testGraph = this.testClass.getClass().newInstance();
        setupGraph(testGraph, testFile);
    }

    public void setupGraph(Graph g, String filename) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        vMap.clear();
        try(FileReader fr = new FileReader(filename)) {
            JsonElement json = parser.parse(fr);
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                JsonArray varray = jsonObject.get("vertices").getAsJsonArray();
                // System.out.println(v);
                for (int i = 0; i < varray.size(); i++) {
                    String vid = varray.get(i).getAsString();
                    Vertex vi = new Vertex(vid, vid);
                    g.addVertex(vi);
                    vMap.put(vid, vi);
                    eMap.put(vi, new HashSet<>());
                }
                JsonArray earray = jsonObject.get("edges").getAsJsonArray();
                for (int i = 0; i < earray.size(); i++) {
                    JsonArray ei = earray.get(i).getAsJsonArray();
                    // this is an edge. only two elements in the array.
                    String vs1 = ei.get(0).getAsString();
                    String vs2 = ei.get(1).getAsString();
                    Vertex v1 = vMap.get(vs1);
                    Vertex v2 = vMap.get(vs2);
                    g.addEdge(v1, v2);
                    eMap.get(v1).add(v2);
                    // eMap.get(v2).add(v1);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

//    @Test
//    public void test0() throws InstantiationException, IllegalAccessException {
//        Graph oneVertexGraph = this.testClass.getClass().newInstance();
//        String label1 = "Label 1";
//        String label2 = "Label 2";
//        Vertex v = new Vertex(label1, label1);
//        oneVertexGraph.addVertex(v);
//        v.setLabel(label2);
//        List<Vertex> vList = oneVertexGraph.getVertices();
//        assertEquals("Incorrect number of vertices", 1, vList.size());
//        Vertex v2 = vList.get(0);
//        assertEquals("Vertex has been mutated", label1, v2.getLabel());
//    }

    @Test
    public void testAddVertices() throws InstantiationException, IllegalAccessException {
        List<Vertex> vList = testGraph.getVertices();
        assertEquals("Incorrect number of vertices", vMap.size(), vList.size());
        for (Vertex v : vMap.values()) {
            assertTrue("Missing vertex", vList.contains(v));
        }
    }

    @Test
    public void testNeighbours() throws InstantiationException, IllegalAccessException {
        List<Vertex> vList = testGraph.getVertices();
        // pick a vertex at random
        // test for its neighbours
        int x = rng.nextInt(vMap.size());
        Vertex v = vList.get(x);
        List<Vertex> nList = testGraph.getNeighbors(v);
        assertEquals("Incorrect number of neighbours", eMap.get(v).size(), nList.size());
        for (Vertex n: nList) {
            assertTrue(eMap.get(v).contains(n));
        }

        // are the neighbours in sorted order?
        List<Vertex> nList2 = new ArrayList<>(nList);
        nList2.sort((Vertex v1, Vertex v2)->v1.getLabel().compareTo(v2.getLabel()));
        assertEquals("Not in lexicographic order", nList2, nList);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        Graph[] graphs = new Graph[] { new AdjacencyListGraph(), new AdjacencyMatrixGraph() };
        String[] files  = new String[] {
            "testcases/graph1.json",
            "testcases/graph2.json",
            "testcases/graph3.json",
            "testcases/graph4.json"
        };
        Object[][] parameters = new Object[graphs.length * files.length][2];
        for (int i = 0; i < graphs.length; i++) {
            for (int j = 0; j < files.length; j++) {
                parameters[i * files.length + j] = new Object[] {graphs[i], files[j]};
            }
        }
        return Arrays.asList(parameters);
    }
}
