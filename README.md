For full information go to Stock.pdf


The project tasked Computer Scientists to create an algorithm to find a single rule to be
used in the stock market. The rule is calculated from a genetic algorithm approach and
the rule is considered the individual during the process. The rules are created and
evaluated over a daily time span. For the genetic algorithm, I used .csv ranging from
1980 to 2018. This allowed me to experiment with a wide variety of dates until I finished
creating the genetic algorithm. The experiment part required 30 historical pieces for
comparison against the rule generated from the genetic algorithm. I chose to limit this
timespan from 2005 to 2018, so that more companies were available for comparison.
Keeping companies consistent in time across the 30 historical pieces is important,
because stock market changes can affect all the companies in similar or widely different
ways. So, keeping a constant in place was important.
The project created strict parameters for the programming of the genetic algorithm. The
genetic algorithm followed a sequence of generating 200 individuals and having an
initial population of 20. The 20 was created using Java’s random number
generator(RNG), as well as all probabilities and chance decisions in the actual algorithm
use the same RNG. The Java RNG is not perfect, but it allowed for enough randomness
to create an environment to run the genetic algorithm in. Aspects of the process like the
luck variable for crossover and the chance of mutation were set in the project
description.

The project is comprised of two major portions. Generating a rule and then testing that
rule. The generating a rule is done in the genetic algorithm and the testing is done
during the experiment.
