package ca.ubc.ece.cpen221.graphs;

import ca.ubc.ece.cpen221.graphs.one.TwitterAnalysis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class Task4Test {

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

    @Test(expected = IOException.class)
    public void test_noFile() throws IOException {
        String[] args = {"none", "commonInfluencers", "a", "b"};
        TwitterAnalysis.main(args);

    }

    @Test
    public void test_retweet() throws IOException {
        String[] args = {"datasets/testing.txt", "numRetweets", "f", "a"};
        TwitterAnalysis.main(args);
        assertEquals("1", outContent.toString());

    }

    @Test
    public void test_common() throws IOException {
        String[] args = {"datasets/testing.txt", "commonInfluencers", "a", "b"};
        TwitterAnalysis.main(args);
        assertEquals("c\r\ne\r\n", outContent.toString());

    }

    @Test
    public void test_noMethod() throws IOException {
        String[] args = {"datasets/testing.txt", "", "a", "b"};
        TwitterAnalysis.main(args);
        assertEquals("The method was not specified correctly.", errContent.toString());

    }

    @Test
    public void test_invalidUserA() throws IOException {
        String[] args = {"datasets/testing.txt", "commonInfluencers", "g", "b"};
        TwitterAnalysis.main(args);
        assertEquals("userA does not exist in the data", errContent.toString());

    }

    @Test
    public void test_invalidUserB() throws IOException {
        String[] args = {"datasets/testing.txt", "commonInfluencers", "a", "g"};
        TwitterAnalysis.main(args);
        assertEquals("userB does not exist in the data", errContent.toString());

    }

}
