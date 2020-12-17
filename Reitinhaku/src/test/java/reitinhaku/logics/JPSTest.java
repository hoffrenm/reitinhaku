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
import reitinhaku.domain.Result;

/**
 *
 * @author Mika Hoffren
 */
public class JPSTest {

    private Node[][] nodes;
    private TestHelper testHelper;
    private JPS jps;

    @Before
    public void setUp() {
        this.testHelper = new TestHelper();
        this.jps = new JPS(new Heuristic());

        this.nodes = testHelper.getNodeArray(100, 100);
    }

    @Test
    public void validGraphYieldsResult() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = jps.findPath(start, goal);
        assertNotNull(result);
    }

    @Test
    public void noGoalHasNoPath() {
        testHelper.removeRow(nodes, 50);
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = jps.findPath(start, goal);
        assertNull(result);
    }

    @Test
    public void pathHasApproximatelyRightLength() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = jps.findPath(start, goal);
        assertTrue(Math.abs(result.getLength() - 138) < 1);
    }

    @Test
    public void jpsExploresOnlyFewNodes() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = jps.findPath(start, goal);
        assertTrue(result.getExplored().size() < 3);
    }

    @Test
    public void jpsPathHasFewNodes() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = jps.findPath(start, goal);
        assertTrue(result.getPath().size() < 3);
    }

    @Test
    public void jpsResultsInMaze() {
        testHelper.addObstacles(nodes);
        Node start = nodes[2][2];
        Node goal = nodes[98][98];

        Result result = jps.findPath(start, goal);
        assertTrue(result.getPath().size() < 100);
        assertTrue(result.getExplored().size() < 160);
        assertTrue(Math.abs(result.getLength() - 2640) < 1);
    }

}
