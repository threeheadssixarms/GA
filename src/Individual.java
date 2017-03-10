import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Individual {
	private static int length;
	private ArrayList<Integer> genes = new ArrayList<Integer>();
	private static double mutation_rate;
	private static int[][] distances;
	private static Random rand = new Random(10);
	private int fitness;

	public static int[][] getDistances() {
		return distances;
	}

	public Individual() {
	}

	public void initialize() {
		for (int i = 0; i < length; i++) {
			genes.add(i);
		}
		Collections.shuffle(genes, rand);
	}

	public static void setLength(int length) {
		Individual.length = length;
	}

	public static int getLength() {
		return length;
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public static void setMutation_rate(double m_rate) {
		Individual.mutation_rate = m_rate;
	}

	public void addGene(int gene, int position) {
		genes.add(position, gene);
	}

	public int getGene(int position) {
		return genes.get(position);
	}

	public static void setDistances(int max) {
		distances = new int[length][length];
		for (int i = 0; i < length; i++)
			for (int j = 0; j <= i; j++) {
				if (i == j)
					distances[i][j] = 0;
				else {
					distances[i][j] = distances[j][i] = rand.nextInt(max) + 1;

				}
			}
	}

	public void mutation() {
		for (int i = 0; i < length; i++) {
			if (rand.nextDouble() < mutation_rate) {
				swap(rand.nextInt(length), rand.nextInt(length));

			}
		}
	}

	private void swap(int a, int b) {
		int temp = getGene(b);
		genes.set(b, getGene(a));
		genes.set(a, temp);
	}

	public int getFitness() {
		if (fitness!=0){
			return fitness;
		}
		fitness = distances[genes.get(length - 1)][genes.get(0)];
		for (int i = 0; i < length - 1; i++) {
			fitness += distances[genes.get(i)][genes.get(i + 1)];
		}
		return fitness;
	}

	public void print() {
		for (int gene : genes) {
			System.out.print(gene + " ");
		}
		System.out.println();
	}

	public static void printDistances() {
		int[][] a = Individual.getDistances();
		for (int i = 0; i < Individual.getLength(); i++) {
			for (int k = 0; k < Individual.getLength(); k++) {
				System.out.print(a[i][k] + " ");
			}
			System.out.println();
		}
	}
}
