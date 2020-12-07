package ca.ubc.ece.cpen221.graphs.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class Task4Grader {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    public Set<String> getSetOfUsers(String filePath) throws IOException {
        Set<String> users = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(user -> users.add(user));
            return users;
        }
    }

    @Test
    public void testNoCIs() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "1", "2"});
        String expectedOutput = "";
        assertEquals(expectedOutput, outContent.toString());
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_1_2() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "3", "4"});
        Set<String> expectedOutput = new HashSet<>(Arrays.asList("1", "2"));
        Set<String> actualOutput =
            new HashSet<>(Arrays.asList(outContent.toString().split("\\r?\\n")));
        assertEquals(expectedOutput, actualOutput);
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_1_2_reverse() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "4", "3"});
        Set<String> expectedOutput = new HashSet<>(Arrays.asList("1", "2"));
        Set<String> actualOutput =
            new HashSet<>(Arrays.asList(outContent.toString().split("\\r?\\n")));
        assertEquals(expectedOutput, actualOutput);
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_1_2_4_5() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "4", "5"});
        Set<String> expectedOutput = new HashSet<>(Arrays.asList("1", "2"));
        Set<String> actualOutput =
            new HashSet<>(Arrays.asList(outContent.toString().split("\\r?\\n")));
        assertEquals(expectedOutput, actualOutput);
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void testNoCIs_2() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "3", "7"});
        assertEquals("", outContent.toString());
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_1_8_9() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "commonInfluencers", "8", "9"});
        Set<String> expectedOutput = new HashSet<>(Arrays.asList("1"));
        Set<String> actualOutput =
            new HashSet<>(Arrays.asList(outContent.toString().split("\\r?\\n")));
        assertEquals(expectedOutput, actualOutput);
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_NR_0() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "1", "1"});
        assertEquals("0", outContent.toString().trim());
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_NR_1() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "1", "8"});
        assertEquals("0", outContent.toString().trim());
    }

    @Test
    public void test_NR_4() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "13", "9"});
        assertEquals("4", outContent.toString().trim());
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_NR_1_13_4() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "13", "4"});
        assertEquals("1", outContent.toString().trim());
    }

    @Test
    public void test_NR_INF() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "3", "1"});
        int possibleOutput1 = -1;
        int possibleOutput2 = Integer.MAX_VALUE;
        assertTrue(String.valueOf(possibleOutput1).equals(outContent.toString().trim()) || String.valueOf(possibleOutput2).equals(outContent.toString().trim()));
        assertEquals("", errContent.toString().trim());
    }

    @Test
    public void test_NR_1_17_22() throws IOException {
        TwitterAnalysis.main(new String[] {"testcases/miniTwitter.txt", "numRetweets", "17", "22"});
        assertEquals("1", outContent.toString().trim());
        assertEquals("", errContent.toString().trim());
    }
}
