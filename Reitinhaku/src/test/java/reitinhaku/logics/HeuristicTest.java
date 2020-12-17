/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import reitinhaku.domain.Node;

/**
 *
 * @author Mika Hoffren
 */
public class HeuristicTest {

    private Heuristic heuristic;

    @Before
    public void setUp() {
        this.heuristic = new Heuristic();
    }

    @Test
    public void euclidean1() {
        Node node1 = new Node(0, 0);
        Node node2 = new Node(45, 83);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(94.41, dist, 0.1);
    }

    @Test
    public void euclidean2() {
        Node node1 = new Node(128, 47);
        Node node2 = new Node(13, 66);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(116.55, dist, 0.1);
    }

    @Test
    public void manhattan1() {
        heuristic.setHeuristic(2);
        Node node1 = new Node(0, 0);
        Node node2 = new Node(45, 83);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(128, dist, 0.01);
    }

    @Test
    public void manhattan2() {
        heuristic.setHeuristic(2);
        Node node1 = new Node(128, 47);
        Node node2 = new Node(13, 66);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(134, dist, 0.01);
    }

    @Test
    public void chebyshev1() {
        heuristic.setHeuristic(3);
        Node node1 = new Node(128, 47);
        Node node2 = new Node(13, 66);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(115, dist, 0.01);
    }

    @Test
    public void chebyshev2() {
        heuristic.setHeuristic(3);
        Node node1 = new Node(1, 47);
        Node node2 = new Node(5, 66);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(19, dist, 0.01);
    }
    
    @Test
    public void constant() {
        heuristic.setHeuristic(4);
        Node node1 = new Node(1, 1);
        Node node2 = new Node(99, 464);

        float dist = heuristic.getDistance(node1, node2);

        assertEquals(0, dist, 0.01);
    }

}
