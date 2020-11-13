/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.domain;

import java.util.List;

/**
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

    public void debug() {
        System.out.println("Nodes: " + nodes.size() + " \n Start: " + start + " \n Goal: " + goal);
    }

    public Node getStart() {
        return start;
    }

    public void setStart(int x, int y) {
        this.start = nodes.stream().filter(n -> n.getX() == x && n.getY() == y).findFirst().orElse(null);
    }

    public Node getGoal() {
        return goal;
    }

    public void setGoal(int x, int y) {
        this.goal = nodes.stream().filter(n -> n.getX() == x && n.getY() == y).findFirst().orElse(null);
    }

}
