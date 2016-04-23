
import java.util.*;

public class GraphTask {

	IGraph<String, Integer> gg;
	
	public static void main(String[] args) {
		GraphTask a = new GraphTask();
		a.run();
		throw new RuntimeException("Nothing implemented yet!"); // delete this
	}

	public void run() {
		Graph g = new Graph("G");
		g.createRandomSimpleGraph(6, 9);
		System.out.println(g);
		System.out.println(g.getAdjMatrix());
		// TODO!!! Your experiments here
	}

	public Graph createGraph(String gid) {
		gg = new IGraph<String,Integer>() {
			
			@Override
			public Iterator<IVertex<String>> vertices() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IVertex<String> createVertex(String data) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IArc<Integer> createArc(Integer data, IVertex<String> from, IVertex<String> to) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int verticesCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void createEdge(Integer data, IVertex<String> from, IVertex<String> to)
					throws IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		gg.createVertex("Test");
		
		Iterator<IVertex<String>> iterator = gg.vertices();
		
		if (iterator != null) {
			while(iterator.hasNext()) {
				IVertex<String> v = iterator.next();
				
				iterator.next();
			}
		}
		
		
		return new Graph(gid);
	}

	
	//http://www.geeksforgeeks.org/bridge-in-a-graph/
	public class Bridge {
		private int[] pre;
		private int[] low;
		Bridge(IGraph<String, String> G) {
			pre = new int[G.verticesCount()];
			low = new int[G.verticesCount()];
			
			for (int v = 0; v < G.verticesCount(); v++)
	            low[v] = -1;
	        for (int v = 0; v < G.verticesCount(); v++)
	            pre[v] = -1;			
		}
	}
	
	
	

}
