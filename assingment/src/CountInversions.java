import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gouthamvidyapradhan on 29/09/2016.
 * Countnversions using MergeSort O(NlogN)
 * Accepted
 */
public class CountInversions
{
    private static int[] A;
    private static long splitCount = 0L;
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        String path = args[0];
        File file = new File(path);
        List<Integer> list = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        while((line = bf.readLine()) != null)
            list.add(Integer.parseInt(line.trim()));
        A = new int[list.size()];
        int i = 0;
        for(int e : list)
            A[i++] = e;
        sortAndMerge(0, A.length - 1);
        System.out.println(splitCount);
    }

    /**
     * Recursively perform split
     * @param l left index
     * @param r right index
     * @return sorted array
     */
    private static int[] sortAndMerge(int l, int r)
    {
        if(l == r)
        {
            int[] m = new int[1];
            m[0] = A[l];
            return m;
        }
        int m = (r - l) / 2;
        int[] left = sortAndMerge(l, l + m);
        int[] right = sortAndMerge(l + m + 1, r);
        return countInversion(left, right);
    }

    /**
     * Count inversion and merge
     * @param left left array
     * @param right right array
     * @return merged array
     */
    private static int[] countInversion(int[] left, int[] right)
    {
        int i = 0, j = 0, k = 0, leftLen = left.length, rightLen = right.length;
        int l = leftLen + rightLen;
        int[] merged = new int[l];
        do
        {
            if(left[i] <= right[j])
                merged[k++] = left[i++];
            else
            {
                splitCount += (leftLen - i);
                merged[k++] = right[j++];
            }

        } while (i < leftLen && j < rightLen);

        //copy remaining
        if(i < leftLen)
            for(; i < leftLen; i++)
                merged[k++] = left[i];

        else if(j < rightLen)
            for(; j < rightLen; j++)
                merged[k++] = right[j];

        return merged;
    }
}
