package GeneticAlgorithm;

import java.util.Arrays;


public class Population {
    public Chromosome[] chromosomes;

    public Population(int size) {
        this.chromosomes = new Chromosome[size];
        initializePopulation();
    }

    public void calculateFitness () {
        for (int i = 0; i < this.chromosomes.length; i++) {
            this.chromosomes[i].calculateFitness();
        }
        sortPopulationBasedOnFitness();
    }

    public void sortPopulationBasedOnFitness () {
        Arrays.sort(this.chromosomes, (chromosome1, chromosome2) -> {
            return chromosome2.getFitness() - chromosome1.getFitness();
        });
    }

    public Chromosome getChromosome (int index) {
        return this.chromosomes[index];
    }

    public void setChromosome (int index, Chromosome chromosome) {
        this.chromosomes[index] = chromosome;
    }

   public  void displayPopulation () {
        for (Chromosome chromosome : this.chromosomes) {
            chromosome.displayChromosome();
        }
    }

    private void initializePopulation () {
        for (int i = 0; i < this.chromosomes.length; i++) {
            this.chromosomes[i] = new Chromosome();
            this.chromosomes[i].generateChromosome();
        }
    }
}
