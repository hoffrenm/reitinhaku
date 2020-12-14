/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import java.util.ArrayList;
import reitinhaku.domain.Node;
import reitinhaku.domain.Result;

/**
 * Pathfinding algorithm that uses A* (A-star) to find the shortest path between
 * two nodes in a graph.
 *
 * @author Mika Hoffren
 */
public class AStar {

    private Result solution;

    /**
     * Generates solution which contains backtraced path from goal to start.
     * Includes duration and nodes which have been visited during pathfinding.
     *
     * @param goal Goal node.
     * @param explored List of explored nodes.
     * @param time Time elapsed on pathfinding.
     */
    private void constructPath(Node goal, PriorityQueue<Node> explored, double time) {
        ArrayList<Node> path = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        path.add(goal);

        Node previous = goal.getPrevious();

        while (previous != null) {
            path.add(previous);
            previous = previous.getPrevious();
        }

        while (!explored.isEmpty()) {
            visited.add(explored.poll());
        }

        this.solution = new Result(path, visited, time, goal.getgScore());
    }

    /**
     * Solves path between nodes using A*-algorithm if such exists.
     *
     * @param start Starting node.
     * @param goal Destination node.
     * @return Solution containing time, path, explored nodes and length of the
     * path. Returns null if there is no path between nodes.
     *
     * @see Result
     */
    public Result findPath(Node start, Node goal) {
        double startTime = System.currentTimeMillis();
        this.solution = null;

        PriorityQueue<Node> openQueue = new PriorityQueue<>();
        PriorityQueue<Node> closed = new PriorityQueue<>();
        openQueue.add(start);

        start.setgScore(0f);
        start.setfScore(Heuristic.euclidean(start, goal));

        while (!openQueue.isEmpty()) {
            Node current = openQueue.poll();
            current.closeNode();
            closed.add(current);

            if (current == goal) {
                double endTime = System.currentTimeMillis();
                constructPath(current, closed, endTime - startTime);
                break;
            }

            for (Node next : current.getConnections()) {
                if (next == null || next.isClosed()) {
                    continue;
                }

                float travelledDistance = current.getgScore();

                travelledDistance += isDiagonal(current, next) ? Math.sqrt(2) : 1;

                if (travelledDistance < next.getgScore()) {
                    next.setPrevious(current);
                    next.setgScore(travelledDistance);
                    next.setfScore(travelledDistance + Heuristic.euclidean(next, goal));

                    openQueue.add(next);
                }
            }
        }

        return solution;
    }

    /**
     * Determines if two nodes are diagonal to each other.
     *
     * @param current
     * @param next
     * @return True if nodes are in diagonal position, false otherwise.
     */
    private boolean isDiagonal(Node current, Node next) {
        return current.getX() != next.getX() && current.getY() != next.getY();
    }

}
