package GeneticAlgorithm;

import java.util.Random;

public class GeneticAlgo {
	private Population population;
	private int populationSize;
	public static int ELITE_CHROMOSOME = 3;
	public static int TOURNAMENT_SELECTION_SIZE = 6;
	public static double MUTATE_RATE = 0.5;

	public GeneticAlgo(int populationSize) {
		this.populationSize = populationSize;
		this.population = new Population(populationSize);
	}

	public void naturalSelection() {
		this.population.calculateFitness();
		this.population = generateNextGeneration();
		this.population.calculateFitness();
	}

	public Chromosome getFittestChromosome() {
		return this.population.getChromosome(0);
	}

	private Chromosome tournamentSelection() {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE);
		Random random = new Random();
		for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {
			tournamentPopulation.setChromosome(i, this.population.getChromosome(random.nextInt(this.populationSize)));
		}
		tournamentPopulation.sortPopulationBasedOnFitness();
		return tournamentPopulation.getChromosome(0);

	}

	private Chromosome crossOver(Chromosome parent1, Chromosome parent2, int o) {
		Chromosome offspring = new Chromosome();
		Random random = new Random();
		int marker = random.nextInt(parent1.LENGTH);
		for (int i = 0; i < parent1.LENGTH; i++) {
			if (o == 0) {
				if (i < marker)
					offspring.setGenes(i, parent1.getGenes(i));
				else
					offspring.setGenes(i, parent2.getGenes(i));
			} else {
				if (i < marker)
					offspring.setGenes(i, parent2.getGenes(i));
				else
					offspring.setGenes(i, parent1.getGenes(i));
			}
		}
		return offspring;
	}

	private Chromosome mutate(Chromosome chromosome) {
		Random random = new Random();
		for (int i = 0; i < chromosome.LENGTH; i++) {
			if (Math.random() <= MUTATE_RATE) {
				Chromosome e = chromosome;
				e.setGenes(i, random.nextInt(chromosome.LENGTH));
				if (e.getFitness() > chromosome.getFitness()) {
					chromosome.setGenes(i, random.nextInt(chromosome.LENGTH));
				}
			}
		}
		return chromosome;
	}

	public void setChr(Population p, Chromosome c, Chromosome c2, int t) {
		p.setChromosome(t, crossOver(c, c2, 1));
		if (t + 1 < this.populationSize)
			p.setChromosome(t + 1, crossOver(c, c2, 0));
	}

	private Population generateNextGeneration() {
		Population nextGenerationPopulation = new Population(this.populationSize);
		for (int i = 0; i < ELITE_CHROMOSOME; i++) {
			nextGenerationPopulation.setChromosome(i, mutate(this.population.getChromosome(i)));
		}
		for (int i = ELITE_CHROMOSOME; i < this.populationSize; i = i + 2) {

			Chromosome p1 = tournamentSelection();
			Chromosome p2 = tournamentSelection();
			nextGenerationPopulation.setChromosome(i, crossOver(p1, p2, 1));
			if (i + 1 < this.populationSize)
				nextGenerationPopulation.setChromosome(i + 1, mutate(crossOver(p1, p2, 0)));

		}
		return nextGenerationPopulation;
	}

	public Population getPop() {
		return this.population;
	}
}
