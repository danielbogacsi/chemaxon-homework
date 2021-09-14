package hw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {
    Vertex vertex = new Vertex('A');

    @Test
    void getLabel() {
        assertEquals('A', vertex.getLabel());
    }

    @Test
    void testToString() {
        assertEquals("A", vertex.toString());
    }
}