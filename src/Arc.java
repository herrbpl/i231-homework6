

/**
 * Interface for Arc connecting two nodes.
 * @author siimaus
 *
 */
interface IArc<T> {
	/**
	 * Gets target of this Arc
	 * @return IVertex<T> indicated by this arc
	 */
	IVertex<T> getTarget();
	/**
	 * Sets value to Arc
	 * @param newValue
	 */
	void setValue(T newValue);
	/**
	 * Gets value of Arc
	 * @return
	 */
	T getValue();
	
	/**
	 * Returns total length of arc from this point on.
	 * @return
	 */
	int arcLength();
	
	
	/**
	 * Gets next Arc in chain
	 * @return
	 */
	IArc<T> next();
}


/**
 * Arc is link between two Vertex instances. Source Vertex is where Arcs
 * originate from. Each Arc has two pointers. First is Vertex to which this Arc
 * links to. Second is next Arc in chain.
 * 
 * @author siimaus
 *
 */
class Arc {

	private String id; // ID of arc
	private Vertex targetVertex; // Vertex this Arc points to.
	private Arc nextArc; // next Arc in stack
	private int info = 0;

	Arc(String s, Vertex v, Arc a) {
		id = s;
		setTarget(v);
		setNextArc(a);
	}

	Arc(String s) {
		this(s, null, null);
	}

	@Override
	public String toString() {
		return id;
	}

	public boolean hasTarget() {
		return (this.getTarget() != null);
	}

	public Vertex getTarget() {
		return this.targetVertex;
	}

	public void setTarget(Vertex target) {
		this.targetVertex = target;
	}

	public Arc getNextArc() {
		return nextArc;
	}

	public void setNextArc(Arc next) {
		this.nextArc = next;
	}

	// TODO!!! Your Arc methods here!
}