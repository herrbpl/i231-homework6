import java.util.*;

public class GraphTask {

   public static void main (String[] args) {
      GraphTask a = new GraphTask();
      a.run();
      throw new RuntimeException ("Nothing implemented yet!"); // delete this
   }

   public void run() {
      Graph g = new Graph ("G");
      g.createRandomSimpleGraph (6, 9);
      System.out.println (g);

      // TODO!!! Your experiments here
   }


   /**
    * Each Vertex has two navigational pointers. One points to next Vertex in graph
    * Second points first Arc going out from this Vertex
    * @author siimaus
    *
    */
   class Vertex implements Iterator<Vertex>{

      private String id; // identifier of point
      private Vertex next; // next point in vertex list. This is top of stack.
      private Arc first; // first arc from this point. This is top of stack.
      private int info = 0;

      Vertex (String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return (this.next != null);
	}

	@Override
	public Vertex next() {
		// TODO Auto-generated method stub
		return this.next;
	}

	public boolean hasArc() {
		return (this.first != null);
	}
	
	public Arc getArc() {
		return this.first;
	}
      // TODO!!! Your Vertex methods here!
   }


   /**
    * Arc is link between two Vertex instances. Source Vertex is where Arcs originate from.
    * Each Arc has two pointers. First is Vertex to which this Arc links to.
    * Second is next Arc in chain.   
    * @author siimaus
    *
    */
   class Arc implements Iterator<Arc>{

      private String id; // ID of arc
      private Vertex target; // 
      private Arc next;
      private int info = 0;

      Arc (String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }
      
    public boolean hasTarget() {
    	return (this.target != null);
    }
    
    public Vertex getTarget() {
    	return this.target;
    }

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return (this.next != null);
	}

	@Override
	public Arc next() {
		// TODO Auto-generated method stub
		return this.next;
	}

      // TODO!!! Your Arc methods here!
   } 


   class Graph {

      private String id; // Graph identificator
      private Vertex first; // stack of Vertex(es)
      private int info = 0;  // unknown

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph (String s) {
         this (s, null);
      }

      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuffer sb = new StringBuffer (nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      /**
       * Creates new Vertex and adds it to top of Vertex stack.
       * @param vid
       * @return
       * TODO: Replace direct set to setter
       */
      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         return res;
      }

      /**
       * Creates Arc from source Vertex to target Vertex. Created Arc will be placed to top of originating Vertex Arc stack.
       * @param aid - Arc identificator
       * @param from - Source Vertex
       * @param to -- Target Vertex
       * @return - pointer to instance of created Arc. 
       */
      public Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid); // new Arc
         res.next = from.first; // Sets next element to precious top of stack
         from.first = res; // sets TOS to newly created Arc
         res.target = to; // sets arc target to Vertex 'to'
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       * @param n number of vertices added to this graph
       */
      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                  + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                  + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int [info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res [i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException 
               ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j) 
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0) 
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }

      // TODO!!! Your Graph methods here!
   }

} 

