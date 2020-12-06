/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.domain;

import java.util.List;

/**
 * Result class holds information for successful pathfinding. Consists of path
 * between goal and start, list of explored nodes, time and length of the path.
 *
 * @author Mika Hoffren
 */
public class Result {

    private final double time;
    private final float length;

    private final List<Node> path;
    private final List<Node> explored;

    public Result(List<Node> path, List<Node> explored, double time, float length) {
        this.time = time;
        this.path = path;
        this.explored = explored;
        this.length = length;
    }

    /**
     * Returns time which algorithm took while solving the path between the goal
     * and the start nodes in milliseconds.
     *
     * @return Time in milliseconds.
     */
    public double getTime() {
        return time;
    }

    /**
     * Returns list of nodes that are part of the shortest path between the goal
     * and the starting node.
     *
     * @return List of nodes which are part of the path.
     */
    public List<Node> getPath() {
        return path;
    }

    /**
     * Returns a list of nodes which have been opened and closed by algorithm in
     * process of pathfinding.
     *
     * @return List of explored nodes during pathfinding.
     */
    public List<Node> getExplored() {
        return explored;
    }

    /**
     * Returns the length of the path in pixels.
     *
     * @return Length of the path in pixels.
     */
    public float getLength() {
        return length;
    }

}
