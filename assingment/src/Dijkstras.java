import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 08/10/2016.
 * Accepted.
 */
public class Dijkstras
{
    /**
     * Cell class to store cell info
     * @author gouthamvidyapradhan
     *
     */
    static class Vertex
    {
        Vertex(int i, int d)
        {
            this.i = i;
            this.d = d;
        }
        int i;
        int d;
    }

    /**
     * Priority Queue
     */
    private static Queue<Vertex> pq = new PriorityQueue<>(200, new Comparator<Vertex>()
    {
        @Override
        public int compare(Vertex o1, Vertex o2)
        {
            return Integer.compare(o1.d, o2.d);
        }
    });
    private static List<List<Vertex>> graph = new ArrayList<>();
    private static int[] minDistance;
    private static int N;

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
        N = 200; //number of vertices
        for(int i = 0; i <= N; i ++)
            graph.add(i, new ArrayList<>());
        while((line = bf.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(line.trim());
            int u = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens())
            {
                String token = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(token.trim(), ",");
                int v = Integer.parseInt(st1.nextToken());
                int w = Integer.parseInt(st1.nextToken());
                graph.get(u).add(new Vertex(v, w));
            }
        }
        dijkstra(1);
        for (int i = 1; i <= N; i ++)
            System.out.println(i + " " + minDistance[i]);
    }

    /**
     * Dijktra's algorithm to find shortest path
     * @param u
     * @return
     */
    static private void dijkstra(int u)
    {
        minDistance = new int[N + 1];
        for(int i=0; i <= N; i++)
            minDistance[i] = Integer.MAX_VALUE;

        Vertex start = new Vertex(u, 0);
        minDistance[start.i] = 0;
        pq.add(start);
        while(!pq.isEmpty())
        {
            Vertex parent = pq.remove();
            if(parent.d != minDistance[parent.i])
                continue;
            List<Vertex> children = graph.get(parent.i);
            for(Vertex c : children)
            {
                if((c.d + parent.d) < minDistance[c.i])
                {
                    //Update the min distance
                    minDistance[c.i] = c.d + parent.d;
                    pq.add(new Vertex(c.i, c.d + parent.d));
                }
            }
        }
    }
}
