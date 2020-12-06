/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import reitinhaku.domain.Graph;
import reitinhaku.domain.Node;

/**
 * Auxiliary class which has functionality to transform picture into graph which
 * is used in pathfinding.
 *
 * @see Graph
 *
 * @author Mika Hoffren
 */
public class GraphBuilder {

    private static final int PIXEL_VALUE_THRESHOLD = 200;

    private Node[][] nodeArr;

    /**
     * Generates map out of image. Pixels from grey to white are considered
     * walkable nodes and other colors represent nodes which cannot be accessed.
     *
     * @param map Image of the map.
     * @return Graph generated from image.
     *
     * @see Image
     * @see Graph
     */
    public Graph buildGraphFromImage(Image map) {
        int width = (int) map.getWidth();
        int height = (int) map.getHeight();

        nodeArr = new Node[height][width];

        createNodes(map, width, height);
        connectNodes();

        List<Node> nodes = new ArrayList<>();

        for (Node[] row : nodeArr) {
            for (Node node : row) {
                if (node != null) {
                    nodes.add(node);
                }
            }
        }

        return new Graph(nodes);
    }

    /**
     * Reads color value of pixels and determines if it is a valid node.
     *
     * @param map Picture of a map.
     * @param x Width of the map.
     * @param y Height of the map.
     */
    private void createNodes(Image map, int x, int y) {
        PixelReader reader = map.getPixelReader();

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                int rgb = reader.getArgb(j, i);

                int red = ((rgb >> 16) & 0xff);
                int green = ((rgb >> 8) & 0xff);
                int blue = (rgb & 0xff);

                if (isNode(red, green, blue)) {
                    nodeArr[i][j] = new Node(j, i);
                }
            }
        }
    }

    /**
     * Connects neighbouring nodes to each other so that graph is formed.
     */
    private void connectNodes() {
        for (int i = 1; i < nodeArr.length - 1; i++) {
            for (int j = 1; j < nodeArr[i].length - 1; j++) {
                Node node = nodeArr[i][j];
                if (node != null) {
                    Node[] connections = node.getConnections();

                    connections[0] = nodeArr[i][j - 1] != null ? nodeArr[i][j - 1] : null; // left
                    connections[1] = nodeArr[i - 1][j - 1] != null ? nodeArr[i - 1][j - 1] : null; // top-left
                    connections[2] = nodeArr[i - 1][j] != null ? nodeArr[i - 1][j] : null; // top
                    connections[3] = nodeArr[i - 1][j + 1] != null ? nodeArr[i - 1][j + 1] : null; // top-right
                    connections[4] = nodeArr[i][j + 1] != null ? nodeArr[i][j + 1] : null; // right
                    connections[5] = nodeArr[i + 1][j + 1] != null ? nodeArr[i + 1][j + 1] : null; // bottom-right
                    connections[6] = nodeArr[i + 1][j] != null ? nodeArr[i + 1][j] : null; // bottom
                    connections[7] = nodeArr[i + 1][j - 1] != null ? nodeArr[i + 1][j - 1] : null; // bottom-left
                }
            }
        }
    }

    /**
     * Determines if a single pixel is a node.
     *
     * @param red Color value for red channel.
     * @param green Color value for green channel.
     * @param blue Color value for blue channel.
     * @return True if color is determined to be valid node, false otherwise.
     */
    private boolean isNode(int red, int green, int blue) {
        return red >= PIXEL_VALUE_THRESHOLD && green >= PIXEL_VALUE_THRESHOLD && blue >= PIXEL_VALUE_THRESHOLD;
    }

}
