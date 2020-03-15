package control;

public class Graph {

	private int n;
	private int[][] graph;

	Graph(int [][] g) {
		graph = g;
		n = g.length;
	}

	boolean SetEdge(int x1, int x2, int w) {

		if (x1 > n || x2 > n || x1 < 0 || x2 < 0) {
			return false;
		}

		graph[x1][x2] = w;
		return true;
	}

	int GetEdge(int x1, int x2) {

		if (x1 > n || x2 > n || x1 < 0 || x2 < 0) {
			return -1;
		}

		return graph[x1][x2];
	}

	public int size() {
		return n;
	}

}
