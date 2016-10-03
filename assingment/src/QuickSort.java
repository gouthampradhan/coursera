import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gouthamvidyapradhan on 03/10/2016.
 * Calculate number of comparisons in quicksort
 */
public class QuickSort
{
    private static int[] A;
    private static int comparisions;

    /**
     * Choose pivot strategy
     */
    private enum PivotStrategy
    {
        FIRST,
        LAST,
        MIDDLE;
    }
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

        reset(list);
        quickSort(0, A.length - 1, PivotStrategy.FIRST);
        print(PivotStrategy.FIRST);

        reset(list);
        quickSort(0, A.length - 1, PivotStrategy.LAST);
        print(PivotStrategy.LAST);

        reset(list);
        quickSort(0, A.length - 1, PivotStrategy.MIDDLE);
        print(PivotStrategy.MIDDLE);
    }

    /**
     * Print output array and comparisions
     * @param p
     */
    private static void print(PivotStrategy p)
    {
        System.out.println(p.toString() + ": " + comparisions);
        for (int e : A)
            System.out.print(e + " ");
        System.out.println();
    }

    /**
     * Reset input array
     * @param list
     */
    private static void reset(List<Integer> list)
    {
        int i = 0;
        for(int e : list)
            A[i++] = e;
        comparisions = 0;
    }

    /**
     * Quick sort
     * @param l left index
     * @param r right index
     * @param p pivot position
     */
    private static void quickSort(int l, int r, PivotStrategy p)
    {
        if(l < r)
        {
            int temp, pivot;
            comparisions += r - l;
            switch (p)
            {
                case LAST:
                    temp = A[l];
                    A[l] = A[r];
                    A[r] = temp;
                    break;

                case MIDDLE:
                    //choose median
                    int median;
                    int m = ((r - l) / 2) + l;
                    if((A[m] <= A[l] && A[m] >= A[r]) || (A[m] <= A[r] && A[m] >= A[l]))
                        median = m;
                    else if((A[l] <= A[m] && A[l] >= A[r]) || (A[l] <= A[r] && A[l] >= A[m] ))
                        median = l;
                    else
                        median = r;
                    temp = A[median];
                    A[median] = A[l];
                    A[l] = temp;
                    break;
            }
            pivot = partition(l, r);
            quickSort(l, pivot - 1, p);
            quickSort(pivot + 1, r, p);
        }
    }

    /**
     * Perform partition
      * @param l left index
     * @param r right index
     * @return pivot element index after partitioning
     */
    private static int partition(int l, int r)
    {
        int p = A[l], i = l + 1, j = l + 1, temp;
        do
        {
            if(A[i] < p)
            {
                temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                j++;
            }
            i++;
        } while(i <= r);

        j--;
        if(j > l)
        {
            temp = A[l];
            A[l] = A[j];
            A[j] = temp;
        }
        return j;
    }
}
