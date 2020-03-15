package control;

import java.util.LinkedList;

public class Path {

	private boolean visited[];
	private LinkedList<Integer> path;
	private LinkedList<LinkedList<Integer>> Allpaths;
	private LinkedList<Integer> GainP;
	private LinkedList<Integer> DelTaK;
	private Graph g;
	private Loop l;

	Path(Graph g, Loop l) {
		this.g = g;
		this.l = l;
		visited = new boolean[g.size()];
		path = new LinkedList<Integer>();
		Allpaths = new LinkedList<LinkedList<Integer>>();
		GainP = new LinkedList<Integer>();
        DelTaK = new LinkedList<Integer>();
	}

	LinkedList<LinkedList<Integer>> Getpaths() {
		return Allpaths;
	}

	@SuppressWarnings("unchecked")
	void getpaths(int i, int d) {

		if (!path.contains(i))
			path.addLast(i);
		visited[i] = true;

		if (i == d && visited[i] == true) {
			Allpaths.add((LinkedList<Integer>) path.clone());
			visited[i] = false;
			path.pollLast();
			return;
		}

		for (int j = 0; j < g.size(); j++) {
			if (g.GetEdge(i, j) != 0 && visited[j] == false) {
				visited[j] = true;
				path.addLast(j);
				getpaths(j, d);
			}
		}

		visited[i] = false;
		if (!path.isEmpty())
			path.removeLast();

	}

	LinkedList<Integer> GainOfPahts() {
		for (int i = 0; i < Allpaths.size(); i++) {
			int Gain = 1;
			for (int j = 0; j < Allpaths.get(i).size() - 1; j++) {
				Gain *= g.GetEdge(Allpaths.get(i).get(j), Allpaths.get(i).get(j + 1));
			}
			GainP.add(Gain);
		}
		return GainP;
	}

	Double overAll() {
		Double ov = 0.0;
		
		for (int i = 0; i < Allpaths.size(); i++) {
			int DK = l.DeltaOfk(Allpaths.get(i));
			DelTaK.add(DK);
			ov +=  DK * GainP.get(i);
		}
		ov = ov / l.Delta();
		return ov;
	}
	
	public LinkedList<Integer> getDelTaK() {
		return DelTaK;
	}

}