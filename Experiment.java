import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.*;


public class Experiment
{
    public static void main(String[] args) throws FileNotFoundException
    {

        File myDirectory = new File("ExperimentData/");
        String[] fileNames = myDirectory.list();



        Individual rule1 = new Individual("e114|s041|m193"); // 2 Year rule1
        Individual rule2 = new Individual("m183|s093|e171"); // 2 Year rule2
        Individual rule3 = new Individual("s193|e173|m128"); // 2 Year rule3

        ArrayList<Individual> population = new ArrayList<Individual>();
        population.add(rule1);
        population.add(rule2);
        population.add(rule3);

        for(Individual i : population)
        {
            for(String file : fileNames)
            {
                Company company = new Company("ExperimentData/" + file);
                int sDate = 999;
                int eDate = 999 + 730;


                double fit = 0.0;
                for(int day = sDate; day < eDate; day++)
                {
                    Boolean b = i.checkBuy(company.getClosings(), company.getDates(), day);
                    company.trade(b, day);
                }
                company.sell(eDate);

                System.out.println(i.rule + "," + file + "," + company.money + "," + company.gainAccount);
                company.resetCost();

            }
        }
    }
}
