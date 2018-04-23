import java.io.*;
import java.util.*;
import java.lang.*;

public class Genetic
{
    String co1;
    String co2;
    String co3;
    String co4;
    String co5;
    Company company1;
    Company company2;
    Company company3;
    Company company4;
    Company company5;

    String currentDate;

    ArrayList<Individual> population;

    public Genetic(String file1, String file2, String file3, String file4, String file5, String startDate)
    {
        this.currentDate = startDate;
        population = new ArrayList<Individual>();
        // Paths for the five files
        co1 = file1;
        co2 = file2;
        co3 = file3;
        co4 = file4;
        co5 = file5;
        // Create Random 20 Rules Individuals
        for(int i = 0; i < 20; i++)
        {
            Individual rule = new Individual();
            rule.createRandom();
            population.add(rule);
        }
    }
    public void evalFitness() throws FileNotFoundException
    {
        company1 = new Company(co1);
        company2 = new Company(co2);
        company3 = new Company(co3);
        company4 = new Company(co4);
        company5 = new Company(co5);


        // Hard coded Dates for now.
        int sDate = 999;
        int eDate = 999 + 730;

        for(Individual i : population)
        {
            double fit = 0.0;

            for(int day = sDate; day < eDate; day++)
            {
                company1.trade(i.checkBuy(company1.getClosings(), company1.getDates(), day), day);
                company2.trade(i.checkBuy(company2.getClosings(), company2.getDates(), day), day);
                company3.trade(i.checkBuy(company3.getClosings(), company3.getDates(), day), day);
                company4.trade(i.checkBuy(company4.getClosings(), company4.getDates(), day), day);
                company5.trade(i.checkBuy(company5.getClosings(), company5.getDates(), day), day);

            }
            company1.sell(eDate);
            //System.out.println(company1.money + "   " + company1.gainAccount);
            fit += company1.money;
            fit += company1.gainAccount;
            fit += company2.money;
            fit += company2.gainAccount;
            fit += company3.money;
            fit += company3.gainAccount;
            fit += company4.money;
            fit += company4.gainAccount;
            fit += company5.money;
            fit += company5.gainAccount;

            i.fitness = fit;

            company1.resetCost();
            company2.resetCost();
            company3.resetCost();
            company4.resetCost();
            company5.resetCost();
        }
    }
    public void roulette()
    {
        double r = 0.0;
        for(Individual rule : population)
                r = r + rule.fitness;
        for(Individual rule : population)
                rule.fitness = rule.fitness / r;
    }
    public int select()
    {
        int index = 0;

        double w = 0.0;
        for(Individual rule : population)
                w = w + rule.fitness;

        double chance = new Random().nextDouble() * w;


        for(Individual rule : population)
        {
            chance -= rule.fitness;

            if(chance <= 0.0)
            {
                return index;
            }

            index++;
        }
        return index;
    }
    public Individual crossover(int a, int b)
    {
        int crossoverPoint = new Random().nextInt(14);
        String ruleA = population.get(a).rule;
        String ruleB = population.get(b).rule;
        String ruleC = ruleA.substring(0, crossoverPoint) + ruleB.substring(crossoverPoint);
        char[] l = {ruleC.charAt(0),ruleC.charAt(5),ruleC.charAt(10)};
        l = removeDuplicates(l);
        ruleC = l[0] + ruleC.substring(1,5) + l[1] + ruleC.substring(6,10) + l[2] + ruleC.substring(11);

        Individual child = new Individual(ruleC);
        return child;
    }

    public void mutate(Individual a)
    {
        char[] rule = a.rule.toCharArray();
        double chance = new Random().nextDouble();
        int m = new Random().nextInt(14);

        int l = -1;

        if(chance <= 0.001)
        {
            System.out.println("Mutating");
            if(rule[m]== 's' || rule[m] == 'm' || rule[m] == 'e')
            {
                l = new Random().nextInt(3);
                char tmp = rule[m];
                rule[m] = rule[l*5];
                rule[l*5] = tmp;
            }
            else if(rule[m]== '&')
            {
                rule[m] = '|';
            }
            else if(rule[m]== '|')
            {
                rule[m] = '&';
            }
            else
            {
                while(l != Character.getNumericValue(rule[m]))
                    l = new Random().nextInt(10);
                rule[m] = Character.forDigit(l, 10);
            }
            a.rule = String.valueOf(rule);

        }
    }
    public int getMaxFit()
    {
        double r = 0.0;
        int ans = -1;
        int i = 0;

        for(Individual c : population) {
                if(c.fitness > r) {
                        ans = i;
                        r = c.fitness;
                }
                i++;
        }
        return ans;
    }

    // Helper Function for crossover
    public char[] removeDuplicates(char[] l)
    {
        Boolean s = false;
        Boolean m = false;
        Boolean e = false;

        char missing = '-';

        for(char i : l)
        {
            if(i == 's')
                s = true;
            if(i == 'm')
                m = true;
            if(i == 'e')
                e = true;
        }

        if(s == false)
            missing = 's';
        if(m == false)
            missing = 'm';
        if(e == false)
            missing = 'e';

        if(l[0] == l[1])
            l[0] = missing;
        if(l[0] == l[2])
            l[0] = missing;
        if(l[1] == l[2])
            l[1] = missing;

        return l;
    }
    //==========================================================================
    //              Run the Trading Sequence
    //              Create Company to load Data
    //==========================================================================
    public void startTrading() throws FileNotFoundException
    {
        int generation = 0;
        System.out.println("\n\nStarting Trading... \n\n");

        while (generation < 200)
        {
            evalFitness();
            roulette();

            int F = select();
            int M = select();

            Random prob = new Random();
            int luck = prob.nextInt(100);

            if(luck < 80)
            {
                Individual c1 = crossover(F, M);
                Individual c2 = crossover(M, F);
                mutate(c1);
                mutate(c2);
                population.add(c1);
                population.add(c2);

                generation++;
                evalFitness();
                roulette();
            }

            int k = getMaxFit();

            System.out.println("Current Generation: " + generation + " and current best rule: " + population.get(k).getRule());
        }
    }
}
