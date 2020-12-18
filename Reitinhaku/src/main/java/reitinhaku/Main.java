/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku;

import java.io.IOException;
import reitinhaku.ui.PathfinderUi;

/**
 *
 * @author hoffrenm
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            System.out.println("Running scenario " + args[0]);
            Benchmark.run(args[0]);
            System.out.println("Scenario finished. Check project folder for results.");
        } else {
            PathfinderUi.main(args);
        }
    }

}
