package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * @author vincent
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("批量更名器1.1 By vincent");
        primaryStage.setScene(new Scene(root, 950, 400));
        primaryStage.getIcons().add(
                new Image("/img/mainIco.png"));
        primaryStage.setResizable(false);
        //关闭主窗体关闭按钮的监听事件：使子线程随主窗体一同关闭
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);

    }

}
