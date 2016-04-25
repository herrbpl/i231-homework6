
import java.util.*;

/**
 * This class performs tasks on graph IGraph<T,A>
 */

public class GraphTask<T,A> {
	

	public static void main(String[] args) {
		GraphTask<String, String> a = new GraphTask<String, String>();
		a.run();
		throw new RuntimeException("Nothing implemented yet!"); // delete this
	}

	@SuppressWarnings("unchecked")
	public void run() {
		Graph g = new Graph("G");
		
		//AbstractGraph<T,A> g = new AbstractGraph<>("Test"); 
		this.createRandomSimpleGraph((IGraph<T, A>) g, 6, 9);
		//g.createRandomSimpleGraph(6, 9);
		System.out.println(g);
		//System.out.println(g.);
		System.out.println(g.getAdjacencyMatrixString());
		
		// TODO!!! Your experiments here
	}

	public Graph createGraph(String gid) {			
		return new Graph(gid);
	}

	/**
	 * Create a connected undirected random tree with n vertices. Each new
	 * vertex is connected to some random existing vertex. As you cannot have generic arrays, i use Objects which i cast to correct type 
	 * This method is problematic because it forces to cast String into type T which may be incorrect. Maybe I need solution for Vertex labeling?
	 * I should be able to generate random graph for any kind of structure 
	 * 
	 * @param g 
	 * 			  IGraph<T,A> for which random tree is built for. It is caller responsibility to clear graph before creating.
	 * @param n
	 *            number of vertices added to this graph
	 */
	@SuppressWarnings("unchecked")
	public void createRandomTree(IGraph<T,A> g, int n) {		
		if (n <= 0)
			return;
		
		Object[] varray = new Object[n];
		for (int i = 0; i < n; i++) {
			//varray[i] = g.createVertex((T) ("v" + String.valueOf(n - i))); // old b inserts vertices here with reverse order!!!
			varray[i] = g.createVertex(null); // setting no ID-s at moment
			if (i > 0) {
				int vnr = (int) (Math.random() * i);
				//g.createArc("a" + varray[vnr].toString() + "_" + varray[i].toString(), (IVertex<T,A>)varray[vnr], (IVertex<T,A>)varray[i]);
				g.createArc(null, (IVertex<T,A>)varray[vnr], (IVertex<T,A>)varray[i]);
				//g.createArc("a" + varray[i].toString() + "_" + varray[vnr].toString(), (IVertex<T,A>)varray[i], (IVertex<T,A>)varray[vnr]);
				g.createArc(null, (IVertex<T,A>)varray[i], (IVertex<T,A>)varray[vnr]);
			} else {
			}
		}
	}

	
	/**
	 * Create a connected simple (undirected, no loops, no multiple arcs)
	 * random graph with n vertices and m edges.
	 * 
	 * @param n
	 *            number of vertices
	 * @param m
	 *            number of edges
	 */
	@SuppressWarnings("unchecked")
	public void createRandomSimpleGraph(IGraph<T,A> g, int n, int m) {
		if (n <= 0)
			return;
		if (n > 2500)
			throw new IllegalArgumentException("Too many vertices: " + n);
		if (m < n - 1 || m > n * (n - 1) / 2)
			throw new IllegalArgumentException("Impossible number of edges: " + m);
		// first = null;
		createRandomTree(g, n); // n-1 edges created here
		
		/*
		Vertex[] vert = new Vertex[n];
		Vertex v = first;
		int c = 0;
		while (v != null) {
			vert[c++] = v;
			v = v.getNextVertex();
		}
		*/
		Object[] vert = new Object[n];
		int c = 0;
		Iterator<IVertex<T,A>> iter = g.vertices();
		while (iter.hasNext()) {			
			vert[c++] = iter.next();
		}
		
		int[][] connected = ((AbstractGraph<T,A>)g).getAdjacencyMatrix();
		
		int edgeCount = m - n + 1; // remaining edges
		while (edgeCount > 0) {
			int i = (int) (Math.random() * n); // random source
			int j = (int) (Math.random() * n); // random target
			if (i == j)
				continue; // no loops
			if (connected[i][j] != 0 || connected[j][i] != 0)
				continue; // no multiple edges
			IVertex<T,A> vi = (IVertex<T,A>)vert[i];
			IVertex<T,A> vj = (IVertex<T,A>)vert[j];
			
			//createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
			g.createArc(null, vi, vj);
			connected[i][j] = 1;
			//createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
			g.createArc(null, vj, vi);
			connected[j][i] = 1;
			edgeCount--; // a new edge happily created
		}
	}	

}
