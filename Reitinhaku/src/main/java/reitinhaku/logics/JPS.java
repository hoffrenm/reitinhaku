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
import reitinhaku.domain.Result;

/**
 * Pathfinding algorithm that uses Jump Point Search to find the shortest path
 * between two nodes in a graph.
 *
 * @author Mika Hoffren
 */
public class JPS {

    private Result solution;

    /**
     * Generates solution which contains backtraced path from goal to start.
     * Includes duration, length and nodes which have been visited during
     * pathfinding.
     *
     * @param goal Goal node.
     * @param explored List of explored nodes.
     * @param time Time elapsed on pathfinding.
     */
    private void constructPath(Node goal, Queue<Node> explored, double time) {
        this.solution = null;

        List<Node> path = new ArrayList<>();
        List<Node> visited = new ArrayList<>();
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

        Queue<Node> openQueue = new PriorityQueue<>();
        Queue<Node> closed = new PriorityQueue<>();
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

            for (Node next : prunedNeighbours(current)) {
                if (next == null || next.isClosed()) {
                    continue;
                }

                int dx = next.getX() - current.getX();
                int dy = next.getY() - current.getY();

                Node jumpNode = jump(next, goal, dx, dy);

                if (jumpNode == null) {
                    continue;
                }

                float distanceToJumpNode = Heuristic.euclidean(current, jumpNode);
                float travelledDistance = current.getgScore() + distanceToJumpNode;

                if (travelledDistance < jumpNode.getgScore()) {
                    jumpNode.setPrevious(current);
                    jumpNode.setgScore(travelledDistance);
                    jumpNode.setfScore(travelledDistance + Heuristic.euclidean(jumpNode, goal));

                    openQueue.add(jumpNode);
                }
            }
        }

        return solution;
    }

    /**
     * Returns a node which is a jump point. Jump point node is considered being
     * one of the possible shortest path to the goal node.
     *
     * @param next Current node being examined.
     * @param goal Destination node.
     * @return Node which is jump point.
     */
    private Node jump(Node next, Node goal, int dx, int dy) {
        if (next == null || next.isClosed()) {
            return null;
        }

        if (next == goal) {
            return next;
        }

        int index = direction(dx, dy);

        Node[] neighbours = next.getConnections();
        Node follower = neighbours[index];

        // Diagonal movement
        if (dx != 0 && dy != 0) {
            if ((neighbours[(index + 2) % 8] != null && neighbours[(index + 3) % 8] == null)
                    || (neighbours[(index + 6) % 8] != null && neighbours[(index + 3) % 5] == null)
                    || (neighbours[(index + 1) % 8] == null && neighbours[(index + 7) % 8] == null)) {
                return next;
            }

            // For each diagonal step, vertical and horizontal scans are launched
            if ((jump(next, goal, dx, 0) != null) || (jump(next, goal, 0, dy) != null)) {
                return next;
            }
            // Vertical and horizontal movement
        } else {
            if ((neighbours[(index + 1) % 8] != null && neighbours[(index + 2) % 8] == null)
                    || (neighbours[(index + 7) % 8] != null && neighbours[(index + 6) % 8] == null)) {
                return next;
            }
        }

        return jump(follower, goal, dx, dy);
    }

    /**
     * Dictates index of next neighbour considering direction of movement.
     * Neighbours are presented as adjacent list such that left neighbour is at
     * index 0 and goes clockwise around the node 0-7.
     *
     * @param dx change of coordinates in horizontal direction.
     * @param dy change of coordinates in vertical direction.
     * @return index of next neighbour in adjacency array.
     * 
     * @see Node.
     */
    private int direction(int dx, int dy) {
        // clamp direction down to -1, 0, 1 range
        dx = Math.max(-1, Math.min(1, dx));
        dy = Math.max(-1, Math.min(1, dy));

        int direction = dx + dy;
        int index = -1;

        if (Math.abs(direction) == 2) {
            index = dx > 0 ? 5 : 1;
        } else if (direction == 0) {
            index = dx > 0 ? 3 : 7;
        } else if (direction == 1) {
            index = dx > dy ? 4 : 6;
        } else if (direction == -1) {
            index = dx > dy ? 2 : 0;
        }

        return index;
    }

    /**
     * Returns all valid neighbours of node when direction of arrival is taken
     * into consideration. Checks natural and forced neighbours.
     *
     * @param next Node being currently examined.
     * @return Neighbouring nodes which fulfill pruning criteria.
     * 
     * @see Node
     */
    private Node[] prunedNeighbours(Node next) {
        Node[] neighbours = new Node[8];
        Node[] connections = next.getConnections();
        Node previous = next.getPrevious();

        if (previous == null) {
            return connections;
        }

        int dx = next.getX() - previous.getX();
        int dy = next.getY() - previous.getY();

        int index = direction(dx, dy);

        if (dx != 0 && dy != 0) {
            neighbours[(index - 1) % 8] = connections[(index - 1) % 8];
            neighbours[(index) % 8] = connections[(index) % 8];
            neighbours[(index + 1) % 8] = connections[(index + 1) % 8];

            if (connections[(index + 5) % 8] == null) {
                neighbours[(index + 6) % 8] = connections[(index + 6) % 8];
            }

            if (connections[(index + 3) % 8] == null) {
                neighbours[(index + 2) % 8] = connections[(index + 2) % 8];
            }
        } else {
            neighbours[(index)] = connections[(index)];

            if (connections[(index + 2) % 8] == null) {
                neighbours[(index + 1) % 8] = connections[(index + 1) % 8];
            }

            if (connections[(index + 6) % 8] == null) {
                neighbours[(index + 7) % 8] = connections[(index + 7) % 8];
            }
        }

        return neighbours;
    }

}
