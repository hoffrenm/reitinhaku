/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import java.util.Arrays;

/**
 * Array based minimum heap priority queue. Allowed operations are insertion and
 * retrieval of value with the highest priority.
 *
 * @author Mika Hoffren
 * @param <E> Element that implements Comparable-interface.
 */
public class PriorityQueue<E extends Comparable<E>> {

    private E[] heap;
    private int heapSize;

    /**
     * Initializes empty priority queue with default 32 slot array.
     */
    public PriorityQueue() {
        heapSize = 0;
        heap = (E[]) new Comparable[32];
    }

    /**
     * Indicates whether queue is empty.
     *
     * @return True if queue has no elements.
     */
    public boolean isEmpty() {
        return this.heapSize == 0;
    }
    
    /**
     * Returns number of elements added to queue so far.
     * 
     * @return Number of elements in queue.
     */
    public int size() {
        return this.heapSize;
    }

    /**
     * Solves index of parent element in array for given children index.
     *
     * @param i Child index in array.
     * @return Index of parent element in array.
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Corresponding array index for left child of the node.
     *
     * @param i Index of node in array.
     * @return Index of left child.
     */
    private int leftChildIndex(int i) {
        return (2 * i) + 1;
    }

    /**
     * Corresponding array index for right child of the node.
     *
     * @param i Index of node in array.
     * @return Index of right child.
     */
    private int rightChildIndex(int i) {
        return (2 * i) + 2;
    }

    /**
     * Insert new element to the queue.
     *
     * @param node Element to be added to queue.
     */
    public void add(E node) {
        if (heapSize > heap.length - 1) {
            increaseSize();
        }

        heap[heapSize] = node;
        heapSize++;
        heapifyUp(heapSize - 1);
    }

    /**
     * Return the element which is in front of the queue. Has highest priority
     * of all nodes inserted. Returns null if queue has no elements.
     *
     * @return Element with the highest priority.
     */
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        E polled = heap[0];
        heap[0] = heap[heapSize - 1];
        heap[heapSize - 1] = null;
        heapSize--;

        heapifyDown(0);

        return polled;
    }

    /**
     * Array will be ordered to fulfill heap rules. Used with insertion method
     * to place last added element to its correct position.
     *
     * @param index Starting index.
     */
    private void heapifyUp(int index) {
        E temp = heap[index];

        while (index > 0 && temp.compareTo(heap[parent(index)]) < 0) {
            heap[index] = heap[parent(index)];
            index = parent(index);
        }

        heap[index] = temp;
    }

    /**
     * Array will be ordered to fulfill heap rules. Used with polling method to
     * shift elements to correct positions after first element of the queue is
     * removed.
     *
     * @param index Starting index
     */
    private void heapifyDown(int index) {
        int left = leftChildIndex(index);
        int right = rightChildIndex(index);
        int smallest = index;

        if (left < heapSize && heap[leftChildIndex(index)].compareTo(heap[index]) < 0) {
            smallest = left;
        }

        if (right < heapSize && heap[rightChildIndex(index)].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * Swap place of two elements in array.
     *
     * @param i Index of first element.
     * @param j Index of second element.
     */
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Doubles size of heap array.
     */
    private void increaseSize() {
        this.heap = Arrays.copyOf(heap, heap.length * 2);
    }

}
