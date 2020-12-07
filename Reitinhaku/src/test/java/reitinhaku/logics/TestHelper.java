package reitinhaku.logics;

import reitinhaku.domain.Node;

/**
 *
 * @author Mika Hoffren
 */
public class TestHelper {

    private Node[][] nodeArray;

    private void buildNodeArrayFromDimensions(int x, int y) {
        nodeArray = new Node[y][x];

        createNodes(nodeArray, x, y);
        connectNodes(nodeArray);
    }

    public Node[][] getNodeArray(int x, int y) {
        buildNodeArrayFromDimensions(x, y);

        return nodeArray;
    }

    private void createNodes(Node[][] nodes, int x, int y) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                nodes[i][j] = new Node(j, i);
            }
        }
    }

    private void connectNodes(Node[][] nodeArr) {
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

    public void removeRow(Node[][] nodeArr, int x) {
        for (int i = 0; i < nodeArr[x].length; i++) {
            nodeArray[x][i] = null;
        }

        connectNodes(nodeArr);
    }

    public void addObstacles(Node[][] nodeArr) {
        for (int i = 9; i < nodeArr.length - 9; i = i + 3) {
            for (int j = 3; j < nodeArr[i].length; j++) {
                if (i % 6 == 0) {
                    nodeArr[i][nodeArr[i].length - j] = null;
                } else {
                    nodeArr[i][j] = null;
                }
            }
        }

        connectNodes(nodeArr);
    }

}
