
public class City {
	private int x;
	private int y;
	private int name;
	private static int[][] distances;
	public City() {

	}

	public double distanceTo(City city) {
		return Math.sqrt(Math.pow(city.x - x, 2) + Math.pow(city.y - y, 2));
	}
	
	public static void setDistances(int max) {
		distances = new int[Individual.getLength()][Individual.getLength()];
		for (int i = 0; i < Individual.getLength(); i++)
			for (int j = 0; j <= i; j++) {
				if (i == j)
					distances[i][j] = 0;
				else {
				//	distances[i][j] = distances[j][i] = 

				}
			}
	}

}
