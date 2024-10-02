import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.*;
import java.util.Vector;

public class DotGraph {
    static DefaultDirectedGraph<String, DefaultEdge> graph;
    static Vector<String> nodes;

    public static DefaultDirectedGraph<String, DefaultEdge> parseGraph(String filename) throws IOException {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        nodes = new Vector<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            // Check for node labels by identifying "node;" format
            if (line.contains("->")) {
                // Parse edges in the format "a -> b;"
                String[] parts = line.replace(";", "").split("->");
                if (parts.length == 2) {
                    String src = parts[0].trim();
                    String dst = parts[1].trim();
                    //System.out.println(src + " -> " + dst);
                    if(!nodes.contains(src)){
                        nodes.add(src);
                        graph.addVertex(src);
                    }
                    if(!nodes.contains(dst)){
                        nodes.add(dst);
                        graph.addVertex(dst);
                    }
                    graph.addEdge(src, dst);
                }
            }
            else if (line.endsWith(";")) {
                // Parse standalone nodes
                String node = line.replace(";", "").trim();
                if(!nodes.contains(node)) {
                    nodes.add(node);
                    graph.addVertex(node);
                    //System.out.println(node);
                }
            }
        }
        reader.close();
        return graph;
    }

    public static String graphtoString(){
        int nodeCount = 0;
        int edgeCount = 0;
        StringBuilder output = new StringBuilder();

        System.out.println("Node List: ");
        for (String node : nodes) {
            System.out.println(node);
            output.append(node).append("; ");
            nodeCount++;
        }
        System.out.println("Total node count: " + nodeCount);
        System.out.println("Edge List: ");
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String dest = graph.getEdgeTarget(edge);
            System.out.println(source + " -> " + dest);
            output.append(source).append(" -> ").append(dest).append("; ");
            edgeCount++;
        }
        System.out.println("Total edge count: " + edgeCount);
        return output.toString();
    }

    public static void outputGraph(String filepath) throws IOException {
        String source;
        String dest;
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write("digraph G {\n");
            for (String node : nodes) {
                fileWriter.write("    " + node + ";\n");
            }
            for (DefaultEdge edge : graph.edgeSet()) {
                source = graph.getEdgeSource(edge);
                dest = graph.getEdgeTarget(edge);
                fileWriter.write("    " + source + " -> " + dest + ";\n");
            }
            fileWriter.write("}\n");
        }
    }

    public static boolean addNode(String label){
        if (!graph.containsVertex(label)) {
            graph.addVertex(label);
            nodes.add(label);
        } else {
            return false;
        }
        return true;
    }

    public static boolean addNodes(String[] labels) {
        for(String label : labels){
            boolean x = addNode(label);
            if(x == false){
                return false;
            }
        }
        return true;
    }

    public static int addEdge(String src, String dst){
        if(graph.containsEdge(src, dst)){
            return -1;
        }
        if(!containsNode(src)){
            addNode(src);
        }
        if(!containsNode(dst)){
            addNode(dst);
        }
        graph.addEdge(src, dst);
        return 0;
    }

    public static int getNodes(){
        int count = 0;
        for (String node : nodes) {
            count++;
        }
        return count;
    }

    public static int getEdges(){
        int count = 0;
        for (DefaultEdge edge : graph.edgeSet()) {
            count++;
        }
        return count;
    }

    public static boolean containsNode(String label) {
        for (String node : nodes) {
            if(node == label){
                return true;
            }
        }
        return false;
    }
}


