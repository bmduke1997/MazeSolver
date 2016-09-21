package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("aMAZEing Solver");
        Controller controller = new Controller();
        Scene scene = new Scene(root, 1261, 903);
        controller.setStage(primaryStage, scene);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/AppIcon.png")));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
