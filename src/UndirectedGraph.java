import java.util.Iterator;

public class UndirectedGraph<T, A> extends AbstractGraph<T, A> {

	public UndirectedGraph(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lists all edges in graph.
	 * 
	 * @return
	 */
	public Iterator<Edge<T, A>> getEdges() {
		return null;
	}

	/**
	 * Creates edge between two Vertexes
	 * @param data Payload for Arc's
	 * @param from Vertex from
	 * @param to Vertex to
	 * @return created Edge
	 */
	public Edge<T,A> createEdge(A data, IVertex<T,A> from, IVertex<T,A> to ) {
		
		IArc<T,A> a1 = createArc(data, from, to);
		IArc<T,A> a2 = createArc(data, to, from);
			
		return new Edge<T,A>(from, to, a1, a2);
	}
}
