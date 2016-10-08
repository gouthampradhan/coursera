import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 07/10/2016.
 * Accepted. Trajan's algorithm of finding SCC works under 6 s.
 */
public class SCC
{
    private static List<List<Integer>> graph = new ArrayList<>();
    private static int[] scc = new int[5];
    private static int[] dfs_low, dfs_num;
    private static int next = 1, top = 0, count = 0;
    private static int[] s;
    private static BitSet visited = new BitSet();

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
        int N = 875714; //number of vertices
        long start = System.currentTimeMillis();
        for(int i = 0; i <= N; i ++)
            graph.add(i, new ArrayList<>());
        s = new int[N + 1]; dfs_num = new int[N + 1]; dfs_low = new int[N + 1];
        while((line = bf.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(line.trim());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
        }
        for(int i = 1; i <= N; i++)
            if(dfs_num[i] == 0)
                dfs(i);
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start));
        for(int i : scc)
            System.out.println(i);
    }

    /**
     * Dfs to find scc
     * @param v
     */
    private static int dfs(int v)
    {
        dfs_num[v] = next; dfs_low[v] = next++;
        s[top++] = v;
        visited.set(v); //vertex set currently being explored
        List<Integer> children = graph.get(v);
        for(int c : children)
        {
            if(dfs_num[c] == 0)
                dfs_low[v] = Math.min(dfs_low[v], dfs(c));
            else if(visited.get(c))
                dfs_low[v] = Math.min(dfs_low[v], dfs_low[c]);
        }
        if(dfs_num[v] == dfs_low[v])
        {
            count = 0;
            while(true)
            {
                int e = s[--top];
                if(e != v)
                {
                    count++;
                    visited.clear(e);
                }
                else break;
            }
            count++;
            visited.clear(s[top]);
            maintainMax(count);
        }
        return dfs_low[v];
    }

    /**
     * Maintain maximum
     * @param count
     */
    private static void maintainMax(int count)
    {
        int temp1, temp2;
        boolean done = false;
        for(int i = 0; (i < 5 && !done); i++)
        {
            if(count > scc[i])
            {
                temp1 = scc[i];
                for(int  j = i + 1; j < 5; j++)
                {
                    temp2 = scc[j];
                    scc[j] = temp1;
                    temp1 = temp2;
                }
                scc[i] = count;
                done = true;
            }
        }
    }
}
