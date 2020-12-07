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
public class Task2Grader {
    private Graph testClass;
    private String testFile;
    private HashMap<String, Vertex> vMap = new HashMap<>();
    private HashMap<Vertex, Set<Vertex>> eMap = new HashMap<>();
    private Set<List<Vertex>> expBFS = new HashSet<>();
    private Set<List<Vertex>> expDFS = new HashSet<>();
    private List<Path> paths = new ArrayList<>();
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

    public Task2Grader(Graph testClass, String filename) throws IllegalAccessException, InstantiationException {
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
                if (jsonObject.has("bfs")) {
                    expBFS.clear();
                    JsonArray bfsCollection = jsonObject.get("bfs").getAsJsonArray();
                    for (int i = 0; i < bfsCollection.size(); i++) {
                        JsonArray oneBFS = bfsCollection.get(i).getAsJsonArray();
                        List<Vertex> bfsSeq = new ArrayList<>();
                        for (int j = 0; j < oneBFS.size(); j++) {
                            String vLabel = oneBFS.get(j).getAsString();
                            bfsSeq.add(vMap.get(vLabel));
                        }
                        expBFS.add(bfsSeq);
                    }
                }
                if (jsonObject.has("dfs")) {
                    expDFS.clear();
                    JsonArray dfsCollection = jsonObject.get("dfs").getAsJsonArray();
                    for (int i = 0; i < dfsCollection.size(); i++) {
                        JsonArray oneDFS = dfsCollection.get(i).getAsJsonArray();
                        List<Vertex> dfsSeq = new ArrayList<>();
                        for (int j = 0; j < oneDFS.size(); j++) {
                            String vLabel = oneDFS.get(j).getAsString();
                            dfsSeq.add(vMap.get(vLabel));
                        }
                        expDFS.add(dfsSeq);
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    @Test
    public void testBFS() throws InstantiationException, IllegalAccessException {
        // test BFS
        Set<List<Vertex>> bfsOutput = Algorithms.breadthFirstSearch(testGraph);

        if (!expBFS.equals(bfsOutput)) {
            for (List<Vertex> l: expBFS) {
                l.remove(0);
                assertTrue("BFS incorrect", bfsOutput.contains(l));
            }
        }

//        for (List<Vertex> l: bfsOutput) {
//            Vertex v1 = l.get(0);
//            v1.setLabel("MISC");
//        }
//        assertEquals("Rep exposure", expBFS, Algorithms.breadthFirstSearch(testGraph));
    }

    @Test
    public void testDFS() throws InstantiationException, IllegalAccessException {
        // test DFS
        Set<List<Vertex>> dfsOutput = Algorithms.depthFirstSearch(testGraph);

        if (!expDFS.equals(dfsOutput)) {
            for (List<Vertex> l: expDFS) {
                l.remove(0);
                assertTrue("DFS incorrect", dfsOutput.contains(l));
            }
        }

//        for (List<Vertex> l: dfsOutput) {
//            Vertex v1 = l.get(0);
//            v1.setLabel("MISC");
//        }
//        assertEquals("Rep exposure", expDFS, Algorithms.depthFirstSearch(testGraph));
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
