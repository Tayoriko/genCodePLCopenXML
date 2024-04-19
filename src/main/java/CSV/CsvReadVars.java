package CSV;

import check.Check;
import check.CheckVar;
import system.ReadFile;
import system.GDB;
import uniqueItems.OneVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReadVars {

    public CsvReadVars(){};

    private String inputLog = "";
    private final List<OneVar> listVars = new ArrayList<>();

    protected String[] convertLineToArray(String value){
        //convert line from csv file to array string
        //string parsing
        String[] elements = value.split(GDB.splitCsv);
        //trim elements
        for (String element : elements) {
            element = element.trim();
        }
        return elements;
    }

    protected boolean checkData(String[] value){
        //check correct of values in string from csv file with data about variable
        //TRUE = data correct
        //FALSE = data incorrect
        if (value.length != 3)  {
            inputLog = inputLog + "Incorrect variable information format: " + Arrays.stream(value).toList() + "\n";
            System.out.println(inputLog);
            return false;
        }
        Check check = new CheckVar().check(value);
        return !check.isError();
    }

    public OneVar createVar(String value){
        //create object OneVar from line from CSV file
        String[] line = convertLineToArray(value);
        if (checkData(line))    {
            return new OneVar(line);
        }
        return new OneVar();
    }

    public List<OneVar> readFile(String filepath, String filename)   {
        List<String> source = new ReadFile().readFromFile(filepath, filename);
        List<OneVar> result = new ArrayList<>();
        for (String s : source) {
            result.add(createVar(s));
        }
        return result;
    }
}
