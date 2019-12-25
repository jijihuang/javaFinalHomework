package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url=getClass().getClassLoader().getResource("sample.fxml");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(url);
        Parent root = loader.load(url);
        primaryStage.setTitle("Huluwa battle");
        primaryStage.setScene(new Scene(root, 1300, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
