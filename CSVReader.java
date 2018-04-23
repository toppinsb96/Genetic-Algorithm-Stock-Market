import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.*;

public class CSVReader
{
    Scanner scn;
    List<String> items;
    ArrayList<List<String>> data;

    public CSVReader(String path) throws FileNotFoundException
    {
        scn = new Scanner(new File(path));

        data  = new ArrayList<List<String>>();

        while(scn.hasNext())
        {
            String line = scn.nextLine();
            items = Arrays.asList(line.split("\\s*,\\s*"));
            data.add(items);
        }

    }

    public ArrayList<String> getClosings()
    {
        ArrayList<String> closings = new ArrayList<String>();

        for(List<String> c : data)
            closings.add(c.get(4));

        return closings;
    }
    public ArrayList<String> getDates()
    {
        ArrayList<String> dates = new ArrayList<String>();

        for(List<String> c : data)
            dates.add(c.get(0));

        return dates;
    }
    public void printData()
    {
        for(List<String> c : data)
            System.out.println(c.get(4));

    }

    public void closeScanner()
    {
        scn.close();
    }
}
