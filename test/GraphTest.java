import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

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
    public void parseTest() throws IOException {
        int totalNodes = DotGraph.getNodes();
        assertEquals(5, totalNodes);
        int totalEdges = DotGraph.getEdges();
        assertEquals(7, totalEdges);

    }

    @Test
    public void outputTest() throws IOException {
        String filepath = "outputGraph.dot";
        DotGraph.outputGraph(filepath);
        File outputFile = new File(filepath);
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

    @Test
    public void addNodeTest(){
        //Add single node
        String label = "w";
        DotGraph.addNode(label);
        assertTrue("Node 'w' was not added.", DotGraph.containsNode("w"));

        //Add multiple nodes at the same time
        String[] labels = {"x", "y", "z"};
        DotGraph.addNodes(labels);
        assertTrue("Node 'x' was not added.", DotGraph.containsNode("x"));
        assertTrue("Node 'y' was not added.", DotGraph.containsNode("y"));
        assertTrue("Node 'z' was not added.", DotGraph.containsNode("z"));

        //Add existing node
        label = "x";
        boolean labelExists = DotGraph.addNode(label);
        assertTrue("Node 'x' was added when it is a duplicate", labelExists = true);
    }

    @Test
    public void addEdgeTest(){
        //Add edge with existing nodes
        String src = "b";
        String dst = "c";
        int edgeAdded = DotGraph.addEdge(src, dst);
        assertTrue("Edge added was not added.", edgeAdded == 0);

        //Add edge with new nodes
        src = "f";
        dst = "g";
        edgeAdded = DotGraph.addEdge(src, dst);
        assertTrue("Edge added was not added.", edgeAdded == 0);
    }
}

