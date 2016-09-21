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
 * WarningWindow Class
 * This displays a waring when the user tries to run the program with an unloaded map.
 * @author Patrick Shinn
 * @version 9/15/16
 */
public class WarningWindow {
    private Stage primaryStage;
    private String windowTitle;
    private String warning, theme;

    public WarningWindow(Stage primaryStage, String windowTitle, String warning,String mapTheme){
        this.primaryStage = primaryStage;
        this.windowTitle = windowTitle;
        this.warning = warning;
        this.theme = mapTheme;
    }

    public void display(){
        // displays the window
        Stage window = new Stage();
        window.setTitle(windowTitle);
        window.initModality(Modality.APPLICATION_MODAL); // means that while this window is open, you can't interact with the main program.

        // buttons
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> window.close());

        // labels
        Label warningLabel = new Label(warning);

        // images
        ImageView warningImage = new ImageView(new Image(getClass().getResourceAsStream("/graphics/warning.png")));

        // Building the window
        VBox layout = new VBox(10);
        HBox closeBox = new HBox();
        closeBox.getChildren().addAll(closeBtn);
        closeBox.setAlignment(Pos.CENTER_RIGHT);
        closeBox.setPadding(new Insets(5,5,5,5));
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(warningImage, warningLabel);
        hBox.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hBox,closeBox);
        layout.setAlignment(Pos.CENTER);

        // Showing the window
        Scene scene = new Scene(layout);
        scene.getStylesheets().addAll("/graphics/css/"+theme+".css");
        window.setScene(scene);
        window.setHeight(200.00);
        window.setWidth(550.00);
        window.show();
    }
}
