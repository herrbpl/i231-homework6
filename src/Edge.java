
/**
 * Edge class. This created for convinience of UndirectedGraph so that edges can
 * be iterated, for example to list bridges.
 * 
 * @author siimaus
 *
 * @param <T>
 * @param <A>
 */
public class Edge<T, A> {
	protected IVertex<T, A> v1, v2;
	protected IArc<T, A> a1, a2;
	
	Edge(IVertex<T, A>v1, IVertex<T, A> v2, IArc<T, A> a1, IArc<T, A> a2 ) {
		this.v1 = v1;
		this.v2 = v2;
		this.a1 = a1;
		this.a2 = a2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		if (!(obj instanceof Edge<?, ?>)) {
			return false;
		}
		Edge<T, A> o2 = (Edge<T, A>) obj;

		if ((o2.v1 == this.v1 && o2.v2 == this.v2) 
			&& (o2.a1 == this.a1 && o2.a2 == this.a2)
				) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if Edge is parallel to other Edge
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isParallel(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		if (!(obj instanceof Edge<?, ?>)) {
			return false;
		}
		Edge<T, A> o2 = (Edge<T, A>) obj;

		// if edge is between same vertexes, then it is equal.	

		if ((o2.v1 == this.v1 && o2.v2 == this.v2) || (o2.v1 == this.v2 && o2.v2 == this.v1)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "e{" + v1.toString() + "_" + v2.toString() + "}";
	}
}
