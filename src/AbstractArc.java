import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstractArc<T, A> implements IArc<T, A> {

	protected A data;
	protected IVertex<T,A> targetVertex;
	protected IArc<T,A> nextArc;
	
	AbstractArc (A data, IVertex<T,A> target, IArc<T,A> next  ) {
		this.data = data;
		this.targetVertex = target;
		this.nextArc = next;
	}
	
	AbstractArc (A data) {
		this(data, null, null);
	}
	
	@Override
	public void setValue(A newValue) {
		// TODO Auto-generated method stub
		this.data = newValue;

	}

	@Override
	public A getValue() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public Iterator<IArc<T, A>> iterator() {
		return new ArcIterator<T,A>(this);
	}

	@Override
	public IVertex<T,A> getTarget() {
		// TODO Auto-generated method stub
		return this.targetVertex;
	}

	@Override
	public int arcLength() {
		// TODO Auto-generated method stub
		int length = 0;
		
		Iterator<IArc<T,A>> iterator = this.iterator();
		while(iterator.hasNext()) {
			length++;
			iterator.next();
		}
		
		return length;
	}
	
	protected class ArcIterator<T,A>  implements Iterator<IArc<T,A>> {

		private IArc<T,A> current;
		
		public ArcIterator(IArc<T,A> first) {
			current = first;
			
		}
		
		
		@Override
		public boolean hasNext() {			
			return (current != null);
		}

		@Override
		public IArc<T, A> next() {
			if (!hasNext()) throw new  NoSuchElementException();
			
			IArc<T,A> result;
			result = current;	
			
			// I think i will not expose internal moving mecanisms through interface, so typecasting here.
			current = ((AbstractArc<T,A>)current).nextArc;						
			return result;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@Override
	public String toString() {		
		return this.getValue().toString();
	}

	@Override
	public void setTarget(IVertex<T,A> target) {
		this.targetVertex = target;
		
	}

	@Override
	public void setNextArc(IArc<T, A> next) {
		if (next == this) throw new IllegalArgumentException("Cannot set next arc to itself!");
		this.nextArc = next;		
	}

	@Override
	public IArc<T, A> getNextArc() {
		// TODO Auto-generated method stub
		return this.nextArc;
	}
	
}
