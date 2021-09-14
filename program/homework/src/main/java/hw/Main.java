package hw;

import java.io.IOException;

/**
 * The program's main class.
 */
public class Main {
    /**
     * The main method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try{
            Graph graph = new Graph();
            String data = ReadFile.readFile(args[0]);
            if(ReadFile.isValid(data)){
                graph.buildGraphFromString(data);
                System.out.println(graph.graphToString());
                System.out.println("Number of fundamental cycles = " + graph.getNumberOfFundamentalCycles());
                System.out.println("Number of edges in the spanning forest = " + graph.getSpanningForest().size());
                //graph.printGraph();
            }
        } catch(IOException e) {
            System.out.println("The input file can not be found or has wrong format!");
        }
    }
}
