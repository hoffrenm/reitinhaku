/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.ui;

import reitinhaku.logics.AStar;
import reitinhaku.logics.JPS;
import reitinhaku.logics.GraphBuilder;
import reitinhaku.domain.Graph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import reitinhaku.domain.Node;

/**
 *
 * @author Mika Hoffren
 */
public class PathfinderUi extends Application {

    private AStar astar;
    private JPS jps;

    private final GraphBuilder graphBuilder;
    private Graph graph;

    private final BorderPane root;
    private final Pane mapArea;
    private final Canvas route;
    private final ImageView map;

    public PathfinderUi() {
        this.astar = new AStar();
        this.jps = new JPS();
        this.graphBuilder = new GraphBuilder();

        this.root = new BorderPane();
        this.map = new ImageView();
        this.route = new Canvas();
        this.mapArea = new Pane(map, route);
    }

    @Override
    public void start(Stage stage) throws Exception {
        root.setCenter(mapArea);
        route.setMouseTransparent(true);

        VBox buttons = new VBox();
        buttons.setPrefWidth(100);
        buttons.setPadding(new Insets(20, 20, 40, 20));
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);

        TextField goalX = new TextField();
        TextField goalY = new TextField();
        TextField startX = new TextField();
        TextField startY = new TextField();

        VBox algorithms = new VBox();
        algorithms.setPadding(new Insets(20, 20, 20, 20));
        algorithms.setSpacing(5);
        ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("A*");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Jump Point Search");
        rb2.setToggleGroup(group);

        algorithms.getChildren().addAll(new Label("Algoritmi"), rb1, rb2);

        VBox coordinates = new VBox();
        HBox goalCoor = new HBox();
        HBox startCoor = new HBox();
        HBox labels = new HBox(new Label("X"), new Label("Y"));;

        goalCoor.setPrefWidth(coordinates.getWidth());
        startCoor.setPrefWidth(coordinates.getWidth());

        goalCoor.getChildren().addAll(goalX, goalY);
        startCoor.getChildren().addAll(startX, startY);

        coordinates.getChildren().addAll(new Label("Koordinaatit"), labels, new Label("Maali"), goalCoor, new Label("Lähtö"), startCoor);
        coordinates.setPrefWidth(75);
        coordinates.setPadding(new Insets(20, 20, 40, 20));

        map.setPreserveRatio(true);
        map.setSmooth(true);
        map.setCache(true);

        map.setOnMouseClicked(e -> {
            MouseButton click = e.getButton();
            int x = (int) e.getX();
            int y = (int) e.getY();

            if (click == MouseButton.PRIMARY) {
                goalX.setText(Double.toString(x));
                goalY.setText(Double.toString(y));

                graph.setGoal(x, y);
            } else if (click == MouseButton.SECONDARY) {
                startX.setText(Double.toString(e.getX()));
                startY.setText(Double.toString(e.getY()));

                graph.setStart(x, y);
            }

            System.out.println("maali: " + graph.getGoal() + "\n alku: " + graph.getStart());
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
            System.out.println(map.getImage().getHeight() + " : " + map.getImage().getWidth());

        });

        Button solveButton = new Button("Selvitä reitti");
        Button openButton = new Button("Valitse kartta");
        Button exitButton = new Button("Lopeta");

        exitButton.setMinWidth(buttons.getPrefWidth());
        openButton.setMinWidth(buttons.getPrefWidth());

        openButton.setOnAction(btnLoadEventListener);
        exitButton.setOnAction((event) -> {
            stage.close();
        });
        solveButton.setOnAction((event) -> {
            Node start = graph.getStart();
            Node goal = graph.getGoal();

            if (start != null && goal != null) {
                astar.findPath(graph.getStart(), graph.getGoal());
                List<Node> path = astar.getPath();

                if (!path.isEmpty()) {
                    drawPath(path);
                } else {
                    System.out.println("*** NO SOLUTION ***");
                }
            }
        });

        buttons.getChildren().addAll(openButton, exitButton);

        VBox sidebar = new VBox();
        sidebar.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setPadding(new Insets(20, 40, 0, 40));
        sidebar.setSpacing(10);
        sidebar.getChildren().addAll(solveButton, coordinates, algorithms, buttons);

        root.setRight(sidebar);

        root.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPrefHeight(600);
        root.setPrefWidth(1000);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void drawPath(List<Node> solution) {
        GraphicsContext gc = route.getGraphicsContext2D();
        gc.clearRect(0, 0, 1000, 1000);
        gc.setFill(Color.RED);

        for (Node node : solution) {
            gc.fillOval(node.getX(), node.getY(), 2, 2);
        }

    }

    EventHandler<ActionEvent> btnLoadEventListener
            = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    map.setImage(image);
                    route.setWidth(image.getWidth());
                    route.setHeight(image.getHeight());

                    graph = graphBuilder.buildGraphFromImage(image);

                    graph.debug();
                } catch (IOException ex) {
                }
            }

        }
    };
}
