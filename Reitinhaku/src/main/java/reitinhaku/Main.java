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
        
        System.out.println(args.length);
        if (args.length == 1) {
            Benchmark.run(args[0]);
        } else {
            PathfinderUi.main(args);
        }
    }

}
