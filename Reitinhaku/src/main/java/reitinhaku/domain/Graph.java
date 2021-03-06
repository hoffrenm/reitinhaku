/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents graph which were created from image.
 *
 * @see GraphBuilder
 *
 * @author Mika Hoffren
 */
public class Graph {

    private Node start;
    private Node goal;

    private List<Node> nodes;

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Sets every node status to unexplored.
     */
    public void reset() {
        for (Node node : nodes) {
            node.setPrevious(null);
            node.setfScore(Float.MAX_VALUE);
            node.setgScore(Float.MAX_VALUE);
            node.openNode();
        }
    }

    /**
     * Starting node which have been previously set.
     *
     * @return Starting Node or null not set.
     */
    public Node getStart() {
        return start;
    }

    /**
     * Finds and sets starting node for the graph. Nonexistent node will result
     * start to be null.
     *
     * @param x X coordinate of starting node.
     * @param y Y coordinate of starting node.
     */
    public void setStart(int x, int y) {
        this.start = nodes.stream().filter(n -> n.getX() == x && n.getY() == y).findFirst().orElse(null);
    }

    /**
     * Goal node which have been previously set.
     *
     * @return Goal node or null if not set.
     */
    public Node getGoal() {
        return goal;
    }

    /**
     * Finds and sets goal node for the graph. Nonexistent node will result goal
     * to be null.
     *
     * @param x X coordinate of goal node.
     * @param y Y coordinate of goal node.
     */
    public void setGoal(int x, int y) {
        this.goal = nodes.stream().filter(n -> n.getX() == x && n.getY() == y).findFirst().orElse(null);
    }

}
