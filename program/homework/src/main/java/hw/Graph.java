package hw;

import java.io.IOException;
import java.util.*;

/**
 * This object represents a graph.
 */
public class Graph {
    private final Map<Vertex, Set<Vertex>> adjVertices;

    /**
     * Constructor without params.
     */
    Graph() {
        this.adjVertices = new HashMap<>();
    }

    /**
     * Constructor with param.
     * @param adjVertices adjacency list
     */
    Graph(Map<Vertex, Set<Vertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    /**
     * Create a new vertex and an empty HashSet. Save both of them into the adjVertices HashMap as a key-value pair.
     * @param label the vertex's label
     */
    public void addVertex(char label) {
        adjVertices.putIfAbsent(new Vertex(label), new HashSet<>());
    }

    /**
     * Create an edge from two vertices. Save both of them to each other's HashSet in the adjVertices HashMap.
     * @param label1 one vertex's label
     * @param label2 other vertex's label
     */
    public void addEdge(char label1, char label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    /**
     * Getter function to get every vertex in the graph.
     * @return a set of vertices
     */
    public Set<Vertex> getSetOfVertices() {
        return adjVertices.keySet();
    }

    /**
     * Getter function to get every edge in the graph.
     * @return a set of edges
     */
    public Set<Set<Vertex>> getSetOfEdges() {
        Set<Set<Vertex>> allEdges = new HashSet<>();
        for (Vertex fromVertex : adjVertices.keySet()) {
            for (Vertex toVertex : adjVertices.get(fromVertex)) {
                Set<Vertex> edges = new HashSet<>();
                edges.add(fromVertex);
                edges.add(toVertex);
                allEdges.add(edges);
            }
        }
        return allEdges;
    }

    /**
     * Build a graph from a given string. It's behavior depends on the current char. If the current is an alphabetic one and there is another char after that, then it will check the next char for further action. If the next char was an alphabetic as well, it will work that way, otherwise it will check if there is one more char after this. If there was another one, then it will work that way as well.
     * @param string this string will be used to build a graph
     * @throws IOException if there was a syntax error in the string
     */
    public void buildGraphFromString(String string) throws IOException {
        if (ReadFile.isValid(string)) {
            for (int i = 0; i < string.length(); ++i) {
                char current = string.charAt(i);
                if (Character.isAlphabetic(current)) {
                    addVertex(current);
                    if (string.length() - i > 1) {
                        char next = string.charAt(i + 1);
                        if (Character.isAlphabetic(next)) {
                            if (current != next) {
                                addVertex(next);
                                addEdge(current, next);
                            } else {
                                throw new IOException();
                            }
                        }
                        if (string.length() - i > 2) {
                            char nextnext = string.charAt(i + 2);
                            if (next == '-') {
                                if (Character.isAlphabetic(nextnext)) {
                                    addVertex(nextnext);
                                    addEdge(current, nextnext);
                                    ++i;
                                } else {
                                    throw new IOException();
                                }
                            }
                            if (next == '.') {
                                if (Character.isAlphabetic(nextnext)) {
                                    ++i;
                                } else {
                                    throw new IOException();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Rebuild the short notation from the graph's implemented data structure. It uses a similar coloring technic which was used in {@link #getSpanningForest()} method.
     * @return a string which has correct short notation syntax
     */
    public String graphToShortNotation() {
        String shortNotation = "";
        Set<Vertex> grayVertices = new HashSet<>();
        Set<Vertex> blackVertices = new HashSet<>();
        for (Vertex vertex : getSetOfVertices()) {
            if (!grayVertices.contains(vertex) && !blackVertices.contains(vertex)) {
                grayVertices.add(vertex);
            }
            while (!grayVertices.isEmpty()) {
                Vertex fromVertex = grayVertices.iterator().next();
                for (Vertex toVertex : adjVertices.get(fromVertex)) {
                    if (!blackVertices.contains(toVertex)) {
                        grayVertices.add(toVertex);
                        if (shortNotation == "") {
                            shortNotation += fromVertex.getLabel();
                            shortNotation += toVertex.getLabel();
                        } else {
                            char lastLabel = shortNotation.charAt(shortNotation.length() - 1);
                            Vertex lastVertex = new Vertex(lastLabel);
                            if (!adjVertices.get(fromVertex).contains(lastVertex) && fromVertex.getLabel() != lastLabel) {
                                shortNotation += ".";
                            }
                            if (fromVertex.getLabel() == lastLabel) {
                                shortNotation += toVertex.getLabel();
                            } else {
                                shortNotation += fromVertex.getLabel();
                                shortNotation += toVertex.getLabel();
                            }
                        }
                    }
                }
                grayVertices.remove(fromVertex);
                blackVertices.add(fromVertex);
            }
        }
        return shortNotation;
    }

    /**
     * Getter function to get the spanning forest for the graph. It uses a technic, which is coloring the non-visited (white), visited (gray) and finished (black) vertices. It's behavior depends on the vertex's color it is visiting.
     * @return spanning forest which is sets of vertices in a set
     */
    public Set<Set<Vertex>> getSpanningForest() {
        Set<Vertex> grayVertices = new HashSet<>();
        Set<Vertex> blackVertices = new HashSet<>();
        Set<Set<Vertex>> spanningForest = new HashSet<>();
        for (Vertex vertex : getSetOfVertices()) {
            if (!grayVertices.contains(vertex) && !blackVertices.contains(vertex)) {
                grayVertices.add(vertex);
            }
            while (!grayVertices.isEmpty()) {
                Vertex fromVertex = grayVertices.iterator().next();
                for (Vertex toVertex : adjVertices.get(fromVertex)) {
                    if (!grayVertices.contains(toVertex) && !blackVertices.contains(toVertex)) {
                        grayVertices.add(toVertex);
                        Set<Vertex> edge = new HashSet<>();
                        edge.add(fromVertex);
                        edge.add(toVertex);
                        spanningForest.add(edge);
                    }
                }
                grayVertices.remove(fromVertex);
                blackVertices.add(fromVertex);
            }
        }
        return spanningForest;
    }

    /**
     * Utility function to traversing a graph.
     * @param fromVertex the start vertex
     * @param path the path to track where we were earlier
     * @param spanningForest the graph which will be traversed
     */
    public void DFSUtil(Vertex fromVertex, Map<Vertex, Vertex> path, Set<Set<Vertex>> spanningForest) {
        for (Set<Vertex> edge : spanningForest) {
            if (edge.contains(fromVertex)) {
                Vertex toVertex = null;
                for (Vertex vertex : edge) {
                    if (!vertex.equals(fromVertex)) {
                        toVertex = vertex;
                    }
                }
                if (!path.containsKey(toVertex)) {
                    path.put(toVertex, fromVertex);
                    DFSUtil(toVertex, path, spanningForest);
                }
            }
        }
    }

    /**
     * Getter function to get a fundamental cycle in a graph. It uses the DFSUtil utility function for graph traversing.
     * @param vertex1 an edge's one vertex
     * @param vertex2 the edge's other vertex
     * @param spanningForest the spanning forest which will be traversed
     * @return a fundamental cycle which is an arraylist, and it contains the vertices of the cycle
     */
    public ArrayList<Vertex> getFundamentalCycle(Vertex vertex1, Vertex vertex2, Set<Set<Vertex>> spanningForest) {
        Map<Vertex, Vertex> path = new HashMap<>();
        path.put(vertex1, vertex1);
        DFSUtil(vertex1, path, spanningForest);
        ArrayList<Vertex> fundamentalCycle = new ArrayList<>();
        Vertex vertex = vertex2;
        while (vertex != vertex1) {
            fundamentalCycle.add(vertex);
            vertex = path.get(vertex);
        }
        fundamentalCycle.add(vertex1);
        return fundamentalCycle;
    }

    /**
     * Getter function to get every fundamental cycles in a graph.
     * @return a set of fundamental cycles
     */
    public Set<ArrayList<Vertex>> getFundamentalCycles() {
        Set<ArrayList<Vertex>> fundamentalCycles = new HashSet<>();
        Set<Set<Vertex>> spanningForest = getSpanningForest();
        for (Set<Vertex> edge : getSetOfEdges()) {
            if (!spanningForest.contains(edge)) {
                ArrayList<Vertex> vertices = new ArrayList<>();
                for (Vertex vertex : edge) {
                    vertices.add(vertex);
                }
                ArrayList<Vertex> fundamentalCycle = getFundamentalCycle(vertices.get(0), vertices.get(1),
                        spanningForest);
                fundamentalCycles.add(fundamentalCycle);
            }
        }
        return fundamentalCycles;
    }

    /**
     * Count the fundamental cycles in the graph.
     * @return the number of fundamental cycles
     */
    public int getNumberOfFundamentalCycles() {
        int numberOfFundamentalCycles = 0;
        Set<Set<Vertex>> spanningForest = getSpanningForest();
        for (Set<Vertex> edge : getSetOfEdges()) {
            if (!spanningForest.contains(edge)) {
                ++numberOfFundamentalCycles;
            }
        }
        return numberOfFundamentalCycles;
    }

    /**
     * Count the fragments in the graph. It uses a similar coloring technic, which was used in {@link #getSpanningForest()} method.
     * @return the number of fragments
     */
    public int getNumberOfFragments() {
        int numberOfFragments = 0;
        Set<Vertex> grayVertices = new HashSet<>();
        Set<Vertex> blackVertices = new HashSet<>();
        for (Vertex vertex : getSetOfVertices()) {
            if (!grayVertices.contains(vertex) && !blackVertices.contains(vertex)) {
                grayVertices.add(vertex);
                ++numberOfFragments;
            }
            while (!grayVertices.isEmpty()) {
                Vertex fromVertex = grayVertices.iterator().next();
                for (Vertex toVertex : adjVertices.get(fromVertex)) {
                    if (!grayVertices.contains(toVertex) && !blackVertices.contains(toVertex)) {
                        grayVertices.add(toVertex);
                    }
                }
                grayVertices.remove(fromVertex);
                blackVertices.add(fromVertex);
            }
        }
        return numberOfFragments;
    }

    /**
     * Print out the graph as a human-readable string.
     * @return a string which contains the vertices, edges and number of fragments for the graph
     */
    public String graphToString(){
        String graph = "";
        graph += "Vertices = " + getSetOfVertices();
        graph += "\nEdges = " + getSetOfEdges();
        graph += "\nNumber of fragments = " + getNumberOfFragments();
        return graph;
    }

    /**
     * Print out every information that we can tell about this graph.
     */
    public void printGraph() {
        System.out.println("Every information:\n");
        System.out.println("Number of vertices = " + getSetOfVertices().size());
        System.out.println("Vertices = " + getSetOfVertices());
        System.out.println("Number of edges = " + getSetOfEdges().size());
        System.out.println("Edges = " + getSetOfEdges());
        System.out.println("Number of fragments = " + getNumberOfFragments());
        System.out.println("Number of edges in the spanning forest = " + getSpanningForest().size());
        System.out.println("Edges in the spanning forest = " + getSpanningForest());
        System.out.println("Short notation from graph = " + graphToShortNotation());
        System.out.println("Number of fundamental cycles = " + getNumberOfFundamentalCycles());
        System.out.println("Fundamental cycles = " + getFundamentalCycles());
    }
}