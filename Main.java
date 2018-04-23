import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Genetic stockMarket = new Genetic("HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","2017-01-04");
        stockMarket.startTrading();
    }
}
