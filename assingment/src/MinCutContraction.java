import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 04/10/2016.
 * Minimum cut contraction algorithm. Accepted. O(N ^ 2) X M
 */
public class MinCutContraction
{
    /**
     * Edge class
     */
    static class Edge
    {
        int u, v;
        Edge(int u, int v)
        {
            this.u = u;
            this.v = v;
        }
    }

    /**
     *
     * @author gouthamvidyapradhan
     * Class to represent UnionFind Disjoint Set
     *
     */
    private static class UnionFind
    {
        static int[] p;
        static int[] rank;
        static int numOfDisjoinSet;
        /**
         * Initialize with its same index as its parent
         */
        public static void init()
        {
            for(int i=0, l = p.length; i<l; i++)
                p[i] = i;
            numOfDisjoinSet = p.length - 1; //0 is not a valid vertex. Ignore 0
        }
        /**
         * Find the representative vertex
         * @param i
         * @return
         */
        private static int findSet(int i)
        {
            if(p[i] != i)
                p[i] = findSet(p[i]);
            return p[i];
        }
        /**
         * Perform union of two vertex
         * @param i
         * @param j
         * @return true if union is performed successfully, false otherwise
         */
        public static boolean union(int i, int j)
        {
            int x = findSet(i);
            int y = findSet(j);
            if(x != y)
            {
                if(rank[x] > rank[y])
                    p[y] = p[x];
                else
                {
                    p[x] = p[y];
                    if(rank[x] == rank[y])
                        rank[y]++; //increment the rank
                }
                numOfDisjoinSet --;
                return true;
            }
            return false;
        }
    }

    private static List<List<Integer>> graph = new ArrayList<>();
    private static List<Edge> edges = new ArrayList<>();
    private static BitSet done = new BitSet();
    private static BitSet visited = new BitSet();
    private static int min = Integer.MAX_VALUE, N;

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        String path = args[0];
        File file = new File(path);
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        int i = 1;
        graph.add(0, null);
        while((line = bf.readLine()) != null)
        {
            List<Integer> list = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(line);
            st.nextToken(); //ignore
            while(st.hasMoreTokens())
                list.add(Integer.parseInt(st.nextToken()));
            graph.add(i++, list); //list of edges
        }
        for(int k = 1, l = graph.size(); k < l; k ++)
        {
            if(!done.get(k))
                dfs(-1, k);
        }
        N = graph.size() - 1; //number of vertices
        for(int j = 0; j < N * 2; j++)
        {
            UnionFind.p = new int[N + 1];
            UnionFind.rank = new int[N + 1];
            UnionFind.init();
            Random r = new Random();
            int totalEdges = edges.size();
            while(UnionFind.numOfDisjoinSet > 2)
            {
                int index = r.nextInt(totalEdges);
                contract(edges.get(index));
            }
            min = Math.min(min, count());
        }
        System.out.println(min);
    }

    /**
     * Count cross edges
     * @return total count of cross edges
     */
    private static int count()
    {
        int count = 0;
        for (Edge e : edges)
        {
            if(UnionFind.findSet(e.u) != UnionFind.findSet(e.v))
                count++;
        }
        return count;
    }

    /**
     * Perform contraction
     * @param edge edge
     */
    private static void contract(Edge edge)
    {
        UnionFind.union(edge.u, edge.v);
    }

    /**
     * Dfs to build list of unique edges
     * @param v
     */
    private static void dfs(int p, int v)
    {
        //done.set(v); //mark this visited
        visited.set(v);
        List<Integer> children = graph.get(v);
        for(int c : children)
        {
            if(c != p && !done.get(c))
            {
                edges.add(new Edge(v, c)); //create a edge
                if(!visited.get(c))
                    dfs(v, c);
            }
        }
        done.set(v);
    }
}
