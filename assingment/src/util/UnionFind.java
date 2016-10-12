package util;

/**
 * Created by gouthamvidyapradhan on 12/10/2016.
 * UnionFindDisjoint Set
 */
/**
 *
 * @author gouthamvidyapradhan
 * Class to represent UnionFind Disjoint Set
 *
 */
class UnionFind
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
