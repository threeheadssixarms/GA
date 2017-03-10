import java.util.ArrayList;
import java.util.Random;

public class Population {
	private static int size;
	private static int tournament_size;
	private ArrayList<Individual> inds = new ArrayList<Individual>();
	private Individual child1;
	private Individual child2;
	private static Random rand = new Random(10);
	private static double crossover_rate;
	private Individual bestIndividual;

	public Population() {
	}

	public void initialize() {
		Individual ind;
		for (int i = 0; i < size; i++) {
			ind = new Individual();
			ind.initialize();
			inds.add(ind);
		}
	}

	public static void setSize(int n) {
		size = n;
		return;
	}

	public static void setTournamentSize(int k) {
		tournament_size = k;
		return;
	}

	public static void setCrossover_rate(double crossover_rate) {
		Population.crossover_rate = crossover_rate;
	}

	public void PMX(int left, int right) {
		Individual parent1 = tournamentSelection();
		Individual parent2 = tournamentSelection();
		if (rand.nextDouble() > crossover_rate) {
			child1 = parent1;
			child2 = parent2;
			return;
		}

		if (left > right) {
			int temp = right;
			right = left;
			left = temp;
		}

		child1 = new Individual();
		child2 = new Individual();
		for (int i = 0; i < Individual.getLength(); i++) {
			int temp = -1;
			int temp2 = -1;
			if (i < left || i > right) {
				temp = parent2.getGene(i);
				temp2 = parent1.getGene(i);
				for (int k = left; k < right + 1; k++) {
					for (int j = left; j < right + 1; j++) {
						if (parent1.getGene(j) == temp) {
							temp = parent2.getGene(j);
						}
						if (parent2.getGene(j) == temp2) {
							temp2 = parent1.getGene(j);
						}
					}
				}
				child1.addGene(temp, i);
				child2.addGene(temp2, i);
			} else {
				child1.addGene(parent1.getGene(i), i);
				child2.addGene(parent2.getGene(i), i);
			}
		}
		return;
	}

	public void add(Individual ind) {
		inds.add(ind);
	}

	public void print() {
		ArrayList<Integer> temp;

		for (int i = 0; i < size; i++) {
			temp = inds.get(i).getGenes();
			for (int j = 0; j < Individual.getLength(); j++) {
				System.out.print(temp.get(j) + " ");
			}
			System.out.println();
		}
	}

	public Individual tournamentSelection() {
		int k = rand.nextInt(size);
		for (int i = 0; i < tournament_size - 1; i++) {
			int j = rand.nextInt(size);
			if (inds.get(k).getFitness() > inds.get(j).getFitness()) {
				k = j;
			}
		}
		return inds.get(k);
	}

	public Individual getBestIndividual() {
		bestIndividual = new Individual();
		int k = 0;
		for (int i = 0; i < size; i++) {
			if (inds.get(k).getFitness() > inds.get(i).getFitness()) {
				k = i;
			}
		}
		bestIndividual = inds.get(k);
		return bestIndividual;
	}

	public static void main(String[] args) {
		Population.setSize(100);
		Population.setTournamentSize(2);
		Population.setCrossover_rate(0.8);
		Individual.setMutation_rate(0.1);
		Individual.setLength(20);
		Individual.setDistances(50);

		Population pop = new Population();
		pop.initialize();
		// pop.print();
		// Individual.printDistances();

		for (int j = 0; j < 1000; j++) {
			pop.getBestIndividual().print();
			System.out.println(pop.getBestIndividual().getFitness());
			// new population
			Population new_pop = new Population();
			for (int i = 0; i < size / 2; i++) {
				pop.PMX(rand.nextInt(Individual.getLength()), rand.nextInt(Individual.getLength()));
				// child1.print();
				// child2.print();
				pop.child1.mutation();
				pop.child2.mutation();
				new_pop.add(pop.child1);
				new_pop.add(pop.child2);
			}

			// replace a random indv of new_pop with the best of pop
			if (new_pop.inds.contains(pop.getBestIndividual()) == false) {
				new_pop.inds.set(rand.nextInt(size), pop.getBestIndividual());
			}

			pop = new_pop;
		}
		//Individual.printDistances();

	}

}
