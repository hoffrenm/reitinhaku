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
import java.util.List;
import reitinhaku.domain.Graph;
import reitinhaku.domain.Node;
import reitinhaku.domain.Result;
import reitinhaku.logics.AStar;
import reitinhaku.logics.GraphBuilder;
import reitinhaku.logics.Heuristic;
import reitinhaku.logics.JPS;

/**
 *
 * @author hoffrenm
 */
public class Benchmark {

    public static void run(String fileName) throws IOException, FileNotFoundException {
        String path = new File(".").getCanonicalPath();

        GraphBuilder gb = new GraphBuilder();

        File details = new File(path + "/maps/" + fileName + ".map.scen");
        File map = new File(path + "/maps/" + fileName + ".map");

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
                astarResults.add("astar;" + r1.getTime() + ";" + actualLength + ";" + r1.getExplored().size());
            }

            graph.reset();
            r2 = jps.findPath(graph.getStart(), graph.getGoal());

            if (Math.abs(r2.getLength() - actualLength) < 50) {
                jpsResults.add("jps;" + r2.getTime() + ";" + actualLength + ";" + r2.getExplored().size());
            }

        }

        writeToTextFile(fileName, astarResults, jpsResults);

    }

    public static void writeToTextFile(String fileName, List<String>... results) throws IOException {
        FileWriter writer = new FileWriter(fileName + ".txt");

        for (List<String> list : results) {
            for (String str : list) {
                writer.write(str + System.lineSeparator());
            }
        }

        writer.close();
    }
    
}
