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

    /**
     * Euclidean distance between two nodes.
     * 
     * @param first First node
     * @param second Second node.
     * @return Euclidean distance between two nodes. Always positive.
     */
    public static float euclidean(Node first, Node second) {
        float x = Math.abs(first.getX() - second.getX());
        float y = Math.abs(first.getY() - second.getY());

        double xx = Math.pow(x, 2);
        double yy = Math.pow(y, 2);

        return (float) Math.sqrt(xx + yy);
    }

}
