import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Interface for dealing with Vertexes. T is object info we store in Vertex
 * 
 * @author siimaus
 *
 */
interface IVertex<T> {
	/**
	 * Get iterable of adjacent vertices to current one.
	 * 
	 * @return
	 */
	Iterator<IVertex<T>> getAdjacent();

	/**
	 * Gets degree of IVertex, that means count of Arcs/Edges connected to
	 * IVertex
	 * 
	 * @return
	 */
	int degree();

	/**
	 * Sets Vertex value to T
	 * 
	 * @param newValue
	 */
	void setValue(T newValue);

	/**
	 * gets data of Vertex
	 * 
	 * @return
	 */
	T getValue();
}

abstract class AbstractVertex<T, A> implements IVertex<T>, Iterable<IVertex<T>> {
	protected T data;

	protected IVertex<T> nextVertex;
	protected IArc<A> firstArc;

	AbstractVertex(T data, IVertex<T> next, IArc<A> arc) {
		this.data = data;
		this.nextVertex = next;
		this.firstArc = arc;
	}

	AbstractVertex(T data) {
		this(data, null, null);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getValue().toString();
	}

	public Iterator<IVertex<T>> iterator() {
		return new ListIterator<T>(this.nextVertex);
	}

	
	/**
	 * Internal class with cursor.
	 * @author siimaus
	 *
	 * @param <T>
	 */
	private class ListIterator<T> implements Iterator<IVertex<T>> {
		private AbstractVertex<T, A> current;
		private IArc<A> currentArc;
		private int type = 0; // indicates whether to iterate over own chain of items or over list of adjacent vertices

		public ListIterator(IVertex<T> first) {
			current = (AbstractVertex<T, A>) first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public IVertex<T> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			IVertex<T> item = current;
			current = (AbstractVertex<T, A>) current.nextVertex;
			return item;
		}
	}
	
	
	/**
	 * Returns iterator for adjacent Vertexes.
	 */
	@Override
	public Iterator<IVertex<T>> getAdjacent() {
		return null;
	}
	
	@Override
	public int degree() {
		if (this.firstArc == null) return 0;
		return this.firstArc.arcLength();
	}

}

class StringVertex extends AbstractVertex<String, String> {

	StringVertex(String data, IVertex<String> next, IArc<String> arc) {
		super(data, next, arc);
		// TODO Auto-generated constructor stub
	}

	
	

	@Override
	public void setValue(String newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * Each Vertex has two navigational pointers. One points to next Vertex in graph
 * Second points first Arc going out from this Vertex
 * 
 * @author siimaus
 *
 */
public class Vertex {

	private String id; // identifier of Vertex
	private Vertex nextVertex; // next point in vertex list. This is top of
								// stack.
	private Arc firstArc; // first arc from this point. This is top of
							// stack.
	private int info = 0;

	Vertex(String s, Vertex v, Arc e) {
		id = s;
		setNextVertex(v);
		setFirstArc(e);
	}

	Vertex(String s) {
		this(s, null, null);
	}

	@Override
	public String toString() {
		return id;
	}

	public boolean hasArc() {
		return (this.getFirstArc() != null);
	}

	public Arc getArc() {
		return this.getFirstArc();
	}
	// TODO!!! Your Vertex methods here!

	public Vertex getNextVertex() {
		return nextVertex;
	}

	public void setNextVertex(Vertex next) {
		this.nextVertex = next;
	}

	public Arc getFirstArc() {
		return firstArc;
	}

	public void setFirstArc(Arc first) {
		this.firstArc = first;
	}

	/**
	 * Returns size of Vertex stack from this Vertex.
	 * 
	 * @return
	 */
	public int vertexCount() {
		int count = 0;
		Vertex v = this;
		while (v != null) {
			count++;
			v = v.getNextVertex();
		}
		return count;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}

}