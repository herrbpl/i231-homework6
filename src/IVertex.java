import java.util.Iterator;

/**
 * Interface for dealing with Vertexes. T is object info we store in Vertex
 * 
 * @author siimaus
 *
 */
// interface IVertex<T, A> extends INode<T>, Iterable<IVertex<T,A>>{
interface IVertex<T, A> extends INode<T>{
	/**
	 * Get iterator of adjacent vertices to current one.
	 * 
	 * @return
	 */
	Iterator<IVertex<T,A>> getAdjacent();

	/**
	 * Gets all outgoing arcs from vector.
	 * @return
	 */
	Iterator<IArc<T,A>> getOutgoingArcs();
	
	/**
	 * Gets degree of IVertex, that means count of Arcs/Edges connected to
	 * IVertex
	 * 
	 * @return
	 */
	int degree();

	/**
	 * Removes all outgoing arcs from vertex.
	 */
	void clear();
	
}
