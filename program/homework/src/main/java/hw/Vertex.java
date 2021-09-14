package hw;

/**
 * This object represents a vertex.
 */
public class Vertex {
    private final char label;

    /**
     * Constructor with param.
     * @param label vertex's label
     */
    Vertex(char label) {
        this.label = label;
    }

    /**
     * Getter function to get the vertex's label.
     * @return vertex's label
     */
    public char getLabel() {
        return label;
    }

    /**
     * A hashCode() method.
     * @return hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + label;
        return result;
    }

    /**
     * An equals() method to compare two objects' equality.
     * @param obj an object to compare to
     * @return true if the objects are equal, otherwise return false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        return label == other.label;
    }

    /**
     * A toString() method which was override.
     * @return vertex's label
     */
    @Override
    public String toString() {
        return label + "";
    }
}