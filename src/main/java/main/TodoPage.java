package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class TodoPage extends Application {
    public void start(Stage stage) throws IOException {
        URL location = TodoPage.class.getClassLoader().getResource("todo.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Scene todoScene = new Scene(fxmlLoader.load(), 400, 600);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(todoScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}