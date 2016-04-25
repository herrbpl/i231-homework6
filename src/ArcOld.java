



/**
 * Arc is link between two Vertex instances. Source Vertex is where Arcs
 * originate from. Each Arc has two pointers. First is Vertex to which this Arc
 * links to. Second is next Arc in chain.
 * 
 * @author siimaus
 *
 */
class ArcOld {

	private String id; // ID of arc
	private VertexOld targetVertex; // Vertex this Arc points to.
	private ArcOld nextArc; // next Arc in stack
	private int info = 0;

	ArcOld(String s, VertexOld v, ArcOld a) {
		id = s;
		setTarget(v);
		setNextArc(a);
	}

	ArcOld(String s) {
		this(s, null, null);
	}

	@Override
	public String toString() {
		return id;
	}

	public boolean hasTarget() {
		return (this.getTarget() != null);
	}

	public VertexOld getTarget() {
		return this.targetVertex;
	}

	public void setTarget(VertexOld target) {
		this.targetVertex = target;
	}

	public ArcOld getNextArc() {
		return nextArc;
	}

	public void setNextArc(ArcOld next) {
		this.nextArc = next;
	}

	// TODO!!! Your Arc methods here!
}