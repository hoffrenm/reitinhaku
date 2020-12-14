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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import reitinhaku.domain.Node;
import reitinhaku.domain.Result;

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

        goalCoor.setPrefWidth(coordinates.getWidth());
        startCoor.setPrefWidth(coordinates.getWidth());

        goalCoor.getChildren().addAll(goalX, goalY);
        startCoor.getChildren().addAll(startX, startY);

        coordinates.getChildren().addAll(new Label("Koordinaatit"), new Label("Maali"), goalCoor, new Label("Lähtö"), startCoor);
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
                startX.setText(Double.toString(e.getX()));
                startY.setText(Double.toString(e.getY()));

                graph.setStart(x, y);
            } else if (click == MouseButton.SECONDARY) {

                goalX.setText(Double.toString(x));
                goalY.setText(Double.toString(y));

                graph.setGoal(x, y);
            }

            System.out.println("maali: " + graph.getGoal() + "\n alku: " + graph.getStart());
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        });

        Button solveButton = new Button("Selvitä reitti");
        Button clearButton = new Button("Tyhjennä");
        Button openButton = new Button("Lataa kartta");
        Button exitButton = new Button("Lopeta");

        VBox results = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        HBox h4 = new HBox();
        Text t1 = new Text("Aika: ");
        Text time = new Text();
        Text t2 = new Text("Solmujen määrä reitillä: ");
        Text pathLength = new Text();
        Text t3 = new Text("Tutkitut solmut: ");
        Text exploredNodes = new Text();
        Text t4 = new Text("Reitin pituus: ");
        Text pathPixelLength = new Text();
        h1.getChildren().addAll(t1, time);
        h2.getChildren().addAll(t2, pathLength);
        h3.getChildren().addAll(t3, exploredNodes);
        h4.getChildren().addAll(t4, pathPixelLength);
        results.getChildren().addAll(h1, h4, h2, h3);

        exitButton.setMinWidth(buttons.getPrefWidth());
        openButton.setMinWidth(buttons.getPrefWidth());
        clearButton.setMinWidth(buttons.getPrefWidth());
        solveButton.setMinWidth(buttons.getPrefWidth());

        openButton.setOnAction(btnLoadEventListener);
        exitButton.setOnAction((event) -> {
            stage.close();
        });
        clearButton.setOnAction((event) -> {
            Image image = map.getImage();
            graph = graphBuilder.buildGraphFromImage(image);
            route.getGraphicsContext2D().clearRect(0, 0, image.getHeight(), image.getWidth());
        });
        solveButton.setOnAction((event) -> {
            Image image = map.getImage();
            graph = graphBuilder.buildGraphFromImage(image);
            route.getGraphicsContext2D().clearRect(0, 0, image.getHeight(), image.getWidth());

            int xS = (int) Double.parseDouble(startX.getText());
            int yS = (int) Double.parseDouble(startY.getText());
            int xG = (int) Double.parseDouble(goalX.getText());
            int yG = (int) Double.parseDouble(goalY.getText());

            graph.setStart(xS, yS);
            graph.setGoal(xG, yG);

            Node start = graph.getStart();
            Node goal = graph.getGoal();

            if (start != null && goal != null) {
                RadioButton selectedAlg = (RadioButton) group.getSelectedToggle();
                String alg = selectedAlg.getText();
                Result solution = null;

                if (alg.equals("A*")) {
                    solution = astar.findPath(start, goal);
                } else if (alg.equals("Jump Point Search")) {
                    solution = jps.findPath(start, goal);
                }

                if (solution != null) {
                    drawPath(solution);
                    time.setText(solution.getTime() + " ms");
                    pathLength.setText(solution.getPath().size() + " solmua");
                    exploredNodes.setText(solution.getExplored().size() + " solmua");
                    pathPixelLength.setText(solution.getLength() + " px");
                } else {
                    System.out.println("*** NO SOLUTION ***");
                }
            }
        });

        buttons.getChildren().addAll(solveButton, clearButton, openButton, exitButton);

        VBox sidebar = new VBox();
        sidebar.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setPadding(new Insets(20, 40, 0, 40));
        sidebar.setSpacing(10);
        sidebar.getChildren().addAll(results, coordinates, algorithms, buttons);

        root.setRight(sidebar);

        root.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPrefHeight(1080);
        root.setPrefWidth(1300);

        Scene scene = new Scene(root);
        stage.setTitle("Pathfinding");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void drawPath(Result solution) {
        GraphicsContext gc = route.getGraphicsContext2D();
        gc.clearRect(0, 0, 1000, 1000);

        gc.setFill(Color.BLUE);

        for (Node node : solution.getExplored()) {
            gc.fillOval(node.getX(), node.getY(), 2, 2);
        }

        gc.setStroke(Color.RED);
        gc.setLineWidth(3);

        Node previous = graph.getGoal();
        while (previous != null) {
            Node temp = previous.getPrevious();
            if (temp == null) {
                break;
            }
            gc.strokeLine(previous.getX(), previous.getY(), temp.getX(), temp.getY());
            previous = previous.getPrevious();
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
                    route.getGraphicsContext2D().clearRect(0, 0, image.getHeight(), image.getWidth());
                    route.setWidth(image.getWidth());
                    route.setHeight(image.getHeight());

                    graph = graphBuilder.buildGraphFromImage(image);
                } catch (IOException ex) {
                    System.out.println("Error uploading an image: " + ex.toString());
                }
            }

        }
    };
}
