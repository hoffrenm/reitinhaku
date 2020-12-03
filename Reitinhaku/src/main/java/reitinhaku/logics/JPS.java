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
        Queue<Node> openQueue = new PriorityQueue<>();
        Queue<Node> closed = new PriorityQueue<>();
        openQueue.add(start);

        start.setgScore(0f);
        start.setfScore(heuristic(start, goal));

        double startTime = System.currentTimeMillis();

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

            for (Node next : prunedNeighbours(current)) {
                if (next == null || next.isClosed()) {
                    continue;
                }

                int dx = next.getX() - current.getX();
                int dy = next.getY() - current.getY();

                Node jumpNode = jump(next, goal, dx, dy);

                if (jumpNode == null || jumpNode.isClosed()) {
                    continue;
                }

                float distanceToJumpNode = heuristic(current, jumpNode);
                float travelledDistance = current.getgScore() + distanceToJumpNode;

                if (travelledDistance < jumpNode.getgScore()) {
                    jumpNode.setPrevious(current);
                    jumpNode.setgScore(travelledDistance);
                    jumpNode.setfScore(travelledDistance + heuristic(jumpNode, goal));

                    jumpNode.openNode();
                    openQueue.add(jumpNode);
                }
            }
        }
    }

    private Node jump(Node next, Node goal, int dx, int dy) {
        if (next == null || next.isClosed()) {
            return null;
        }

        if (next == goal) {
            return next;
        }

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

        Node[] neighbours = next.getConnections();
        Node follower = neighbours[index];

        //Diagonal movement
        if (dx != 0 && dy != 0) {
            if (index == 7) {
                if ((neighbours[1] != null && neighbours[2] == null)
                        || (neighbours[5] != null && neighbours[4] == null)
                        || (neighbours[0] == null && neighbours[6] == null)) {
                    return next;
                }
            } else if (index == 1) {
                if ((neighbours[7] != null && neighbours[6] == null)
                        || (neighbours[3] != null && neighbours[4] == null)
                        || (neighbours[0] == null && neighbours[2] != null)) {
                    return next;
                }
            } else if (index == 5) {
                if ((neighbours[7] != null && neighbours[0] == null)
                        || (neighbours[3] != null && neighbours[2] == null)
                        || (neighbours[6] == null && neighbours[4] == null)) {
                    return next;
                }
            } else if (index == 3) {
                if ((neighbours[1] != null && neighbours[0] == null)
                        || (neighbours[5] != null && neighbours[6] == null)
                        || (neighbours[2] == null && neighbours[4] == null)) {
                    return next;
                }
            }

            if ((jump(next, goal, dx, 0) != null) || (jump(next, goal, 0, dy) != null)) {
                return next;
            }
        } else {
            //Horizontal
            if (dx != 0) {
                if (dx > 0) {
                    if ((neighbours[3] != null && neighbours[2] == null) || (neighbours[5] != null && neighbours[6] == null)) {
                        return next;
                    }
                } else {
                    if ((neighbours[1] != null && neighbours[2] == null) || (neighbours[7] != null && neighbours[6] == null)) {
                        return next;
                    }
                }
                //Vertical
            } else {
                if (dy > 0) {
                    if ((neighbours[5] != null && neighbours[4] == null) || (neighbours[7] != null && neighbours[0] == null)) {
                        return next;
                    }
                } else {
                    if ((neighbours[1] != null && neighbours[0] == null) || (neighbours[3] != null && neighbours[4] == null)) {
                        return next;
                    }
                }
            }
        }

        return jump(follower, goal, dx, dy);
    }

    private Node[] prunedNeighbours(Node next) {
        Node[] neighbours = new Node[8];
        Node[] connections = next.getConnections();
        Node previous = next.getPrevious();

        if (previous == null) {
            return connections;
        }

        int dx = next.getX() - previous.getX();
        int dy = next.getY() - previous.getY();

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

    private float heuristic(Node first, Node second) {
        float x = Math.abs(first.getX() - second.getX());
        float y = Math.abs(first.getY() - second.getY());

        double xx = Math.pow(x, 2);
        double yy = Math.pow(y, 2);

        return (float) Math.sqrt(xx + yy);
    }

}
