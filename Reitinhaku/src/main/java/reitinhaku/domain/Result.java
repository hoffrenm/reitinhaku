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
public class Result {

    private double time;

    private List<Node> path;
    private List<Node> explored;

    public Result(List<Node> path, List<Node> explored, double time) {
        this.time = time;
        this.path = path;
        this.explored = explored;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public List<Node> getExplored() {
        return explored;
    }

    public void setExplored(List<Node> explored) {
        this.explored = explored;
    }

    @Override
    public String toString() {
        return "Result{" + "time=" + time + ", path=" + path + ", explored=" + explored + '}';
    }

}
