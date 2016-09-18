package GUI.Windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private String theme;

    public LegendWindow(Stage primaryStage, String windowTitle, String mapTheme, String theme){
        this.primaryStage = primaryStage;
        this.windowTitle = windowTitle;
        this.mapTheme = mapTheme;
        this.theme = theme;
    }

    public void display(){
        // displays the fucking window
        Stage window = new Stage();
        window.setTitle(windowTitle);
        window.initModality(Modality.APPLICATION_MODAL); // means that while this window is open, you can't interact with the main program.

        // Buttons
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> window.close());

        // labels
        Label walls = new Label("Walls");
        Label path = new Label("Path");
        Label portal = new Label("Portals");
        Label ladder = new Label("Stairs\n(It's actually a ladder)");
        Label finish = new Label("Finish");
        Label start = new Label("Start");
        Label sprite = new Label("Sprite");
        Label marked = new Label("Visited Spot");

        // Images
        Image wallImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/wall.png"));
        Image pathImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/openPath.png"));
        Image portalImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/portal.png"));
        Image ladderImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/ladder.png"));
        Image startImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/Start.png"));
        Image finishImg = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/Finish.png"));
        Image spriteImge = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/sprite.png"));
        Image markImage = new Image(getClass().getResourceAsStream("/graphics/"+mapTheme+"/visited.png"));

        // layout components.
        VBox layout = new VBox(10); // main hbox splits screen down the middle.
        HBox subLayout = new HBox(50);
        VBox leftVBox = new VBox(20); // the left vbox, the next three hbox are sub elements of this one.
        HBox left1 = new HBox(10);
        ImageView wallImageView = new ImageView(wallImg);
        HBox left2 = new HBox(10);
        ImageView pathImageView = new ImageView(pathImg);
        HBox left3 = new HBox(10);
        ImageView portalImageView = new ImageView(portalImg);
        HBox left4 = new HBox(10);
        ImageView spriteImageView = new ImageView(spriteImge);
        VBox rightVBox = new VBox(20); // the right vbox, the rest of the hbox are sub elements of this one.
        HBox right1 = new HBox(10);
        ImageView ladderImageView = new ImageView(ladderImg);
        HBox right2 = new HBox(10);
        ImageView startImageView = new ImageView(startImg);
        HBox right3 = new HBox(10);
        ImageView finishImageView = new ImageView(finishImg);
        HBox right4 = new HBox(10);
        ImageView markImageView = new ImageView(markImage);
        VBox closeBox = new VBox();
        closeBox.getChildren().addAll(closeBtn);
        closeBox.setAlignment(Pos.CENTER_RIGHT);
        closeBox.setPadding(new Insets(5,5,5,5));

        // building layout.
        left1.getChildren().addAll(wallImageView, walls);
        left1.setAlignment(Pos.CENTER_LEFT);
        left2.getChildren().addAll(pathImageView, path);
        left2.setAlignment(Pos.CENTER_LEFT);
        left3.getChildren().addAll(portalImageView, portal);
        left3.setAlignment(Pos.CENTER_LEFT);
        left4.getChildren().addAll(spriteImageView, sprite);
        left4.setAlignment(Pos.CENTER_LEFT);
        leftVBox.getChildren().addAll(left1, left2, left3, left4);
        leftVBox.setAlignment(Pos.TOP_RIGHT);
        leftVBox.setPadding(new Insets(25,0,0,25));
        right1.getChildren().addAll(ladderImageView, ladder);
        right1.setAlignment(Pos.CENTER_LEFT);
        right2.getChildren().addAll(startImageView, start);
        right2.setAlignment(Pos.CENTER_LEFT);
        right3.getChildren().addAll(finishImageView, finish);
        right3.setAlignment(Pos.CENTER_LEFT);
        right4.getChildren().addAll(markImageView, marked);
        right4.setAlignment(Pos.CENTER_LEFT);
        rightVBox.getChildren().addAll(right1, right2, right3, right4);
        rightVBox.setAlignment(Pos.TOP_RIGHT);
        rightVBox.setPadding(new Insets(25,0,0,25));
        subLayout.getChildren().addAll(leftVBox, rightVBox);
        layout.getChildren().addAll(subLayout, closeBox);
        layout.setAlignment(Pos.CENTER);



        // building and displaying the window.
        Scene scene = new Scene(layout);
        scene.getStylesheets().addAll("/graphics/css/"+theme+".css");
        window.setScene(scene);
        window.setHeight(350);
        window.setWidth(500);
        window.setResizable(false);
        window.show();
    }
}
