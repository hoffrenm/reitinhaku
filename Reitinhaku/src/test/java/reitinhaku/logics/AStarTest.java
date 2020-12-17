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
public class AStarTest {

    private Node[][] nodes;
    private TestHelper testHelper;
    private AStar aStar;
    private JPS jps;

    @Before
    public void setUp() {
        this.testHelper = new TestHelper();
        this.aStar = new AStar(new Heuristic());
        this.jps = new JPS(new Heuristic());

        this.nodes = testHelper.getNodeArray(100, 100);
    }

    @Test
    public void validGraphYieldsResult() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = aStar.findPath(start, goal);
        assertNotNull(result);
    }

    @Test
    public void noGoalHasNoPath() {
        testHelper.removeRow(nodes, 50);
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = aStar.findPath(start, goal);
        assertNull(result);
    }

    @Test
    public void pathHasApproximatelyRightLength() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = aStar.findPath(start, goal);
        assertTrue(Math.abs(result.getLength() - 138) < 1);
    }

    @Test
    public void aStarExploresModerateAmountOfNodes() {
        Node start = nodes[1][1];
        Node goal = nodes[99][99];

        Result result = aStar.findPath(start, goal);
        assertTrue(result.getExplored().size() > 95);
    }

    @Test
    public void aStarResultsInMaze() {
        testHelper.addObstacles(nodes);
        Node start = nodes[2][2];
        Node goal = nodes[98][98];

        Result result = aStar.findPath(start, goal);
        assertTrue(result.getPath().size() < 2610);
        assertTrue(result.getExplored().size() < 9135);
        assertTrue(Math.abs(result.getLength() - 2640) < 1);
    }

    @Test
    public void JPSIsSuperior() {
        testHelper.addObstacles(nodes);
        Node start = nodes[2][2];
        Node goal = nodes[98][98];

        Result astarR = aStar.findPath(start, goal);

        nodes = testHelper.getNodeArray(100, 100);
        testHelper.addObstacles(nodes);
        start = nodes[2][2];
        goal = nodes[98][98];

        Result jpsR = jps.findPath(start, goal);

        assertTrue(jpsR.getExplored().size() < astarR.getExplored().size());
        assertTrue(jpsR.getTime() < astarR.getTime());
        assertTrue(jpsR.getPath().size() < astarR.getPath().size());
    }

    @Test
    public void pathLengthsAreEqual() {
        testHelper.addObstacles(nodes);
        Node start = nodes[2][2];
        Node goal = nodes[98][98];

        Result astarR = aStar.findPath(start, goal);

        nodes = testHelper.getNodeArray(100, 100);
        testHelper.addObstacles(nodes);
        start = nodes[2][2];
        goal = nodes[98][98];

        Result jpsR = jps.findPath(start, goal);

        assertTrue(Math.abs(astarR.getLength() - jpsR.getLength()) < 1);
    }

}
