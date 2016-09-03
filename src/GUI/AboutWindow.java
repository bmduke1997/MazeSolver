package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * AboutWindow
 * This is a customizable about window.
 * @author Patrick Shinn
 * @version 9/2/16
 */
public class AboutWindow {
    private String windowName ,
            devoloper,
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
        this.devoloper = developer;
        this.windowName = website;
    }
    public static void display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
    }
}
