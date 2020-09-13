package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("批量更名器1.0 By vincent");
        primaryStage.setScene(new Scene(root, 730, 400));
        primaryStage.getIcons().add(
                new Image("/img/mainIco.png"));
        primaryStage.setResizable(false);


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
