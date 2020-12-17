/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import reitinhaku.domain.Graph;
import reitinhaku.domain.Result;
import reitinhaku.logics.AStar;
import reitinhaku.logics.GraphBuilder;
import reitinhaku.logics.Heuristic;
import reitinhaku.logics.JPS;
import reitinhaku.ui.PathfinderUi;

/**
 *
 * @author hoffrenm
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        PathfinderUi.main(args);

        GraphBuilder gb = new GraphBuilder();
        String testFile = "32room_001";

        File details = new File(testFile + ".map.scen");
        File map = new File(testFile + ".map");

        ArrayList<String> astarResults = new ArrayList<>();
        ArrayList<String> jpsResults = new ArrayList<>();

        AStar astar = new AStar(new Heuristic());
        JPS jps = new JPS(new Heuristic());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(details));

        Graph graph = gb.buildGraphFromMapFile(map);

        float actualLength = 0;

        Result r1;
        Result r2;

        String st;
        while ((st = bufferedReader.readLine()) != null) {
            String[] stats = st.split("\t");

            if (stats.length < 3) {
                continue;
            }

            graph.setStart(Integer.parseInt(stats[4]), Integer.parseInt(stats[5]));
            graph.setGoal(Integer.parseInt(stats[6]), Integer.parseInt(stats[7]));

            actualLength = Float.parseFloat(stats[8]);

            graph.reset();
            r1 = astar.findPath(graph.getStart(), graph.getGoal());

            if (Math.abs(r1.getLength() - actualLength) < 50) {
                astarResults.add(r1.getTime() + ";" + actualLength + ";" + r1.getExplored().size());
            }

            graph.reset();
            r2 = jps.findPath(graph.getStart(), graph.getGoal());

            if (Math.abs(r2.getLength() - actualLength) < 50) {
                jpsResults.add(r2.getTime() + ";" + actualLength + ";" + r2.getExplored().size());
            }

        }

        System.out.println(astarResults.toString());
        System.out.println(jpsResults.toString());

        FileWriter writer = new FileWriter(testFile + ".txt");
        for (String str : astarResults) {
            writer.write("astar;" + str + System.lineSeparator());
        }
        for (String str : jpsResults) {
            writer.write("jps;" + str + System.lineSeparator());
        }
        writer.close();
    }

}
