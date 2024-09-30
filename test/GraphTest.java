import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    String filename;
    String filepath;
    DefaultDirectedGraph<String, DefaultEdge> directedGraph;

    @Before
    public void setup(){
        filename = "graphfile.dot";
        filepath = getClass().getResource(filename).getPath();
    }

    @Test
    public void parseTest(){
        try {
            DotGraph.parseGraph(filepath);
            int totalNodes = DotGraph.getNodes();
            assertEquals(5, totalNodes);
            int totalEdges = DotGraph.getEdges();
            assertEquals(7, totalEdges);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void outputTest() throws IOException {
        DotGraph.graphtoString();
        DotGraph.outputGraph();
    }
}

