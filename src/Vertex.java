import java.util.Iterator;
import java.util.NoSuchElementException;






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