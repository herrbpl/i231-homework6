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
		
		
		
		Iterator<GraphTask.Edge> ei = g.bridges();
		GraphTask.Edge e = null;
		while (ei.hasNext()) {
			e = ei.next();
			System.out.println(e.toString());
		}
	}
	
	@Test //(timeout = 20000)
	public void testFindVertex() {		
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		GraphTask.Vertex v1, v2, v3, vs;
		v1 = g.createVertex("v1");
		v2 = g.createVertex("v2");
		v3 = g.createVertex("v3");
		
		vs = g.findVertex("v1");
		assertNotNull(vs);
		assertEquals("v1", vs.toString());
		
		vs = g.findVertex("v2");
		assertNotNull(vs);
		assertEquals("v2", vs.toString());
		
		vs = g.findVertex("v3");
		assertNotNull(vs);
		assertEquals("v3", vs.toString());
		
		vs = g.findVertex("vc");
		assertNull(vs);
		
		
	}
	
	@Test //(timeout = 20000)
	public void testBridges() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");
		v2 = g.createVertex("v2");
		v3 = g.createVertex("v3");
		
		assertEquals(0, g.bridgeCount());
		g.createEdge(v1, v2);
		g.createEdge(v1, v3);
		assertEquals(2, g.bridgeCount());
		g.createEdge(v2, v3);
		System.out.println(g);
		assertEquals(0, g.bridgeCount());		
		g.removeEdge(v1, v2);
		System.out.println(g);
		assertEquals(2, g.bridgeCount());
		g.removeEdge(v3,v2);
		System.out.println(g);
		assertEquals(1, g.bridgeCount());
		
		g.clear();		
		assertEquals(0, g.bridgeCount());
		
		
		// Case with one cycle!
		
		// Vertexes
		for(int i = 0; i < 5; i++) {
			String s = "v" + String.valueOf(i);			
			v1 = g.createVertex(s);						
		}
		
		// edges
				
		g.createEdge("v1", "v0");
		g.createEdge("v0", "v2");
		g.createEdge("v2", "v1");
		g.createEdge("v0", "v3");
		g.createEdge("v3", "v4");
		
		System.out.println(g);
		
		assertEquals(2, g.bridgeCount());
		
		Iterator<GraphTask.Edge> ei = g.bridges();
		GraphTask.Edge e = null;
		while (ei.hasNext()) {
			e = ei.next();
			System.out.println(e.toString());
		}
		assertTrue(g.isBridge("v0", "v3"));
		assertTrue(g.isBridge("v3", "v4"));		
		assertEquals(1, g.componentsCount());
		
		// remove edge, must have two components left
		g.removeEdge("v3", "v0");
		
		assertFalse(g.isBridge("v0", "v3"));
		assertTrue(g.isBridge("v3", "v4"));		
		assertEquals(2, g.componentsCount());
		
	}
	
	@Test //(timeout = 20000)
	public void testIndexOf() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("root");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");
		assertEquals("Must be equal 2", 2, g.indexOf(v1)); // entered first, bottom of stack
		assertEquals("Must be equal 1", 1, g.indexOf(v2));		
		assertEquals("Must be equal 0", 0, g.indexOf(v3));
	}
	

	@Test //(timeout = 20000)
	public void testRemoveArc1() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("testRemoveArc1");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");
		
		GraphTask.Arc a1 = g.createArc("a_v1_v2", v1, v2);
		GraphTask.Arc a2 = g.createArc("a_v2_v1", v2, v1);
					
		g.createArc("a_v1_v3", v1, v3);
		g.createArc("a_v3_v1", v3, v1);
		GraphTask.Arc a3;
		
		System.out.println(g);
		a3 = g.removeArc(v1, a1);
		System.out.println(a3);
		assertNotNull("Must find arc a1", a3);
		assertEquals("Must be equal", "a_v1_v2", a3.toString());
		System.out.println(g);
		assertNull("Must find null",g.removeArc(v1, a1));
		System.out.println(g);
		assertNotNull("Must find arc a2", g.removeArc(v2, a2));
		System.out.println(g);
		
	}
	

	@Test //(timeout = 20000)
	public void testRemoveArc2() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("testRemoveArc2");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");
		
		g.createArc("a_v1_v2", v1, v2);
		g.createArc("a_v2_v1", v2, v1);
					
		g.createArc("a_v1_v3", v1, v3);
		g.createArc("a_v3_v1", v3, v1);
		
		GraphTask.Arc a;
				
		System.out.println(g);
		a = g.removeArc(v1, v2);		
		System.out.println(g);
		System.out.println(a);
		assertNotNull("Must be not null", a);
		System.out.println(a.toString());
		assertEquals("Must be a_v1_v2", "a_v1_v2", a.toString());
		
		a = g.removeArc(v1, v2);		
		System.out.println(g);
		System.out.println(a);
		assertNull("Must be null", a);
		
		
		a = g.removeArc(v2, v1);		
		System.out.println(g);
		System.out.println(a);
		assertNotNull("Must be not null", a);
		assertEquals("Must be a_v2_v1", "a_v2_v1", a.toString());
		
		a = g.removeArc(v2, v1);		
		System.out.println(g);
		System.out.println(a);
		assertNull("Must be null", a);
		
		System.out.println(g);
		
		
		
	}
	
	@Test //(timeout = 20000)
	public void testRemoveVertex() {
		GraphTask gt = new GraphTask();
		GraphTask.Graph g = gt.createGraph("testRemoveArc2");
		GraphTask.Vertex v1, v2, v3;
		v1 = g.createVertex("v1");

		v2 = g.createVertex("v2");

		v3 = g.createVertex("v3");
		
		g.createArc("a_v1_v2", v1, v2);
		g.createArc("a_v2_v1", v2, v1);
					
		g.createArc("a_v1_v3", v1, v3);
		g.createArc("a_v3_v1", v3, v1);
		System.out.println(g);
		
		g.removeVertex(v1);
		assertEquals(2, g.vertexCount());
		assertEquals(2, g.componentsCount());
		assertEquals(0, g.bridgeCount());
		System.out.println(g);
		
	}
		
	
}
