package GUI.Windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LegendWindow Class.
 * Displays for the map.
 *
 * @author Patrick Shinn
 * @version 9/15/16
 */
public class LegendWindow {
    private Stage primaryStage;
    private String windowTitle;
    private String mapTheme;

    public LegendWindow(Stage primaryStage, String windowTitle, String mapTheme){
        this.primaryStage = primaryStage;
        this.windowTitle = windowTitle;
        this.mapTheme = mapTheme;
    }

    public void display(){
        // displays the fucking window
        Stage window = new Stage();
        window.setTitle(windowTitle);
        window.initModality(Modality.APPLICATION_MODAL); // means that while this window is open, you can't interact with the main program.

        // labels
        Label walls = new Label("Walls");
        Label path = new Label("Path");
        Label portal = new Label("Portals");
        Label ladder = new Label("Stairs\n(It's actually a ladder)");
        Label finish = new Label("Finish");
        Label start = new Label("Start");

        // Images
        Image wallImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/wall.png"));
        Image pathImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"//openPath.png"));
        Image portalImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/portal.png"));
        Image ladderImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"//ladder.png"));
        Image startImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"//Start.png"));
        Image finishImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"//Finish.png"));

        // layout components.
        HBox layout = new HBox(50); // main hbox splits screen down the middle.
        VBox leftVBox = new VBox(20); // the left vbox, the next three hbox are sub elements of this one.
        HBox left1 = new HBox(10);
        ImageView wallImageView = new ImageView(wallImg);
        HBox left2 = new HBox(10);
        ImageView pathImageView = new ImageView(pathImg);
        HBox left3 = new HBox(10);
        ImageView portalImageView = new ImageView(portalImg);
        VBox rightVBox = new VBox(20); // the right vbox, the rest of the hbox are sub elements of this one.
        HBox right1 = new HBox(10);
        ImageView ladderImageView = new ImageView(ladderImg);
        HBox right2 = new HBox(10);
        ImageView startImageView = new ImageView(startImg);
        HBox right3 = new HBox(10);
        ImageView finishImageView = new ImageView(finishImg);

        // building layout.
        left1.getChildren().addAll(wallImageView, walls);
        left1.setAlignment(Pos.CENTER_LEFT);
        left2.getChildren().addAll(pathImageView, path);
        left2.setAlignment(Pos.CENTER_LEFT);
        left3.getChildren().addAll(portalImageView, portal);
        left3.setAlignment(Pos.CENTER_LEFT);
        leftVBox.getChildren().addAll(left1, left2, left3);
        leftVBox.setAlignment(Pos.TOP_RIGHT);
        leftVBox.setPadding(new Insets(25,0,0,25));
        right1.getChildren().addAll(ladderImageView, ladder);
        right1.setAlignment(Pos.CENTER_LEFT);
        right2.getChildren().addAll(startImageView, start);
        right2.setAlignment(Pos.CENTER_LEFT);
        right3.getChildren().addAll(finishImageView, finish);
        right3.setAlignment(Pos.CENTER_LEFT);
        rightVBox.getChildren().addAll(right1, right2, right3);
        rightVBox.setAlignment(Pos.TOP_RIGHT);
        rightVBox.setPadding(new Insets(25,0,0,25));
        layout.getChildren().addAll(leftVBox, rightVBox);
        layout.setAlignment(Pos.CENTER);



        // building and displaying the window.
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setHeight(300);
        window.setWidth(500);
        window.setResizable(false);
        window.show();
    }
}
