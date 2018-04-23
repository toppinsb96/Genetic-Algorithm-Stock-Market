import java.io.*;
import java.util.*;
import java.lang.*;

public class Individual
{
    String rule;
    char op1, op2;
    int s, m, e;
    boolean buy;
    double fitness;


    //==========================================================================
    //                      Constructors for Individuals
    //                      Example individual 1: s050&e000&m000
    //                      Example individual 2: s050&e030&m010
    //                                            01234567890123
    //==========================================================================
    public Individual()
    {
        this.rule = "";
        s = m = e = 0;
        fitness = 0;
    }
    public Individual(String rule)
    {
        this.rule = rule;
        fitness = 0;
        parseRule();
    }
    public Individual(String rule, int s, int m, int e)
    {
        this.rule = rule;
        this.s = s;
        this.m = m;
        this.e = e;
        fitness = 0;
    }
    //==========================================================================
    //                          Basic Object functions
    //==========================================================================
    public void setRule(String rule)
    {
        this.rule = rule;
    }
    public void setS(int s)
    {
        this.s = s;
    }
    public void setM(int m)
    {
        this.m = m;
    }
    public void setE(int e)
    {
        this.e = e;
    }
    public String getRule()
    {
        return rule;
    }
    public int getS()
    {
        return s;
    }
    public int getM()
    {
        return m;
    }
    public int getE()
    {
        return e;
    }
    //==========================================================================
    //                          RULE CALCULATION AND DECIDER
    //==========================================================================
    public Boolean smaRule(int n, ArrayList<String> closings, ArrayList<String> dates, String today)
    {
        double numerator = 0.0;
        int start = dates.indexOf(today);
        double actualClosing = Double.parseDouble(closings.get(start));

        if(start < n)
            return false;

        for(int i = start - n - 1; i < start - 1; i++)
        {
            numerator += Double.parseDouble(closings.get(i));
        }
        double sma = numerator / n;


        return sma > actualClosing;
    }
    public Boolean maxRule(int n, ArrayList<String> closings, ArrayList<String> dates, String today)
    {
        double max = 0.0;
        int start = dates.indexOf(today);
        double actualClosing = Double.parseDouble(closings.get(start));

        if(start < n)
            return false;

        for(int i = start - n - 1; i < start - 1; i++)
        {
            if(Double.parseDouble(closings.get(i)) > max)
                max = Double.parseDouble(closings.get(i));
        }


        return max < actualClosing;
    }
    public Boolean emaRule(int n, ArrayList<String> closings, ArrayList<String> dates, String today)
    {
        int start = dates.indexOf(today);
        double actualClosing = Double.parseDouble(closings.get(start));
        double numerator = 0.0;
        double denominator = 0.0;
        double alpha = 2 / (n + 1);

        if(start < n)
            return false;

        int exp = 0;
        for(int i = start - n; i < start; i++)
        {
            numerator += Math.pow((1-alpha), exp) * Double.parseDouble(closings.get(i));
            exp++;
        }

        for(int i = 0; i < n; i++)
            denominator += Math.pow((1-alpha), i);

        return numerator / denominator < actualClosing;

    }
    public Boolean checkBuy(ArrayList<String> closings, ArrayList<String> dates, int day)
    {
        char rule1 = getType(rule.charAt(0));
        char rule2 = getType(rule.charAt(5));
        char rule3 = getType(rule.charAt(10));

        boolean set1 = false;
        boolean set2 = false;

        String today = dates.get(day);


        if(op1 == '&')
        {
            if(calculateRule(rule1, closings, dates, today) && calculateRule(rule2, closings, dates, today))
                set1 = true;
            else
                set1 = false;
        }
        else
        {
            if(calculateRule(rule1, closings, dates, today) || calculateRule(rule2, closings, dates, today))
                set1 = true;
            else
                set1 = false;
        }


        if(op2 == '&')
        {
            if(set1 && calculateRule(rule3, closings, dates, today))
                return true;
            else
                return false;
        }
        else
        {
            if(set1 || calculateRule(rule3, closings, dates, today))
                return true;
            else
                return false;
        }
    }
    //==========================================================================
    //                          Helper Functions for Rules
    //==========================================================================
    public Boolean calculateRule(char rule, ArrayList<String> closings, ArrayList<String> dates, String today)
    {
        switch(rule)
        {
            case 's' :  return smaRule(this.s, closings, dates, today);
            case 'm' :  return maxRule(this.m, closings, dates, today);
            case 'e' :  return emaRule(this.e, closings, dates, today);
        }
        return false;
    }
    public void parseRule()
    {
        findType(rule.charAt(0));
        findType(rule.charAt(5));
        findType(rule.charAt(10));
        op1 = rule.charAt(4);
        op2 = rule.charAt(9);
    }
    public void createRandom()
    {
        ArrayList<String> ruleTypes = new ArrayList<String>();
        ruleTypes.add("s");
        ruleTypes.add("m");
        ruleTypes.add("e");
        Collections.shuffle(ruleTypes);

        int prob1 = new Random().nextInt(200);
        int prob2 = new Random().nextInt(200);
        int prob3 = new Random().nextInt(200);
        String n1 = String.format("%03d", prob1);
        String n2 = String.format("%03d", prob2);
        String n3 = String.format("%03d", prob3);

        int logOP1 = new Random().nextInt(2);
        int logOP2 = new Random().nextInt(2);

        op1 = (logOP1 > 0) ? '&' : '|';
        op2 = (logOP2 > 0) ? '&' : '|';

        rule += ""  + ruleTypes.get(0) + n1;
        rule += op1 + ruleTypes.get(1) + n2;
        rule += op2 + ruleTypes.get(2) + n3;
        parseRule();
    }
    public void findType(char type)
    {
        int index = rule.indexOf(type) + 1;
        switch(type)
        {
            case 's' :  this.s = Integer.parseInt(rule.substring(index, index + 3));
                        break;
            case 'm' :  this.m = Integer.parseInt(rule.substring(index, index + 3));
                        break;
            case 'e' :  this.e = Integer.parseInt(rule.substring(index, index + 3));
                        break;
        }
    }
    public char getType(char type)
    {
        switch(type)
        {
            case 's' :  return 's';
            case 'm' :  return 'm';
            case 'e' :  return 'e';

        }
        return 'f';
    }
}
