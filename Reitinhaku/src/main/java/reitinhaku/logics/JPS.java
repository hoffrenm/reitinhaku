/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import reitinhaku.domain.Node;
import reitinhaku.domain.Result;

/**
 *
 * @author Mika Hoffren
 */
public class JPS {

    private Result solution;

    private void constructPath(Node goal, Queue<Node> explored, double time) {
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

        this.solution = new Result(path, visited, time);
    }

    public Result getSolution() {
        return solution;
    }

    public void findPath(Node start, Node goal) {
        double startTime = System.currentTimeMillis();

        Queue<Node> openQueue = new PriorityQueue<>();
        Queue<Node> closed = new PriorityQueue<>();
        openQueue.add(start);

        start.setgScore(0f);
        start.setfScore(heuristic(start, goal));

        while (!openQueue.isEmpty()) {
            Node current = openQueue.poll();
            current.closeNode();
            closed.add(current);

            if (current == goal) {
                double endTime = System.currentTimeMillis();
                System.out.println("Goal reached");
                constructPath(current, closed, endTime - startTime);
                break;
            }

            for (Node next : current.getConnections()) {
                if (next == null || next.isClosed()) {
                    continue;
                }

                int dx = next.getX() - current.getX();
                int dy = next.getY() - current.getY();

                Node jumpNode = jump(current, dx, dy, goal);

                if (jumpNode == null || jumpNode.isClosed()) {
                    continue;
                }

                float distanceToJumpNode = heuristic(jumpNode, current);
                float travelledDistance = current.getgScore() + distanceToJumpNode;

                if (!jumpNode.isOpened() || travelledDistance < next.getgScore()) {
                    next.setPrevious(current);
                    next.setgScore(travelledDistance);
                    next.setfScore(travelledDistance + heuristic(next, goal));

                    jumpNode.openNode();
                    openQueue.add(jumpNode);
                }
            }
        }
    }

    private Node jump(Node current, int dx, int dy, Node goal) {
        int direction = dx + dy;
        int index = -1;

        if (Math.abs(direction) == 2) {
            index = dx > 0 ? 5 : 1;
        } else if (direction == 0) {
            index = dx > 0 ? 7 : 3;
        } else if (direction == 1) {
            index = dx > dy ? 6 : 4;
        } else if (direction == -1) {
            index = dx > dy ? 0 : 2;
        }

        Node next = current.getConnections()[index];

        if (next == null || next.isClosed()) {
            return null;
        }

        if (next == goal) {
            return next;
        }

        //Diagonal movement
        if (dx != 0 && dy != 0) {
            // Horizontal
            if (jump(next, dx, 0, goal) != null) {
                return next;
            }
            // Vertical
            if (jump(next, 0, dy, goal) != null) {
                return next;
            }
            //Vertical or horizontal movement
        } else {
            //Horizontal
            Node[] temp = current.getConnections();
            if (dx != 0) {
                if (temp[2] != null || temp[6] != null) {
                    return next;
                }
                //Vertical
            } else {
                if (temp[0] != null || temp[4] != null) {
                    return next;
                }
            }
        }

        return jump(next, dx, dy, goal);
    }

    private float heuristic(Node current, Node goal) {
        float x = Math.abs(current.getX() - goal.getX());
        float y = Math.abs(current.getY() - goal.getY());

        double xx = Math.pow(x, 2);
        double yy = Math.pow(y, 2);

        return (float) Math.sqrt(xx + yy);
    }

    private boolean isDiagonal(Node current, Node next) {
        if (current.getX() != next.getX() && current.getY() != next.getY()) {
            return true;
        }

        return false;
    }

}
