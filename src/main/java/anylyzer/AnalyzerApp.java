package anylyzer;

import anylyzer.controllers.InitApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AnalyzerApp extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            setMainStage(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Sample.fxml"));
            loader.load();

            InitApplicationController display = loader.getController();


            Parent root = loader.getRoot();
            primaryStage.setTitle("Student marks com.analyzer");
            primaryStage.setScene(new Scene(root, 600, 150));
            primaryStage.getIcons().add(new Image("/Images/checklist-256.ico"));


            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(AnalyzerApp.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        AnalyzerApp.mainStage = mainStage;
    }

}
