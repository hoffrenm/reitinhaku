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
public class AStar {

    private Result solution;

    public AStar() {
    }

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
        
        this. solution = new Result(path, visited, time);
    }

    public Result getSolution() {
        return solution;
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
                
                float travelledDistance = current.getgScore();
                
                travelledDistance += isDiagonal(current, next) ? Math.sqrt(2) : 1;

                if (travelledDistance < next.getgScore()) {
                    next.setPrevious(current);
                    next.setgScore(travelledDistance);
                    next.setfScore(travelledDistance + heuristic(next, goal));

                    openQueue.add(next);
                }
            }
        }
    }
    
    private boolean isDiagonal(Node current, Node next) {
        if (current.getX() != next.getX() && current.getY() != next.getY()) {
            return true;
        }
        
        return false;
    }

}
