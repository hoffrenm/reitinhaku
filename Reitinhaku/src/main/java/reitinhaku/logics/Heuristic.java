/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import reitinhaku.domain.Node;

/**
 * Class that holds heuristic functions for algorithms to utilize.
 *
 * @author Mika Hoffren
 */
public class Heuristic {

    private int heuristic;

    public Heuristic() {
        this.heuristic = 1;
    }

    /**
     * Euclidean distance between two nodes.
     *
     * @param first First node
     * @param second Second node
     * @return Euclidean distance between two nodes. Always positive.
     */
    public float euclidean(Node first, Node second) {
        float x = first.getX() - second.getX();
        float y = first.getY() - second.getY();

        double xx = x*x;
        double yy = y*y;

        return (float) Math.sqrt(xx + yy);
    }

    /**
     * Manhattan distance between two nodes.
     *
     * @param first First node.
     * @param second Second node.
     * @return Manhattan distance between two nodes. Always positive.
     */
    private int manhattan(Node first, Node second) {
        float x = first.getX() - second.getX();
        float y = first.getY() - second.getY();
        
        x = x > 0 ? x : -x;
        y = y > 0 ? y : -y;

        return (int) (x + y);
    }

    /**
     * Chebyshev distance between two nodes.
     *
     * @param first First node.
     * @param second Second node.
     * @return Chebyshev distance between two nodes. Always positive.
     */
    private float chebyshev(Node first, Node second) {
        float x = first.getX() - second.getX();
        float y = first.getY() - second.getY();

        x = x > 0 ? x : -x;
        y = y > 0 ? y : -y;
        
        return x > y ? x : y;
    }

    /**
     * Distance between two nodes is ignored.
     *
     * @param first First node.
     * @param second Second node.
     * @return Zero distance.
     */
    private float constant() {
        return 0f;
    }

    public void setHeuristic(int i) {
        this.heuristic = i;
    }

    /**
     * Gives distance between nodes using algorithm which is previously set.
     *
     * @param first First node.
     * @param second Second node.
     * @return Distance between nodes with given algorithm.
     */
    public float getDistance(Node first, Node second) {
        switch (heuristic) {
            case 1:
                return euclidean(first, second);
            case 2:
                return manhattan(first, second);
            case 3:
                return chebyshev(first, second);
        }

        return constant();
    }
}

