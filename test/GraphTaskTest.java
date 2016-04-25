import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

/** Testklass.
 * @author jaanus
 */
public class GraphTaskTest {

//   @Test (timeout=20000)
//   public void test1() { 
//      GraphTask.main (null);
//      assertTrue ("There are no tests", true);
//   }

   @Test (timeout=20000)
   public void testVertexCount() {
	  GraphTask gt = new GraphTask();
      Graph g = gt.createGraph("root");
      assertEquals(0, g.vertexCount());
      g.createVertex("1");
      assertEquals(1, g.vertexCount());
      g.createVertex("2");
      assertEquals(2, g.vertexCount());
      g.createVertex("3");
      assertEquals(3, g.vertexCount());
      
      
   }
  
   @Test (timeout=20000)
   public void testArc() {
	  IArc<String, String> a = new AbstractArc<>("Test");
	  IArc<String, String> b = new AbstractArc<>("Test2");
	  
	  
	  AbstractArc<String,String> x = ((AbstractArc<String,String>)a);
	  AbstractArc<String,String> y = ((AbstractArc<String,String>)b);
	  AbstractArc<String,String> z = new AbstractArc<String, String>("test3");
	  
	  x.setNextArc(y);
	  y.setNextArc(z);
	  System.out.println(x.arcLength());
     
	 Iterator<IArc<String, String>> ai = x.iterator();
	 
	 
	 if (ai != null) {
		 IArc<String, String> n;
		 while (ai.hasNext()) {
			 n = ai.next();
			 
			 System.out.println(n.toString());
		 }
		 
	 }
	
   }

   @Test //(timeout=20000)
   public void testGraph() {
	   AbstractGraph<String,String> g = new AbstractGraph<String,String>("root");
	   IVertex<String, String> v1,v2, v3;
	   
	   v1 = g.createVertex("v1");
	   v2 = g.createVertex("v2");
	   v3 = g.createVertex("v3");
	   
	   g.createArc("a" + v1.toString()+"_"+v2.toString(), v1, v2);
	   g.createArc("a" + v1.toString()+"_"+v3.toString(), v1, v3);
	   g.createArc("a" + v3.toString()+"_"+v2.toString(), v3, v2);
	   
	   System.out.println(g);
	   System.out.println(g.getAdjacencyMatrixString());
	   assertEquals("Must be equal", 3, g.verticesCount());
	   
	   g.clear();
	   
	   assertEquals("Must be equal", 0, g.verticesCount());
	   System.out.println(g);
	   System.out.println(g.getAdjacencyMatrixString());
   }
}

