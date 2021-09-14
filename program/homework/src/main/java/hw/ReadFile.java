package hw;

import java.io.*;
import java.util.Scanner;

/**
 * To read and validate input file.
 */
public class ReadFile {

    /**
     * This method is responsible for reading the input file.
     * @param filename the file's name which will be read
     * @return a string which is containing the input file's content
     * @throws IOException if the file can not be found or has wrong format
     */
    public static String readFile(String filename) throws IOException{
        File myObj = new File(filename);
        Scanner scanner = new Scanner(myObj);
        String data = scanner.nextLine();
        if (scanner.hasNextLine()) {
            throw new IOException();
        }
        scanner.close();
        return data;
    }

    /**
     * Utility function to validate a string with regex. Actually used to check the input file's content if it is correct.
     * @param string this string will be validated
     * @return true if the string was valid, otherwise return false
     */
    public static boolean isValid(String string) {
        String regex = "[A-Z-.]*";
        return string.matches(regex);
    }
}