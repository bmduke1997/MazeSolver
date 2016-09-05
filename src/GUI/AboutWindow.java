package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * AboutWindow
 * This is a customizable about window.
 * @author Patrick Shinn
 * @version 9/2/16
 */

// // TODO: 9/5/16 FORMAT THIS SHIT TO MAKE IT LOOK NICE 
public class AboutWindow {
    private String windowName ,
            developer,
            version,
            appName,
            website,
            aboutApp;
    public AboutWindow(String windowName, String appName,
                       String version, String aboutApp,
                       String developer, String website){
        this.windowName = windowName;
        this.appName = appName;
        this.version = version;
        this.aboutApp = aboutApp;
        this.developer = developer;
        this.website = website;
    }
    public void display(){
        // Stage setup
        Stage window = new Stage();
        window.setTitle(windowName);
        window.initModality(Modality.APPLICATION_MODAL); // means that while this window is open, you can't interact with the main program.
        
        // Buttons
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close()); // sets the button to close the window
        
        // Labels
        Label appNameLabel = new Label(appName);
        Label websiteLabel = new Label("Website: " + website);
        Label aboutAppLabel = new Label(aboutApp);
        Label developerLabel = new Label("Developers: " + developer);
        Label versionLabel = new Label("Version: " + version);
        
        // Layout type
        VBox layout = new VBox(10);
        layout.getChildren().addAll(appNameLabel, developerLabel, versionLabel, aboutAppLabel, websiteLabel);

        // Building scene and displaying.
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setHeight(200.00);
        window.setWidth(500.00);
        window.show();
    }
}
