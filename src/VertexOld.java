

/**
 * Each Vertex has two navigational pointers. One points to next Vertex in graph
 * Second points first Arc going out from this Vertex
 * 
 * @author siimaus
 *
 */
public class VertexOld {

	private String id; // identifier of Vertex
	private VertexOld nextVertex; // next point in vertex list. This is top of
								// stack.
	private ArcOld firstArc; // first arc from this point. This is top of
							// stack.
	private int info = 0;

	VertexOld(String s, VertexOld v, ArcOld e) {
		id = s;
		setNextVertex(v);
		setFirstArc(e);
	}

	VertexOld(String s) {
		this(s, null, null);
	}

	@Override
	public String toString() {
		return id;
	}

	public boolean hasArc() {
		return (this.getFirstArc() != null);
	}

	public ArcOld getArc() {
		return this.getFirstArc();
	}
	// TODO!!! Your Vertex methods here!

	public VertexOld getNextVertex() {
		return nextVertex;
	}

	public void setNextVertex(VertexOld next) {
		this.nextVertex = next;
	}

	public ArcOld getFirstArc() {
		return firstArc;
	}

	public void setFirstArc(ArcOld first) {
		this.firstArc = first;
	}

	/**
	 * Returns size of Vertex stack from this Vertex.
	 * 
	 * @return
	 */
	public int vertexCount() {
		int count = 0;
		VertexOld v = this;
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