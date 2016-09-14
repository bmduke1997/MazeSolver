package GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Map Thing");
        Controller controller = new Controller();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root, 1070, 630));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/AppIcon.png")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
