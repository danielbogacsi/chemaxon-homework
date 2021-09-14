package hw;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

    @Test
    void readFile_Correct() throws IOException {
        String content = "ABC";
        String filename = "readFile_Correct.txt";
        assertEquals(content, hw.ReadFile.readFile(filename));
    }

    @Test
    void readFile_NotFound() {
        assertThrows(FileNotFoundException.class, () -> ReadFile.readFile("notFound"));
    }

    @Test
    void readFile_MoreThanOneLine() {
        assertThrows(IOException.class, () -> ReadFile.readFile("readFile_MoreThanOneLine.txt"));
    }

    @Test
    void isValid_Valid() {
        String string = "A-B.C";
        assertTrue(ReadFile.isValid(string));
    }

    @Test
    void isValid_NotValid() {
        String string = "123abc";
        assertFalse(ReadFile.isValid(string));
    }
}