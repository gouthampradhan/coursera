import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 09/10/2016.
 */
public class TwoSum
{
    private static Set<Long> hashSet = new HashSet<>(10000000);
    private static long[] arr = new long[10000000];
    private static int count = 0;

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
        int index = 0;
        while((line = bf.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(line.trim());
            String token = st.nextToken();
            long v = Long.parseLong(token);
            if(!hashSet.contains(v))
            {
                hashSet.add(v);
                arr[index++] = v;
            }
        }
        long start;
        start = System.currentTimeMillis();
        System.out.println("Using hashing...");
        countUsingHashing();
        System.out.println(count);
        System.out.println("Total time using hashing: " + (System.currentTimeMillis() - start));
        count = 0; //reset
        System.out.println("Using binary search...");
        start = System.currentTimeMillis();
        countUsingBinarySearch(Arrays.copyOf(arr, hashSet.size()));
        System.out.println(count);
        System.out.println("Total time using binary search: " + (System.currentTimeMillis() - start));
    }

    /**
     * Count using hashing
     */
    private static void countUsingHashing()
    {
        long y, s = hashSet.size();
        for (int i = -10000; i <= 10000; i++)
        {
            for (int j = 0; j < s; j ++)
            {
                y = (i - arr[j]);
                if(y != arr[j] && hashSet.contains(y))
                {
                    count++;
                    //System.out.println(i + "->" + arr[j] + " " + y);
                    break;
                }
            }
        }
    }

    /**
     * Count using binarySearch.
     */
    private static void countUsingBinarySearch(long[] arr)
    {
        long y; int s = arr.length;
        Arrays.sort(arr);
        for (int i = -10000; i <= 10000; i++)
        {
            for (int j = 0; j < s; j ++)
            {
                y = (i - arr[j]);
                if(y != arr[j])
                {
                    if(Arrays.binarySearch(arr, y) >= 0)
                    {
                        count++;
                        //System.out.println(i + "->" + arr[j] + " " + y);
                        break;
                    }
                }
            }
        }
    }

}
