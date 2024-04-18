package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public List<String> readFromFile(String filepath, String filename) {
        List<String> csvValues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filepath + filename + RegexBase.fileFormatDefault))) {
            String line;
            while ((line = reader.readLine()) != null)
            {
                csvValues.add(line);
            }
            System.out.println("Data load from: " + filename + RegexBase.fileFormatDefault);
        } catch (IOException e) {
            System.out.println("Data not found.");
        }
        return csvValues;
    }
}
