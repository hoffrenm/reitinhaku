/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.domain;

/**
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
        this.connections = new Node[4];
        this.closed = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getgScore() {
        return gScore;
    }

    public void setgScore(float gScore) {
        this.gScore = gScore;
    }

    public float getfScore() {
        return fScore;
    }

    public void setfScore(float fScore) {
        this.fScore = fScore;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public boolean isClosed() {
        return closed;
    }

    public void closeNode() {
        this.closed = true;
    }

    public Node[] getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        return "Node{" + "x=" + x + ", y=" + y + ", connections=" + connections.length;
    }

    @Override
    public int compareTo(Node o) {
        return this.fScore > o.fScore ? 1 : -1;
    }

}
