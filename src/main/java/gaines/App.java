package gaines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class App extends Application {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        log.info("Starting Hello JavaFX and Maven demonstration application");

        final String html = "/html/index.html";
        log.debug("Loading main view from: {}", html);

        final WebView root = new WebView();
        String url = getClass().getResource(html).toExternalForm();
        log.debug("Content loaded from: " + url);
        root.getEngine().load(url);
        root.setZoom(Screen.getPrimary().getDpi() / 96);

        log.debug("Showing JFX scene");
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Hello JavaFx, HTML5, and Maven");
        stage.setScene(scene);
        stage.show();
    }
}
