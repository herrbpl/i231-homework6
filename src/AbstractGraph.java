import java.util.Iterator;

public class AbstractGraph<T,A> implements IGraph<T, A> {

	protected String id;
	protected IVertex<T, A> firstVertex;
	protected int vertexCount = 0;
	
	public AbstractGraph(String id) {
		this.id = id;
	}
	
	@Override
	public Iterator<IVertex<T, A>> vertices() {
		// TODO Auto-generated method stub
		return ((AbstractVertex<T,A>)firstVertex).iterator();
	}

	@Override
	public IVertex<T, A> createVertex(T data) {
		// TODO Auto-generated method stub
		AbstractVertex<T, A> v = new AbstractVertex<T, A>(data);
		v.vertexId = this.vertexCount++;
		v.nextVertex = this.firstVertex;
		this.firstVertex = v;
		return v;
	}
	
	@Override
	public IArc<T, A> createArc(A data, IVertex<T, A> from, IVertex<T, A> to) throws IllegalArgumentException {
		
		if (!existsVertex(from)) throw new IllegalArgumentException("Vertex 'from' does not exist in graph");
		if (!existsVertex(to)) throw new IllegalArgumentException("Vertex 'to' does not exist in graph");
		
		AbstractArc<T,A> arc = new AbstractArc<T, A>(data);		
		arc.nextArc = ((AbstractVertex<T,A>)from).firstArc;		
		((AbstractVertex<T,A>)from).firstArc = arc;		
		arc.targetVertex = to;		
		return arc;
	}	

	@Override
	public int verticesCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if (this.firstVertex == null) return count;
		
		Iterator<IVertex<T, A>> i = this.vertices();
		while(i.hasNext()) {
			count++;
			i.next();
		}		
		
		return count;
	}

	public boolean existsVertex(IVertex<T,A> v) {
		if (v == null) return false;
		if (this.firstVertex == null) return false;
		
		Iterator<IVertex<T, A>> i = this.vertices();
		IVertex<T,A> e = null;
		
		while(i.hasNext()) {
			
			e = i.next();
			if (v == e) return true;
		}		
		return false;
	}
	
	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer(nl);
		sb.append(id);
		sb.append(nl);
		
		Iterator<IVertex<T, A>> i = this.vertices();
		
		Iterator<IArc<T, A>> j = null;
		IArc<T,A> a = null;
		
		IVertex<T,A> v = null;
		
		while(i.hasNext()) {
			v = i.next();
			
			sb.append(v.toString());
			sb.append(" -->");
			
			j = v.getOutgoingArcs();
			
			if (j == null) {
				System.out.println("WTF!?");
			}
			
			while(j != null && j.hasNext()) {
				a = j.next();				
				
				sb.append(" ");
				sb.append(a.toString());
				sb.append(" (");
				sb.append(v.toString());
				sb.append("->");
				sb.append(a.getTarget().toString());
				sb.append(")");
				
			}
			sb.append(nl);
		}
		
		return sb.toString();
				
	}
	
	/**
	 * Creates Adjacency matrix.   
	 * @return Adjacency matrix	 * 
	 */
	public int[][] getAdjacencyMatrix() {
	
		// reindexing is required!
		Iterator<IVertex<T, A>> i = this.vertices();
		IVertex<T,A> v = null;
		vertexCount = 0;
		while(i.hasNext()) {			
			v = i.next();			
			((AbstractVertex<T,A>)v).vertexId = vertexCount;
			vertexCount++;
		}
		
		
		
		int[][] res = new int[this.vertexCount][this.vertexCount]; // Dangerous! could have changed by something
		
		
				
		Iterator<IArc<T, A>> j = null;
		IArc<T,A> a = null;
		
		i = this.vertices();
		
		int ii, jj;
		
		while(i.hasNext()) {
			v = i.next();
			
			ii = ((AbstractVertex<T,A>)v).vertexId;
			System.out.printf("VERTEX[ %s %d ]\n", v, ii );
			j = v.getOutgoingArcs();
			
						
			while(j != null && j.hasNext()) {
			
				a = j.next();
				if (a.getTarget() != null) {
				   jj = ((AbstractVertex<T,A>)a.getTarget()).vertexId;
				   res[ii][jj]++;
				}
				
				
			}
		}
				
		return res;
	}
	
	private String padLeft(String s, int length, String pad) {
		while (s.length() < length) {
			s = pad + s;
		}
		return s;
	}
	
	
	/**
	 * Returns Adj Matrix as nicely formatted string
	 * 
	 * @return Adj Matrix as string
	 * TODO: remove double loops
	 */
	public String getAdjacencyMatrixString() {
		int[][] mat = getAdjacencyMatrix();
		StringBuilder sb = new StringBuilder();
		String[] columns = new String[this.vertexCount]; // dangerous
		

		Iterator<IVertex<T, A>> iter = this.vertices();
		
		int pos = -1; int maxLen = 0; String sep = ""; int totLen = 0;
		IVertex<T, A> v = null;
		
		while (iter.hasNext()) {				
			v = iter.next();
			
			pos++;
			columns[pos] = " "+v.toString()+" ";
			maxLen = Math.max(columns[pos].length(), maxLen);
			sb.append(sep).append(columns[pos]); // add to header
			sep = "|";			
		}
		
		sb.insert(0,  padLeft("|", maxLen+1, " ")).append("\n");
		totLen = sb.length();
		sb.append(padLeft("", totLen, "-")).append("\n");
		
		for (int i = 0; i < mat.length; i++) {
			sep = "";
			sb.append(columns[i]).append("|");
			for (int j = 0; j < mat.length; j++) {
				String val = String.valueOf(mat[i][j]);
				sb.append(sep).append(padLeft(val, columns[i].length() - val.length(), " " )).append(" ");
				sep = "|";
			}
			sb.append("\n");				
		}
		

		return sb.toString();
	}

	
}
