import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstractVertex<T,A> implements IVertex<T,A> {

	protected T data;
	protected IVertex<T,A> nextVertex;
	protected IArc<T,A> firstArc;

	AbstractVertex(T data, IVertex<T,A> nextVertex, IArc<T,A> firstArc ) {
		this.data = data;
		this.nextVertex = nextVertex;
		this.firstArc = firstArc;
	}
	
	AbstractVertex(T data) {
		this(data, null, null);
	}
	
	@Override
	public void setValue(T newValue) {
		// TODO Auto-generated method stub
		data = newValue;
	}

	@Override
	public T getValue() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public Iterator<IVertex<T, A>> iterator() {
		// TODO Auto-generated method stub
		return new VertexIterator<T,A>(this);
	}

	@Override
	public Iterator<IVertex<T, A>> getAdjacent() {		
		return new VertexIterator<T,A>(this.firstArc);
	}

	@Override
	public int degree() {
		if (this.firstArc == null) return 0;
		return this.firstArc.arcLength();
	}

	
	protected class VertexIterator<T ,A>  implements Iterator<IVertex<T,A>> {

		private IVertex<T,A> currentVertex = null;
		private IArc<T,A> currentArc = null;
		private int iteratorType = 0;
		
		public VertexIterator(IVertex<T,A> first) {
			iteratorType = 1; 
			currentVertex = first;			
		}		
		
		public VertexIterator(IArc<T,A> first) {
			iteratorType = 2; 
			currentArc = first;			
		}
		
		@Override
		public boolean hasNext() {	
			if (iteratorType == 1) return (currentVertex != null); 
			if (iteratorType == 2) return (currentArc != null);
			return false;
		}

		@Override
		public IVertex<T, A> next() {
			if (!hasNext()) throw new  NoSuchElementException();
									
			IVertex<T,A> result = null;
			
			if (iteratorType == 1) {
			
				result = currentVertex;						
				currentVertex = ((AbstractVertex<T,A>)currentVertex).nextVertex;
			} else if (iteratorType == 2) {
				result = currentArc.getTarget();
				// I think I should remove this from interface, thus i used type casting here.
				currentArc = ((AbstractArc<T,A>)currentArc).nextArc; 
				
			} else {
				throw new RuntimeException("Invalid iterator type!");
			}
			return result;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getValue().toString();
	}
	
}
