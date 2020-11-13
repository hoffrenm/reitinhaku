/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import reitinhaku.domain.Node;

/**
 *
 * @author Mika Hoffren
 */
public class AStar {

    public ArrayList<Node> path;

    public AStar() {
        this.path = null;
    }

    public List<Node> path() {
        return this.path = new ArrayList<>();
    }

    private void constructPath(Node goal) {
        path = new ArrayList<>();
        path.add(goal);

        Node previous = goal.getPrevious();

        while (previous != null) {
            path.add(previous);

            previous = previous.getPrevious();
        }
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    // todo: refactor this to dedicated class (possibly with multiple heuristics)
    private float heuristic(Node current, Node goal) {
        float x = Math.abs(current.getX() - goal.getX());
        float y = Math.abs(current.getY() - goal.getY());

        double xx = Math.pow(x, 2);
        double yy = Math.pow(y, 2);

        return (float) Math.sqrt(xx + yy);
    }

    public void findPath(Node start, Node goal) {
        Queue<Node> openQueue = new PriorityQueue<>();
        openQueue.add(start);

        start.setgScore(0f);
        start.setfScore(heuristic(start, goal));

        while (!openQueue.isEmpty()) {
            Node current = openQueue.poll();
            current.closeNode();

            if (current == goal) {
                System.out.println("Goal reached");
                constructPath(current);
                break;
            }

            for (Node next : current.getConnections()) {
                if (next == null || next.isClosed()) {
                    continue;
                }

                float travelledDistance = current.getgScore() + 1f; // at this time cost of one step is 1

                if (travelledDistance < next.getgScore()) {
                    next.setPrevious(current);
                    next.setgScore(travelledDistance);
                    next.setfScore(travelledDistance + heuristic(next, goal));

                    openQueue.add(next);
                }
            }
        }
    }

}
