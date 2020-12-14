/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mika Hoffren
 */
public class PriorityQueueTest {

    private PriorityQueue pq;

    @Before
    public void setUp() {
        this.pq = new PriorityQueue();
    }

    @Test
    public void freshQueueIsEmpty() {
        assertTrue(pq.isEmpty());
    }

    @Test
    public void freshQueueHasNoElements() {
        assertEquals(0, pq.size());
    }

    @Test
    public void elementCanBeAdded() {
        pq.add(1);
        assertEquals(1, pq.size());
    }
    
    @Test
    public void multipleElementsCanBeAdded() {
        pq.add(15);
        pq.add(7);
        pq.add(19);
        pq.add(3);
        assertEquals(4, pq.size());
    }
    
    @Test
    public void evenMoreElementsCanBeAdded() {
        for (int i = 0; i < 135; i++) {
            pq.add(i);
        }
        
        assertEquals(pq.size(), 135);
    }
    
    @Test
    public void elementsAreOrdered1() {
        pq.add(15);
        pq.add(7);
        pq.add(3);
        pq.add(19);
        assertEquals(pq.poll(), 3);
    }
    
    @Test
    public void elementsAreOrdered2() {
        pq.add(1023);
        pq.add(15);
        pq.add(954);
        assertEquals(pq.poll(), 15);
        pq.add(1000);
        pq.add(123);
        assertEquals(pq.poll(), 123);
        assertEquals(pq.poll(), 954);
        pq.add(19);
        assertEquals(pq.poll(), 19);
    }
    
    @Test
    public void emptyQueueReturnsNull() {
        assertNull(pq.poll());
    }
    
    @Test
    public void queueCanBeEmptiedAndFilledAgain() {
        pq.add(19);
        pq.poll();
        pq.add(39);
        pq.add(21);
        pq.add(25);
        assertEquals(pq.poll(), 21);
    }
    
}
