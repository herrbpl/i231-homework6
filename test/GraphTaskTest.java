import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

/**
 * Testklass.
 * 
 * @author jaanus
 */
public class GraphTaskTest {

	// @Test (timeout=20000)
	// public void test1() {
	// GraphTask.main (null);
	// assertTrue ("There are no tests", true);
	// }

	@Test(timeout = 20000)
	public void testVertexCount() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		assertEquals(0, g.vertexCount());
		g.createVertex("1");
		assertEquals(1, g.vertexCount());
		g.createVertex("2");
		assertEquals(2, g.vertexCount());
		g.createVertex("3");
		assertEquals(3, g.vertexCount());

	}

	@Test //(timeout = 20000)
	public void testArcIterator() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");

		g.createArc("a_v1_v2", v1, v2);
		g.createArc("a_v1_v3", v1, v3);
		g.createArc("a_v3_v2", v3, v2);
		
		StringBuilder sb = new StringBuilder();
		
		Iterator<GraphTask.Arc> iter = v1.getOutArcs();
		GraphTask.Arc a = null;
		if (!iter.hasNext()) {
			System.out.println("WTF!");
		}
		while( iter.hasNext()) {
			a = iter.next();			
			sb.append(a.toString());
		}
		assertEquals("Must be equal", "a_v1_v3a_v1_v2", sb.toString());
		

	}
	
	@Test //(timeout = 20000)
	public void testConnectedComponents() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");

		assertEquals("Components count must be 3", 3, g.componentsCount());
				
		g.createArc("a_v1_v2", v1, v2);
		g.createArc("a_v2_v1", v2, v1);
		
		assertEquals("Components count must be 2", 2, g.componentsCount());		
		g.createArc("a_v1_v3", v1, v3);
		g.createArc("a_v3_v1", v3, v1);
		
		assertEquals("Components count must be 1", 1, g.componentsCount());		
		System.out.println(g);
		System.out.println(g.getAdjMatrix());
		g.findBridges();
	}
}
