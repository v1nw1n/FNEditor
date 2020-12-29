package model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Controller;

import javax.xml.bind.annotation.XmlElement;
import java.io.BufferedReader;
import java.time.LocalDateTime;

/**
 * @author vincent
 */
public class ButtonEnableStatusListener extends Thread{
    @FXML
    public TableView<MyFile> fileTableView;
    @FXML
    public TextField str1;
    /**  绑定触发对象**/
    public TableView tableView;
    /** 状态变更对象  **/
    Button[] buttons;

    public ButtonEnableStatusListener() {}

    public ButtonEnableStatusListener(TableView tableView, Button[] buttons) {
        this.tableView = fileTableView;

        this.buttons = buttons;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println(LocalDateTime.now()+"::::"+str1.getText());
//                if(tableView.getItems().size()<=0){
//                    for (Button b:this.buttons){
//                        b.setDisable(false);
//                    }
//                }else{
//                    for (Button b:this.buttons){
//                        b.setDisable(true);
//                    }
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("count end");
            }
        }
    }
}
