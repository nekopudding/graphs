package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TwitterAnalysis {

    private static List<String> followedA = new ArrayList<>();
    private static List<String> followerB = new ArrayList<>();
    private static Graph<Integer> g = new AdjacencyMatrixGraph<>();
    private static HashMap<String, Vertex<Integer>> labels = new HashMap<>();

    /**
     * Opens the specified text file and extracts the information and constructs a graph representation
     *
     * @param fileName name of file to be opened
     * @throws IOException if file is not found
     */
    public TwitterAnalysis(String fileName) throws IOException {

        try {
            File twitter = new File(fileName);
            Scanner reader = new Scanner(twitter);
            while (reader.hasNextLine()) {
                String r = reader.nextLine();
                followedA.add(r.substring(0, r.lastIndexOf(" ") - 3));
                followerB.add(r.substring(r.lastIndexOf(" ") + 1));
            }
        } catch (FileNotFoundException e) {
            throw new IOException();
        }

        for (int i = 0; i < followedA.size(); i++) {
            if (!labels.containsKey(followedA.get(i))) {
                labels.put(followedA.get(i), new Vertex<>(followedA.get(i), 0));
                g.addVertex(labels.get(followedA.get(i)));
            }
            if (!labels.containsKey(followerB.get(i))) {
                labels.put(followerB.get(i), new Vertex<>(followerB.get(i), 0));
                g.addVertex(labels.get(followerB.get(i)));
            }
            g.addEdge(labels.get(followerB.get(i)), labels.get(followedA.get(i)));
        }

    }

    /**
     * Finds common users that both userA and userB follows
     *
     * @param userA the user
     * @param userB the other user
     * @return the list of common users that both userA and userB follows
     */
    public static Set<String> commonInfluencers(String userA, String userB) {
        Set<String> commonData = new HashSet<>();
        List<Vertex<Integer>> common = Algorithms.downstreamVertices(g, labels.get(userA), labels.get(userB));

        for (Vertex v : common) {
            commonData.add(v.getLabel());
        }

        return commonData;
    }

    /**
     * Finds the smallest amount of retweets for a post from userA to reach userB's feed
     *
     * @param userA the user posting
     * @param userB the user the post is reaching
     * @return the smallest number of retweets required for userA's post to reach userB
     */
    public static int numRetweets(String userA, String userB) {
        boolean flag = false;
        Set<List<Vertex<Integer>>> graph = Algorithms.depthFirstSearch(g);
        for (List<Vertex<Integer>> l : graph) {
            for (int i = 0; i < l.size(); i++) {
                if (userB.equals(l.get(0).getLabel()) && userA.equals(l.get(i).getLabel())) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            System.err.print("userA's post will never reach userB's feed");
            return -1;
        }

        int retweets = Algorithms.shortestDistance(g, labels.get(userB), labels.get(userA)) - 1;
        return retweets;
    }

    public static void main(String[] args) throws IOException {

        new TwitterAnalysis(args[0]);

        if (!labels.containsKey(args[2])) {
            System.err.print("userA does not exist in the data");
            return;
        } else if (!labels.containsKey(args[3])) {
            System.err.print("userB does not exist in the data");
            return;
        }

        if (args[1].equals("commonInfluencers")) {

            Set<String> common = commonInfluencers(args[2], args[3]);

            for (String c : common) {
                System.out.println(c);
            }

        } else if (args[1].equals("numRetweets")) {
            int retweets = numRetweets(args[2], args[3]);
            System.out.print(retweets);

        } else {
            System.err.print("The method was not specified correctly.");
        }

        /*
            main() should take four arguments:
            - The first argument should be a filename for a file holding
              a Twitter dataset.
            - The second should be one of "commonInfluencers" or "numRetweets".
            - The next two arguments should be identifiers for userA and userB.
            Then main() should invoke the appropriate method and write the result to
            standard output (often, the terminal). For "numRetweets", the output to
            standard output should be just the int. For "commonInfluencers", each
            of the influencers should be written to standard output, one per line,
            with no other text.
         */
    }

}
