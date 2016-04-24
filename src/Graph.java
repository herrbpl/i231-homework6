import java.util.Iterator;





public class Graph {

		private String id; // Graph identificator
		private Vertex first; // stack of Vertex(es)
		private int info = 0; // unknown
				

		Graph(String s, Vertex v) {
			id = s;
			first = v;			
		}

		Graph(String s) {
			this(s, null);			
		}

		@Override
		public String toString() {
			String nl = System.getProperty("line.separator");
			StringBuffer sb = new StringBuffer(nl);
			sb.append(id);
			sb.append(nl);
			Vertex v = first;
			while (v != null) {
				sb.append(v.toString());
				sb.append(" -->");
				Arc a = v.getFirstArc();
				while (a != null) {
					sb.append(" ");
					sb.append(a.toString());
					sb.append(" (");
					sb.append(v.toString());
					sb.append("->");
					sb.append(a.getTarget().toString());
					sb.append(")");
					a = a.getNextArc();
				}
				sb.append(nl);
				v = v.getNextVertex();
			}
			return sb.toString();
		}

		/**
		 * Returns count of Vertexes in graph
		 * 
		 * @return
		 */
		public int vertexCount() {
			if (this.first == null)
				return 0;
			return this.first.vertexCount();
		}

		/**
		 * Creates new Vertex and adds it to top of Vertex stack.
		 * 
		 * @param vid
		 *            - vertex identification
		 * @return TODO: Replace direct set to setter
		 */
		public Vertex createVertex(String vid) {
			Vertex res = new Vertex(vid);
			res.setNextVertex(first);
			first = res;
			return res;
		}

		/**
		 * Creates Arc from source Vertex to target Vertex. Created Arc will be
		 * placed to top of originating Vertex Arc stack.
		 * 
		 * @param aid
		 *            - Arc identificator
		 * @param from
		 *            - Source Vertex
		 * @param to
		 *            -- Target Vertex
		 * @return - pointer to instance of created Arc.
		 */
		public Arc createArc(String aid, Vertex from, Vertex to) {
			Arc res = new Arc(aid); // new Arc
			res.setNextArc(from.getFirstArc()); // Sets next element to precious
												// top of stack
			from.setFirstArc(res); // sets TOS to newly created Arc
			res.setTarget(to); // sets arc target to Vertex 'to'
			return res;
		}

		/**
		 * Create a connected undirected random tree with n vertices. Each new
		 * vertex is connected to some random existing vertex.
		 * 
		 * @param n
		 *            number of vertices added to this graph
		 */
		public void createRandomTree(int n) {
			if (n <= 0)
				return;
			Vertex[] varray = new Vertex[n];
			for (int i = 0; i < n; i++) {
				varray[i] = createVertex("v" + String.valueOf(n - i));
				if (i > 0) {
					int vnr = (int) (Math.random() * i);
					createArc("a" + varray[vnr].toString() + "_" + varray[i].toString(), varray[vnr], varray[i]);
					createArc("a" + varray[i].toString() + "_" + varray[vnr].toString(), varray[i], varray[vnr]);
				} else {
				}
			}
		}

		/**
		 * Create an adjacency matrix of this graph. Side effect: corrupts info
		 * fields in the graph
		 * 
		 * @return adjacency matrix
		 */
		public int[][] createAdjMatrix() {
			info = 0;
			Vertex v = first;
			while (v != null) {
				v.setInfo(info++);
				v = v.getNextVertex();
			}
			int[][] res = new int[info][info];
			v = first;
			while (v != null) {
				int i = v.getInfo();
				Arc a = v.getFirstArc();
				while (a != null) {
					int j = a.getTarget().getInfo();
					res[i][j]++;
					a = a.getNextArc();
				}
				v = v.getNextVertex();
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
		public String getAdjMatrix() {
			int[][] mat = this.createAdjMatrix();
			StringBuilder sb = new StringBuilder();
			String[] columns = new String[this.vertexCount()];
			
			Vertex v = this.first;
			 int pos = -1; int maxLen = 0; String sep = ""; int totLen = 0;
			while (v != null) {				
				
				pos++;
				columns[pos] = " "+v.toString()+" ";
				maxLen = Math.max(columns[pos].length(), maxLen);
				sb.append(sep).append(columns[pos]); // add to header
				sep = "|";
				v = v.getNextVertex();
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

		/**
		 * Create a connected simple (undirected, no loops, no multiple arcs)
		 * random graph with n vertices and m edges.
		 * 
		 * @param n
		 *            number of vertices
		 * @param m
		 *            number of edges
		 */
		public void createRandomSimpleGraph(int n, int m) {
			if (n <= 0)
				return;
			if (n > 2500)
				throw new IllegalArgumentException("Too many vertices: " + n);
			if (m < n - 1 || m > n * (n - 1) / 2)
				throw new IllegalArgumentException("Impossible number of edges: " + m);
			first = null;
			createRandomTree(n); // n-1 edges created here
			Vertex[] vert = new Vertex[n];
			Vertex v = first;
			int c = 0;
			while (v != null) {
				vert[c++] = v;
				v = v.getNextVertex();
			}
			int[][] connected = createAdjMatrix();
			int edgeCount = m - n + 1; // remaining edges
			while (edgeCount > 0) {
				int i = (int) (Math.random() * n); // random source
				int j = (int) (Math.random() * n); // random target
				if (i == j)
					continue; // no loops
				if (connected[i][j] != 0 || connected[j][i] != 0)
					continue; // no multiple edges
				Vertex vi = vert[i];
				Vertex vj = vert[j];
				createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
				connected[i][j] = 1;
				createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
				connected[j][i] = 1;
				edgeCount--; // a new edge happily created
			}
		}

		// TODO!!! Your Graph methods here!
	}