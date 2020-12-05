/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import reitinhaku.domain.Node;

/**
 *
 * @author Mika Hoffren
 */
public class Heuristic {

    public static float euclidean(Node current, Node next) {
        float x = Math.abs(current.getX() - next.getX());
        float y = Math.abs(current.getY() - next.getY());

        double xx = Math.pow(x, 2);
        double yy = Math.pow(y, 2);

        return (float) Math.sqrt(xx + yy);
    }

}
