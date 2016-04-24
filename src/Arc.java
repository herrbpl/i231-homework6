



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