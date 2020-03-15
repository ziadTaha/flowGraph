package control;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Loop {

	private boolean visited[];
	private LinkedList<Integer> loop;
	private LinkedList<LinkedList<Integer>> AllLoops;
	private LinkedList<Integer> GainL;
	private LinkedList<Set<Integer>> nontouch;
	private LinkedList<String> nonName;
	private LinkedList<Integer> nonGain;
	private Set<Set<Integer>> a;
	private Graph g;

	Loop(Graph g) {
		this.g = g;
		visited = new boolean[g.size()];
		loop = new LinkedList<Integer>();
		AllLoops = new LinkedList<LinkedList<Integer>>();
		a = new HashSet<Set<Integer>>();
		nonName = new LinkedList<String>();
		nontouch = new LinkedList<Set<Integer>>();
		GainL = new LinkedList<Integer>();
		nonGain = new LinkedList<Integer>();
	}

	private void getloops(int i, int d) {

		if (!loop.contains(i))
			loop.addLast(i);

		if (i == d && visited[i] == true) {
			AllLoops.add((LinkedList<Integer>) loop.clone());
			visited[i] = false;
			loop.pollLast();
			return;
		}

		for (int j = d; j < g.size(); j++) {
			if (g.GetEdge(i, j) != 0 && visited[j] == false) {
				visited[j] = true;
				loop.addLast(j);
				getloops(j, d);
			}
		}

		visited[i] = false;
		if (!loop.isEmpty())
			loop.removeLast();

	}

	void GetAllLoops() {
		for (int i = 0; i < g.size(); i++) {
			getloops(i, i);
		}

	}

	LinkedList<LinkedList<Integer>> Getpaths() {
		
		return AllLoops;
	}

	LinkedList<Integer> GainOfLoops() {
		for (int i = 0; i < AllLoops.size(); i++) {
			int Gain = 1;
			for (int j = 0; j < AllLoops.get(i).size() - 1; j++) {
				Gain *= g.GetEdge(AllLoops.get(i).get(j), AllLoops.get(i).get(j + 1));
			}
			GainL.add(Gain);
		}
		return GainL;
	}

	void Nontouching() {

		for (int i = 0; i < AllLoops.size(); i++) {
			for (int j = i; j < AllLoops.size(); j++) {
				Set<Integer> hSet = new HashSet<Integer>(AllLoops.get(i));
				hSet.addAll(AllLoops.get(j));
				if (hSet.size() == AllLoops.get(j).size() + AllLoops.get(i).size() - 2) {
					String s = Integer.toString(i);
					s += Integer.toString(j);
					nontouch.add(hSet);
					nonName.add(s);
					a.add(hSet);
					nonGain.add(GainL.get(i) * GainL.get(j));
				}
			}
		}

		for (int i = 0; i < nontouch.size(); i++) {
			for (int j = 0; j < AllLoops.size(); j++) {
				Set<Integer> hSet = new HashSet<Integer>(AllLoops.get(j));
				hSet.addAll(nontouch.get(i));
				if (hSet.size() == AllLoops.get(j).size() + nontouch.get(i).size() - 1) {
					if (!a.contains(hSet)) {
						a.add(hSet);
						String s = nonName.get(i) + Integer.toString(j);
						nontouch.add(hSet);
						nonName.add(s);
						nonGain.add(nonGain.get(i) * GainL.get(j));
					}
				}
			}
		}

	}

	int Delta() {
		int D = 1;
		for (int i = 0; i < GainL.size(); i++) {
			D -= GainL.get(i);
		}
		for (int i = 0; i < nonName.size(); i++) {
			if (nonName.get(i).length() % 2 == 0) {
				D += nonGain.get(i);
			} else {
				D -= nonGain.get(i);
			}
		}
		return D;
	}

	int DeltaOfk(LinkedList<Integer> k) {
		int Dk = 1;
		for (int i = 0; i < AllLoops.size(); i++) {
			Set<Integer> hSet = new HashSet<Integer>(AllLoops.get(i));
			hSet.addAll(k);
			if (hSet.size() == k.size() + AllLoops.get(i).size() - 1) {
				Dk -= GainL.get(i);
			}
		}

		for (int i = 0; i < nonName.size(); i++) {
			Set<Integer> hSet = new HashSet<Integer>(k);
			hSet.addAll(nontouch.get(i));
			if (hSet.size() == nontouch.get(i).size() + k.size()) {
				if (nonName.get(i).length() % 2 == 0) {
					Dk += nonGain.get(i);
				} else {
					Dk -= nonGain.get(i);
				}
			}
		}

		return Dk;
	}
	
	LinkedList<String> getNonTouch(){
		return nonName;
	}

}
