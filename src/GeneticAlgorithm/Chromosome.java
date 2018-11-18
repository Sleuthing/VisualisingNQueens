package GeneticAlgorithm;

import Backtracking.SolverUtils;
import java.util.Arrays;

public class Chromosome {
	// Index is row on chess board
	// value for each index is the column on chess board.
	public int[] genes;
	private int fitness;
	public final int LENGTH = Main.M;

	public Chromosome() {
		this.genes = new int[this.LENGTH];
		this.fitness = -100;
	}

	public void generateChromosome() {
		this.genes = SolverUtils.generateRandomState(LENGTH);
	}

	public void calculateFitness() {
		int conflicts = 0;
		for (int i = 0; i < this.LENGTH; i++) {
			conflicts += SolverUtils.getHeuristicCost(this.genes);
		}
		this.fitness = -1 * conflicts;
	}

	public int getFitness() {
		return this.fitness;
	}

	public int getGenes(int index) {
		return this.genes[index];
	}

	public void setGenes(int index, int gene) {
		this.genes[index] = gene;
	}

	public void displayChromosome() {
		System.out.println(Arrays.toString(this.genes));
	}
}
