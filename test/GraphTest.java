import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {
    String file;
    String filepath;
    DefaultDirectedGraph<String, DefaultEdge> directedGraph;

    @Before
    public void setup() throws IOException {
        file = "graphfile.dot";
        filepath = getClass().getResource(file).getPath();
        DotGraph.parseGraph(filepath);
    }

    @Test
    public void parseTest(){
        int totalNodes = DotGraph.getNodes();
        assertEquals(5, totalNodes);
        int totalEdges = DotGraph.getEdges();
        assertEquals(7, totalEdges);

    }

    @Test
    public void outputTest() throws IOException {
        DotGraph.outputGraph();
        File outputFile = new File("outputGraph.dot");
        assertTrue("The output file was not created.", outputFile.exists()); //checks that the output file was created
        StringBuilder fileContent = new StringBuilder(); //read output file contents
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        }
        String expectedContent = "digraph G {    a;    b;    c;    d;    e;    a -> b;    a -> c;    b -> d;    c -> d;    c -> e;    e -> a;    a -> d;}";
        assertEquals("The output DOT file content is incorrect.", expectedContent.trim(), fileContent.toString().trim());
        outputFile.delete();
    }

    @Test
    public void graphToStringTest() throws IOException {
        String expectedContent = "a; b; c; d; e; a -> b; a -> c; b -> d; c -> d; c -> e; e -> a; a -> d;";
        String recievedOutput = DotGraph.graphtoString();
        assertEquals("The output returned from graphtoString() does not match the expected output", expectedContent.trim(), recievedOutput.trim());
    }
}

