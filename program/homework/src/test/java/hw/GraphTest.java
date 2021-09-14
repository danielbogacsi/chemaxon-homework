package hw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    Graph createGraphFromChars(ArrayList<Character> vertices, ArrayList<ArrayList<Character>> edges){
        Map<Vertex, Set<Vertex>> adjVertices = new HashMap<>();
        for (int i = 0; i < vertices.size(); ++i) {
            Set<Vertex> adj = new HashSet<>();
            for (int j = 0; j < edges.get(i).size(); ++j) {
                char label = edges.get(i).get(j);
                adj.add(new Vertex(label));
            }
            char label = vertices.get(i);
            adjVertices.put(new Vertex(label), adj);
        }
        return new Graph(adjVertices);
    }

    @Test
    void addVerticesAndGetVertices() {
        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);

        Graph graph = new Graph();
        graph.addVertex(label1);
        graph.addVertex(label2);

        Set<Vertex> vertices = new HashSet<>(Arrays.asList(vertex1, vertex2));

        assertEquals(vertices, graph.getSetOfVertices());
    }

    @Test
    void addEdgesAndGetEdges() {
        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);
        char label3 = 'C';
        Vertex vertex3 = new Vertex(label3);

        Graph graph = new Graph();
        graph.addVertex(label1);
        graph.addVertex(label2);
        graph.addVertex(label3);
        graph.addEdge(label1, label2);
        graph.addEdge(label2, label3);

        Set<Vertex> edge1 = new HashSet<>(Arrays.asList(vertex1, vertex2));
        Set<Vertex> edge2 = new HashSet<>(Arrays.asList(vertex2, vertex3));
        Set<Set<Vertex>> edges = new HashSet<>(Arrays.asList(edge1, edge2));

        assertEquals(edges, graph.getSetOfEdges());
    }

    @Test
    void buildGraphFromString_NotCorrect() {
        String string = "A..B";

        Graph graph = new Graph();

        assertThrows(IOException.class, () -> graph.buildGraphFromString(string));
    }

    @Test
    void graphToShortNotation() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(List.of('A')),
                        new ArrayList<>(List.of('D')),
                        new ArrayList<>(List.of('C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        String shortNotation = testGraph.graphToShortNotation();

        assertEquals("AB.CD", shortNotation);
    }

    @Test
    void getSpanningForest_OneFragment() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(Arrays.asList('A', 'C', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);
        char label3 = 'C';
        Vertex vertex3 = new Vertex(label3);
        char label4 = 'D';
        Vertex vertex4 = new Vertex(label4);

        Set<Set<Vertex>> testSpanningForest =
                new HashSet<>(Arrays.asList(
                    new HashSet<>(Arrays.asList(vertex1, vertex2)),
                    new HashSet<>(Arrays.asList(vertex2, vertex3)),
                    new HashSet<>(Arrays.asList(vertex2, vertex4))
        ));

        assertEquals(testSpanningForest, testGraph.getSpanningForest());
    }

    @Test
    void getSpanningForest_TwoFragment() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList('B', 'C')),
                        new ArrayList<>(Arrays.asList('A', 'C')),
                        new ArrayList<>(Arrays.asList('A', 'B')),
                        new ArrayList<>(Arrays.asList('E', 'F')),
                        new ArrayList<>(Arrays.asList('D', 'F')),
                        new ArrayList<>(Arrays.asList('E', 'D'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);
        char label3 = 'C';
        Vertex vertex3 = new Vertex(label3);
        char label4 = 'D';
        Vertex vertex4 = new Vertex(label4);
        char label5 = 'E';
        Vertex vertex5 = new Vertex(label5);
        char label6 = 'F';
        Vertex vertex6 = new Vertex(label6);

        Set<Set<Vertex>> testSpanningForest =
                new HashSet<>(Arrays.asList(
                        new HashSet<>(Arrays.asList(vertex1, vertex2)),
                        new HashSet<>(Arrays.asList(vertex1, vertex3)),
                        new HashSet<>(Arrays.asList(vertex4, vertex5)),
                        new HashSet<>(Arrays.asList(vertex4, vertex6))
                ));

        assertEquals(testSpanningForest, testGraph.getSpanningForest());
    }

    @Test
    void getFundamentalCycle() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(Arrays.asList('A', 'C', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);
        char label3 = 'C';
        Vertex vertex3 = new Vertex(label3);
        char label4 = 'D';
        Vertex vertex4 = new Vertex(label4);

        Set<Set<Vertex>> spanningForest =
                new HashSet<>(Arrays.asList(
                        new HashSet<>(Arrays.asList(vertex1, vertex2)),
                        new HashSet<>(Arrays.asList(vertex2, vertex3)),
                        new HashSet<>(Arrays.asList(vertex2, vertex4))
                ));

        ArrayList<Vertex> fundamentalCycle = new ArrayList<>(Arrays.asList(vertex4, vertex2, vertex3));

        assertEquals(fundamentalCycle, testGraph.getFundamentalCycle(vertex3, vertex4, spanningForest));
    }

    @Test
    void getFundamentalCycles() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('A', 'C', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('A', 'B', 'C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        char label1 = 'A';
        Vertex vertex1 = new Vertex(label1);
        char label2 = 'B';
        Vertex vertex2 = new Vertex(label2);
        char label3 = 'C';
        Vertex vertex3 = new Vertex(label3);
        char label4 = 'D';
        Vertex vertex4 = new Vertex(label4);

        Set<ArrayList<Vertex>> fundamentalCycles = new HashSet<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(vertex4, vertex1, vertex2)),
                new ArrayList<>(Arrays.asList(vertex4, vertex1, vertex2, vertex3))
        ));

        assertEquals(fundamentalCycles, testGraph.getFundamentalCycles());
    }

    @Test
    void getNumberOfFundamentalCycles_Exist() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('A', 'C', 'D')),
                        new ArrayList<>(Arrays.asList('B', 'D')),
                        new ArrayList<>(Arrays.asList('A', 'B', 'C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        assertEquals(2, testGraph.getNumberOfFundamentalCycles());
    }

    @Test
    void getNumberOfFundamentalCycles_NotExist() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(Arrays.asList('A', 'C')),
                        new ArrayList<>(List.of('B'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        assertEquals(0, testGraph.getNumberOfFundamentalCycles());
    }

    @Test
    void getNumberOfFragments_One() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(List.of('A'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        assertEquals(1, testGraph.getNumberOfFragments());
    }

    @Test
    void getNumberOfFragments_MoreThanOne() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(List.of('A')),
                        new ArrayList<>(List.of('D')),
                        new ArrayList<>(List.of('C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        assertEquals(2, testGraph.getNumberOfFragments());
    }

    @Test
    void graphToString() {
        ArrayList<Character> vertices = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D'));
        ArrayList<ArrayList<Character>> edges =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(List.of('B')),
                        new ArrayList<>(List.of('A')),
                        new ArrayList<>(List.of('D')),
                        new ArrayList<>(List.of('C'))
                ));
        Graph testGraph = createGraphFromChars(vertices, edges);

        String string = "Vertices = [A, B, C, D]\nEdges = [[A, B], [C, D]]\nNumber of fragments = 2";

        assertEquals(string, testGraph.graphToString());
    }
}