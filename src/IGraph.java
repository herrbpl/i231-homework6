import java.util.Iterator;


/**
 * Graph Interface. Graph has payload of class T. Idea is to do all operations (like DFS, BFS, etc.) outside of graph itself. I don't know, whether this is good idea.
 * @author siimaus
 * http://www.geeksforgeeks.org/bridge-in-a-graph/
 * https://www.udacity.com/course/viewer#!/c-cs215/l-48723544/m-48729232
 * TODO: Add methods for vertex and arc removal
 */
interface IGraph<T, A> {
	
	/**
	 * Returns iterable of all vertices in graph
	 * @return
	 */
	Iterator<IVertex<T,A>> vertices();
	/**
	 * Adds Vertex to Graph 
	 * @param data - Data object that Vertex contains
	 * @return returns created IVertex<T> object
	 */
	IVertex<T,A> createVertex(T data);	
	/**
	 * Adds IArc<A> from IVertex<T> from to IVertex<T> to  
	 * @param data - data for Arc
	 * @param from - originating node
	 * @param to - destination node
	 * @return returns created IArc<A> object
	 * @throws IllegalArgumentException when either from or to does not exist in graph
	 */
	IArc<T, A> createArc( A data, IVertex<T,A> from, IVertex<T,A> to ) throws IllegalArgumentException;
		
	
	/** 
	 * Returns number of IVertex in Graph
	 * @return
	 */
	
	int verticesCount();
	
	/**
	 * Removes all vertices from graph. 
	 */
	void clear();
	
}