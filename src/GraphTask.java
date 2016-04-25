
import java.util.*;

public class GraphTask {

	/**
	 * Data structure to hold Edge (Pair of vertex 1 and vertex 2)
	 * 
	 * @author siimaus
	 *
	 */
	public class Edge {
		protected Vertex a, b;

		Edge(Vertex from, Vertex to) {
			if (a == null || b == null) {
				throw new IllegalArgumentException("Edge vertex cannot be null");
			}
			this.a = from;
			this.b = to;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("e{%s->%s}", a.id, b.id);
		}
	}

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
		return new Graph(gid);
	}

	/**
	 * Each Vertex has two navigational pointers. One points to next Vertex in
	 * graph Second points first Arc going out from this Vertex
	 * 
	 * @author siimaus
	 *
	 */
	class Vertex {

		private String id; // identifier of point
		private Vertex nextVertex; // next point in vertex list. This is top of
									// stack.
		private Arc firstArc; // first arc from this point. This is top of
								// stack.
		private int info = 0;
		private int componentId = -1; // indicates to which component vertex
										// belongs to. -1 - not calculated.

		Vertex(String id, Vertex v, Arc e) {
			if (id == null) {
				throw new IllegalArgumentException("Vertex id must not be null!");
			}
			this.id = id;
			this.nextVertex = v;
			this.firstArc = e;
		}

		Vertex(String s) {
			this(s, null, null);
		}

		@Override
		public String toString() {
			return id;
		}

		/**
		 * Returns size of Vertex stack from this Vertex.
		 * 
		 * @return
		 */
		public int vertexCount() {
			int count = 0;
			Vertex v = this;
			while (v != null) {
				count++;
				v = v.nextVertex;
			}
			return count;
		}

		/**
		 * Gets Iterator over outgoing Arcs, giving us adjacent vertexes.
		 * 
		 * @return Iterator over outgoing Arcs
		 */

		public Iterator<Arc> getOutArcs() {
			if (this.firstArc == null) {
				return new Iterator<Arc>() {

					@Override
					public Arc next() {
						// TODO Auto-generated method stub
						throw new NoSuchElementException("No such element!");
					}

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return false;
					}
				};

			} else {
				return this.firstArc.iterator();
			}

		}

	}

	/**
	 * Arc is link between two Vertex instances. Source Vertex is where Arcs
	 * originate from. Each Arc has two pointers. First is Vertex to which this
	 * Arc links to. Second is next Arc in chain.
	 * 
	 * @author siimaus
	 *
	 */
	class Arc implements Iterable<Arc> {

		private String id; // ID of arc
		private Vertex targetVertex; // Vertex this Arc points to.
		private Arc nextArc; // next Arc in stack
		private int info = 0;

		Arc(String s, Vertex v, Arc a) {
			id = s;
			this.targetVertex = v;
			this.nextArc = a;
		}

		Arc(String s) {
			this(s, null, null);
		}

		@Override
		public String toString() {
			return id;
		}

		@Override
		public Iterator<Arc> iterator() {
			final Arc self = this;
			// TODO Auto-generated method stub
			return new Iterator<Arc>() {

				private Arc current = self;

				@Override
				public Arc next() {
					if (!hasNext()) {
						throw new NoSuchElementException("Next element not found!");
					}
					// TODO Auto-generated method stub
					Arc result = current;
					current = current.nextArc;
					return result;
				}

				@Override
				public boolean hasNext() {
					return (current != null);
				}

			};
		}

		// TODO!!! Your Arc methods here!
	}

	public class Graph {

		private String id; // Graph identificator
		private Vertex first; // stack of Vertex(es)
		private int info = 0; // unknown

		private int connectedComponents = -1;
		private Vertex[] components = new Vertex[0]; // Components in graph
		private int componentVertexIndex[] = new int[0]; // vertexes in each
															// component

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
				Arc a = v.firstArc;
				while (a != null) {
					sb.append(" ");
					sb.append(a.toString());
					sb.append(" (");
					sb.append(v.toString());
					sb.append("->");
					sb.append(a.targetVertex.toString());
					sb.append(")");
					a = a.nextArc;
				}
				sb.append(nl);
				v = v.nextVertex;
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
			res.nextVertex = first;
			first = res;
			connectedComponents = -1;
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
			res.nextArc = from.firstArc; // Sets next element to precious
											// top of stack
			from.firstArc = res; // sets TOS to newly created Arc
			res.targetVertex = to; // sets arc target to Vertex 'to'
			connectedComponents = -1;
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
				v.info = info++;
				v = v.nextVertex;
			}
			int[][] res = new int[info][info];
			v = first;
			while (v != null) {
				int i = v.info;
				Arc a = v.firstArc;
				while (a != null) {
					int j = a.targetVertex.info;
					res[i][j]++;
					a = a.nextArc;
				}
				v = v.nextVertex;
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
		 * @return Adj Matrix as string TODO: remove double loops
		 */
		public String getAdjMatrix() {
			int[][] mat = this.createAdjMatrix();
			StringBuilder sb = new StringBuilder();
			String[] columns = new String[this.vertexCount()];

			Vertex v = this.first;
			int pos = -1;
			int maxLen = 0;
			String sep = "";
			int totLen = 0;
			while (v != null) {

				pos++;
				columns[pos] = " " + v.toString() + " ";
				maxLen = Math.max(columns[pos].length(), maxLen);
				sb.append(sep).append(columns[pos]); // add to header
				sep = "|";
				v = v.nextVertex;
			}

			sb.insert(0, padLeft("|", maxLen + 1, " ")).append("\n");
			totLen = sb.length();
			sb.append(padLeft("", totLen, "-")).append("\n");

			for (int i = 0; i < mat.length; i++) {
				sep = "";
				sb.append(columns[i]).append("|");
				for (int j = 0; j < mat.length; j++) {
					String val = String.valueOf(mat[i][j]);
					sb.append(sep).append(padLeft(val, columns[i].length() - val.length(), " ")).append(" ");
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
				v = v.nextVertex;
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

		/**
		 * get graph connected components
		 */
		private void findGraphComponents() {
			final Graph self = this;
			Object i = new Object() {
				Graph g = self;

				@Override
				public String toString() {
					// TODO Auto-generated method stub
					reset();
					connect();
					return "";
				}

				/**
				 * Reset graph info
				 */
				void reset() {
					int info = 0;
					Vertex v = g.first;
					while (v != null) {
						v.info = info++;
						v.componentId = -1;
						v = v.nextVertex;
					}
					g.connectedComponents = 0;
					g.components = new Vertex[info + 1];
					g.componentVertexIndex = new int[info + 1];
				}

				/**
				 * Searches for connected nodes.
				 * 
				 * @seealso <a href=
				 *          "http://algs4.cs.princeton.edu/41graph/CC.java.html">
				 *          http://algs4.cs.princeton.edu/41graph/CC.java.html
				 *          </a>
				 */
				void connect() {
					Vertex v = g.first;

					while (v != null) {
						if (v.componentId == -1) {
							dfs(v);
							g.connectedComponents++; // new component
						}
						v = v.nextVertex;
					}

				}

				/**
				 * DFS searching connected components
				 * 
				 * @param v
				 * @see <a href=
				 *      "http://algs4.cs.princeton.edu/41graph/CC.java.html">
				 *      http://algs4.cs.princeton.edu/41graph/CC.java.html</a>
				 */
				void dfs(Vertex v) {

					v.componentId = g.connectedComponents; // set vertex
															// component id
					g.componentVertexIndex[g.connectedComponents]++; // number
																		// of
																		// vertices
																		// in
																		// component

					// previous count
					if (g.connectedComponents > 0) {
						g.components[g.componentVertexIndex[g.connectedComponents - 1]
								+ g.componentVertexIndex[g.connectedComponents]] = v;
					} else {
						g.components[g.componentVertexIndex[g.connectedComponents]] = v;
					}

					Iterator<Arc> iter = v.getOutArcs();
					while (iter.hasNext()) {
						Vertex next = iter.next().targetVertex;

						// if there actually is target vertex..
						if (next != null && next.componentId == -1) {
							dfs(next);
						}
					}
				}

			};
			i.toString();
		}

		/**
		 * Finds number of connected components in graph.
		 * 
		 * @return Number of connected components in graph
		 */
		public int componentsCount() {
			if (this.connectedComponents < 0) {
				findGraphComponents();
			}
			return this.connectedComponents;
		}

		/**
		 * Find bridges in Graph. We expect graph to be undirected.
		 * 
		 * @see <a href=
		 *      "http://algs4.cs.princeton.edu/41graph/Bridge.java.html">http://
		 *      algs4.cs.princeton.edu/41graph/Bridge.java.html</a>
		 */
		public void findBridges() {
			final Graph self = this;
			Object i = new Object() {
				Graph g = self;

				int vertexCount = g.vertexCount();
				int[] pre = new int[vertexCount];
				int[] low = new int[vertexCount];
				int count = 0;// counter of dfs execution cycles.

				@Override
				public String toString() {
					reset();
					search();
					return "";
				}

				void reset() {					
					int info = 0;
					Vertex v = g.first;
					while (v != null) {
						v.info = info++;
						low[v.info] = -1;
						pre[v.info] = -1;
						v = v.nextVertex;						
					}										
				}
				/**
				 * Search for bridges
				 */
				void search() {
					Vertex v = g.first;
					while (v != null) {	
						if (pre[v.info] == -1) {
							dfs(v,v);
						}
						v = v.nextVertex;
					}								       
				}
				/**
				 * DFS called in search mechanism recursively.
				 * @param u
				 * @param v
				 */
				void dfs(Vertex u, Vertex v) {
					pre[v.info] = count++; // increased cycle
					low[v.info] = pre[v.info]; // For this vertex, 
					
					// for each outgoing arc
					Iterator<Arc> iter = v.getOutArcs();
					while (iter.hasNext()) {
						Vertex next = iter.next().targetVertex;
						// if there actually is target vertex..
						if (next != null) {
						   if (pre[next.info] == -1) { // not visited yet
							   dfs(v, next); // recursive, now v is parent and next is vertex examined
							   low[v.info] = Math.min(low[v.info], low[next.info]); // update minimal steps required to reach v. dfs might have found another, better way.
							   
							   if (low[next.info] == pre[next.info]) { // found a bridge.
								   System.out.println(v.id+"-"+next.id + " is a bridge!");
							   }
							   
						   } else { // next is already visited by something. 
							   
							   if (next.info != u.info) { // if this vertex is not where we reached to v from
								   low[v.info] = Math.min(low[v.info], pre[next.info]); // set minimal number of steps know required to reach v
							   }
						   }
							
						}
					}
					
				}
				
			};
			i.toString();

		}

	}

}
