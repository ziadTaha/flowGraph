package control;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Graph g = new Graph(8) ;

		g.SetEdge(0, 1, 5);
		g.SetEdge(1, 2, 5);
		g.SetEdge(2, 3, 5);
		g.SetEdge(3, 4, 5);
		g.SetEdge(4, 5, 5);
		g.SetEdge(5, 6, 5);
		g.SetEdge(6, 7, 5);
		g.SetEdge(1, 0, -5);
		g.SetEdge(3, 2, -5);
		g.SetEdge(5, 4, -5);
		g.SetEdge(7, 6, -5);*/


//4 non touch
		/*Graph g = new Graph(8) ;

		g.SetEdge(0, 1, 5);
		g.SetEdge(1, 2, 8);
		g.SetEdge(2, 3, 1);
		g.SetEdge(3, 4, 5);
		g.SetEdge(4, 5, 8);
		g.SetEdge(5, 6, 5);
		g.SetEdge(6, 7, 8);
		g.SetEdge(1, 0, 8);
		g.SetEdge(3, 2, 8);
		g.SetEdge(5, 4, 8);
		g.SetEdge(7, 6, 8);
		*/

       //last from courses + change
		int a [][] = new int [5][5];
		Graph g = new Graph(a) ;
		g.SetEdge(0, 1, 5);
		g.SetEdge(1, 2, 5);
		g.SetEdge(2, 3, 5);
		g.SetEdge(3, 4, 5);
		g.SetEdge(4, 5, 5);
		g.SetEdge(5, 6, 5);
		g.SetEdge(6, 7, 5);
	    g.SetEdge(3, 6, 5);
		g.SetEdge(5, 4, -5);
		g.SetEdge(6, 2, -5);
		g.SetEdge(7, 1, -5);
		g.SetEdge(5, 7, 5);
		g.SetEdge(7,5, -5);
		//g.SetEdge(6,5, 8);

		/*Graph g = new Graph(5) ;
		g.SetEdge(0, 1, 10);
		g.SetEdge(1, 2, 10);
		g.SetEdge(2, 3, 10);
		g.SetEdge(3, 4, 10);
		g.SetEdge(0, 2, 10);
		g.SetEdge(0, 4, 10);
		g.SetEdge(3, 1, 10);
		g.SetEdge(3, 0, 10);*/
		//g.SetEdge(6,5, 8);
		Loop l = new Loop(g);
		l.GetAllLoops();
		l.Getpaths();
	    l.GainOfLoops();
		l.Nontouching();
		l.Delta();

		Path p = new Path(g , l);
		p.getpaths(0, 7);
		p.Getpaths();
		p.GainOfPahts();
		p.overAll();


	}

}
