import util.MinMaxHeap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 10/10/2016.
 * Accepted.
 * Sum of medians using min and max heap.
 */
public class MedianMaintenance
{
    private static int sumOfMedian = 0;
    private static BufferedReader bf;

   /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        bf = new BufferedReader(new FileReader(new File(args[0])));
        String line;

        List<Integer> list = new ArrayList<>();
        int N, minTop, maxTop;

        while((line = bf.readLine()) != null)
            list.add(Integer.parseInt(line.trim()));
        N = list.size();

        MinMaxHeap<Integer> minHeap = new MinMaxHeap<>(N + 1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        MinMaxHeap<Integer> maxHeap = new MinMaxHeap<>(N + 1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        int a = list.get(0);
        int b = list.get(1);
        sumOfMedian = (a % 10000); //initialize the sum of medians with the first element.
        if(a < b)
        {
            maxHeap.add(a); //left heap
            minHeap.add(b); //right heap
        }
        else
        {
            maxHeap.add(b);
            minHeap.add(a);
        }
        sumMedian(maxHeap, minHeap);
        for (int i = 2, l = list.size(); i < l; i ++)
        {
            maxTop = maxHeap.peek();
            minTop = minHeap.peek();
            if(list.get(i) <= maxTop)
                maxHeap.add(list.get(i));
            else if(list.get(i) > minTop)
                minHeap.add(list.get(i));
            else
            {
                //if the given value lies in between
                if(maxHeap.size() < minHeap.size())
                    maxHeap.add(list.get(i));
                else
                    minHeap.add(list.get(i));
            }
            //fix the imbalance
            fixImbalance(maxHeap, minHeap);
            sumMedian(maxHeap, minHeap);
        }
        System.out.println(sumOfMedian);
    }

    /**
     *
     * @param minHeap
     * @param maxHeap
     */
    private static void fixImbalance(MinMaxHeap<Integer> minHeap, MinMaxHeap<Integer> maxHeap)
    {
        int diff = maxHeap.size() - minHeap.size();
        if(diff == 2)
            minHeap.add(maxHeap.poll());
        else if(diff == -2)
            maxHeap.add(minHeap.poll());
    }

    /**
     *
     * @param maxHeap
     * @param minHeap
     */
    private static void sumMedian(MinMaxHeap<Integer> maxHeap, MinMaxHeap<Integer> minHeap)
    {
        if(maxHeap.size() >= minHeap.size())
            sumOfMedian = (sumOfMedian += (maxHeap.peek())) % 10000;
        else sumOfMedian = (sumOfMedian += (minHeap.peek())) % 10000;
    }
}
