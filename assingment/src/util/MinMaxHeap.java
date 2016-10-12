package util;

import java.util.Comparator;

/**
 * Created by pradhang on 10/12/2016.
 * Custom PriorityQueue using heap.
 */
public class MinMaxHeap<E>
{
    private final Comparator<? super E> comparator;
    private int capacity = 0;
    private int size = 0;
    private Object[] queue;

    /**
     * Constructor
     * @param capacity capacity
     * @param comparator Comparator
     */
    public MinMaxHeap(int capacity,
                      Comparator<? super E> comparator)
    {
        this.comparator = comparator;
        this.capacity = capacity;
        queue = new Object[capacity];
    }

    /**
     * Add to queue
     * @param e E element to add to the queue
     */
    public void add(E e)
    {
        if(size >= capacity - 1)
            throw new RuntimeException("Heap full.");
        queue[size] = e;
        bubbleUp(e);
        size++;
    }

    /**
     * Fetch top element from the heap. Works in O(logN).
     * @return E
     */
    @SuppressWarnings("unchecked")
    public E peek()
    {
        return (E) queue[0];
    }

    /**
     * Delete top element from heap. Works in O(logN).
     * @return E top element which was deleted. null if the queue is empty.
     */
    @SuppressWarnings("unchecked")
    public E poll()
    {
        if(size == 0)
            return null;
        E top = (E) queue[0];
        if(size > 1)
        {
            queue[0] = queue[size - 1];
            bubbleDown();
        }
        size--;
        return top;
    }

    /**
     * Shift element up
     * @param e E
     */
    @SuppressWarnings("unchecked")
    private void bubbleUp(E e)
    {
        int i = size, parent;
        while(i > 0)
        {
            parent = (i - 1) >>> 1;
            if(comparator.compare((E)queue[i], (E)queue[parent]) >= 0)
                break;
            Object temp = queue[parent];
            queue[parent] = e;
            queue[i] = temp;
            i = parent;
        }
    }

    /**
     * Shift down root element.
     */
    @SuppressWarnings("unchecked")
    private void bubbleDown()
    {
        if(size <= 1)
            return;
        int i = 1, child1, child2, child;
        Object temp;
        switch (size)
        {
            case 2 :
                if(comparator.compare((E)queue[1], (E)queue[0]) < 0)
                {
                    temp = queue[0];
                    queue[0] = queue[1];
                    queue[1] = temp;
                }
                break;

            default:
                while(i < size)
                {
                    child1 = (i << 1) - 1;
                    child2 = i << 1;
                    if(child1 < size && child2 < size)
                        child = (comparator.compare((E) queue[child1], (E) queue[child2]) < 0) ? child1 : child2;
                    else if(child1 < size)
                        child = child1;
                    else break;

                    if(comparator.compare((E) queue[child], (E) queue[i - 1]) < 0)
                    {
                        //swap
                        temp = queue[i - 1];
                        queue[i - 1] = queue[child];
                        queue[child] = temp;
                    }
                    i = child + 1;
                }
                break;
        }
    }

    /**
     * Return size
     * @return int size
     */
    public int size()
    {
        return size;
    }
}
