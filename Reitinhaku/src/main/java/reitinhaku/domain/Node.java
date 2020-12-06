/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.domain;

/**
 * Single node in graph. Contains coordinates and information for pathfinding
 * algorithm to use and modify during pathfinding.
 *
 * @author Mika Hoffren
 */
public class Node implements Comparable<Node> {

    private int x;
    private int y;

    private float gScore;
    private float fScore;

    private Node previous;
    private Node[] connections;

    private boolean closed;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gScore = Float.MAX_VALUE;
        this.fScore = Float.MAX_VALUE;
        this.previous = null;
        this.connections = new Node[8];
        this.closed = false;
    }

    /**
     * X coordinate of a node.
     *
     * @return X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Y coordinate of a node.
     *
     * @return Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns a path length which have been travelled so far when this node is
     * reached.
     *
     * @return Path length.
     */
    public float getgScore() {
        return gScore;
    }

    /**
     * Sets a gScore which presents travelled distance to this node through
     * previous nodes. GScore may always be determined lower during pathfinding,
     * but never increased.
     *
     * @param gScore Current travelled length.
     */
    public void setgScore(float gScore) {
        this.gScore = gScore;
    }

    /**
     * Best estimate to goal during pathfinding. Consists of travelled distance
     * (gScore) + heuristic estimate to finish.
     *
     * @return Estimate to end node.
     */
    public float getfScore() {
        return fScore;
    }

    /**
     * Sets best estimate for node which have been calculated during
     * pathdfinding. Consists of travelled distance (gScore) + heuristic
     * estimate to finish.
     *
     * @param fScore Estimated value to end node.
     */
    public void setfScore(float fScore) {
        this.fScore = fScore;
    }

    /**
     * Returns a previous node where from this node was accessed. Previous node
     * is not necessarily a neighbouring node.
     *
     * @return Previous node.
     */
    public Node getPrevious() {
        return previous;
    }

    /**
     * Sets a parent node where this node was arrived from.
     *
     * @param previous A parent node in a path.
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * Implicates if this node have been visited during algorithm. Implicates if
     * all the neighbouring nodes have been exhausted during pathfinding.
     *
     * @return True if this node has no more neighbouring nodes to investigate,
     * false otherwise.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * When there are no more nodes to travel through this node, it is marked as
     * closed and will not be visited again
     */
    public void closeNode() {
        this.closed = true;
    }

    /**
     * Returns array containing 8 neighbours. Leftmost neighbour is at index 0
     * and goes clockwise from 0 to 7.
     *
     * @return Neighbouring nodes.
     */
    public Node[] getConnections() {
        return connections;
    }

    /**
     * Determines if node has priority over other node. Node with the lower
     * fScore has priority.
     *
     * @param o Node to compare.
     * @return True if compared node has lower fScore
     */
    @Override
    public int compareTo(Node o) {
        return this.fScore > o.fScore ? 1 : -1;
    }

}
