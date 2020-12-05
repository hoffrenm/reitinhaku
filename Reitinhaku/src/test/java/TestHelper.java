
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import reitinhaku.domain.Graph;
import reitinhaku.domain.Node;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mika Hoffren
 */
public class TestHelper {

    public Graph buildGraphFromDimensions(int x, int y) {
        Node[][] nodeArr = new Node[y][x];

        createNodes(nodeArr, x, y);
        connectNodes(nodeArr);

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
    
    public void removeNode(Graph graph, int x, int y) {
        
    }
}
