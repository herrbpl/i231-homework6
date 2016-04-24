
public interface INode<T>{
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
