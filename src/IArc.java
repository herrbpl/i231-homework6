
/**
 * Interface for Arc connecting two nodes.
 * @author siimaus
 *
 */
interface IArc<T,A> extends INode<A>, Iterable<IArc<T,A>>{
	/**
	 * Gets target of this Arc
	 * @return IVertex<T> indicated by this arc
	 */
	IVertex<T,A> getTarget();
	
	/**
	 * Sets target of Arc. This also should only be set from Graph itself.
	 * @param target
	 */
	void setTarget(IVertex<T,A> target);
	
	/**
	 * Sets next arc in chain. Actually, this operation should never executed outside of graph to avoid structure corruption
	 * @param next
	 */
	void setNextArc(IArc<T,A> next);
	
	/**
	 * Next Arc in chain. This should not be exposed probably.
	 * @return
	 */
	IArc<T,A> getNextArc();
	
	/**
	 * Returns total length of arc from this point on. And this should not be exposed form interface.
	 * @return
	 */
	int arcLength();
	
}