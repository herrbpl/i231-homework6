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
	  a.setNextArc(b);
	  System.out.println(a.arcLength());
     
	 Iterator<IArc<String, String>> ai = a.iterator();
	 
	 
	 if (ai != null) {
		 IArc<String, String> n;
		 while (ai.hasNext()) {
			 n = ai.next();
			 n.getTarget().getValue();
			 n.toString();
		 }
		 
	 }
	
   }
   
}

