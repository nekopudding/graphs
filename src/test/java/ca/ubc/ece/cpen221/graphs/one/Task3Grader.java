package ca.ubc.ece.cpen221.graphs.one;


/* import that graph classes */

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class Task3Grader {
    private static final int NO_PATH = -1;
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NO_DIAMETER = -3;
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1); // max 1 second per method
    private Graph testClass;
    private String testFile;
    private HashMap<String, Vertex> vMap = new HashMap<>();
    private HashMap<Vertex, Set<Vertex>> eMap = new HashMap<>();
    private Set<List<Vertex>> expBFS = new HashSet<>();
    private Set<List<Vertex>> expDFS = new HashSet<>();
    private List<Path> paths = new ArrayList<>();
    private Graph testGraph;
    private Random rng = new Random();
    private int diameter;

    public Task3Grader(Graph testClass, String filename)
        throws IllegalAccessException, InstantiationException {
        this.testClass = testClass;
        this.testFile = filename;
        testGraph = this.testClass.getClass().newInstance();
        setupGraph(testGraph, testFile);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        Graph[] graphs = new Graph[] {new AdjacencyListGraph(), new AdjacencyMatrixGraph()};
        String[] files = new String[] {
            "testcases/graph1.json",
            "testcases/graph2.json",
            "testcases/graph3.json",
            "testcases/graph4.json",
            "testcases/graph5.json",
            "testcases/graph6.json",
            "testcases/graph7.json"
        };
        Object[][] parameters = new Object[graphs.length * files.length][2];
        for (int i = 0; i < graphs.length; i++) {
            for (int j = 0; j < files.length; j++) {
                parameters[i * files.length + j] = new Object[] {graphs[i], files[j]};
            }
        }
        return Arrays.asList(parameters);
    }

    public void setupGraph(Graph g, String filename) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        vMap.clear();
        try (FileReader fr = new FileReader(filename)) {
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
                if (jsonObject.has("paths")) {
                    paths.clear();
                    JsonArray pathCollection = jsonObject.get("paths").getAsJsonArray();
                    for (int i = 0; i < pathCollection.size(); i++) {
                        JsonObject onePath = pathCollection.get(i).getAsJsonObject();
                        String v1label = onePath.get("v1").getAsString();
                        String v2label = onePath.get("v2").getAsString();
                        int distance = onePath.get("distance").getAsInt();
                        paths.add(new Path(vMap.get(v1label), vMap.get(v2label), distance));
                    }
                }
                if (jsonObject.has("diameter")) {
                    diameter = jsonObject.get("diameter").getAsInt();
                }


            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    @Test
    public void testDistance() {
        // test path length
        if (paths.size() == 0) {
            return;
        }

        for (Path path : paths) {
            try {
                int actDistance = Algorithms.shortestDistance(testGraph, path.v1, path.v2);
                if (path.distance == NO_PATH) {
                    assertTrue(actDistance == path.distance || actDistance == INFINITY);
                } else {
                    assertEquals("Path distance incorrect", path.distance, actDistance);
                }
            }
            catch (Exception e) {
                if (path.distance != NO_PATH) {
                    fail("Incorrect shortest path");
                } else {
                    if (e instanceof RuntimeException) {
                        fail("Runtime exception is not appropriate for shortest path");
                    }
                }
            }
        }
    }

    @Test
    public void testDiameter() {
        if (diameter == NO_DIAMETER) {
            diameter = INFINITY;
        }
        try {
            int computedDiameter = Algorithms.diameter(testGraph);
            if (diameter == INFINITY) {
                assertEquals(INFINITY, computedDiameter);
            } else {
                assertEquals("Incorrect diameter", diameter, Algorithms.diameter(testGraph));
            }
        }
        catch (Exception e) {
            if (diameter != INFINITY) {
                fail("No exception expected for diameter");
            } else {
                if (e instanceof RuntimeException) {
                    fail("Runtime exception not suitable for diameter");
                }
            }
        }
    }

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
}
