package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.MyFile;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Controller {


    public Button loadButton;
    public Button lookThrough;
    public TextField dirInput;
    public TableView<MyFile> fileTableView;
    public TableColumn<MyFile,String> fName;
    public TableColumn<MyFile,String> fType;
    public TableColumn<MyFile,String> modifyTime;
    public TableColumn<MyFile,String> fSize;
    public TextField str1;
    public TextField str2;
    public TextField str3;
    public Button matchButton;
    public Button updateButton;
    public Button replaceButton;

    /**
     * 装载表格 wired the TableView object
     * @param dir 装载对象的路径(表格的文件对象从这个目录路径装载)
     */
    private void initTableData(File dir){
        ObservableList<MyFile> fileList = FXCollections.observableArrayList();
        File[] files = dir.listFiles();
        if(files!=null){
            for(File f:files){
                MyFile mf = new MyFile(f);
                fileList.add(mf);
            }
        }
        //添加文件对象到表格视图 add file object to table view
        fName.setCellValueFactory(new PropertyValueFactory<>("fileName"));//参数为数据模型的属性字段 the assignment is property field of the data model
        fType.setCellValueFactory(new PropertyValueFactory<>("type"));
        fSize.setCellValueFactory(new PropertyValueFactory<>("fSize"));
        modifyTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        fileTableView.setItems(fileList);
        //初始化按钮 init button to disabled
        matchButton.setDisable(false);

    }
    /**
     * 打开文件夹选择器并自动载入目录，向表格载入文件对象 open directoryChooser to choose the directory and load dir automatically(load file object for table view
     * @param actionEvent 事件类型
     * @throws IOException 抛出异常
     */
    public void openExplorer(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource().equals(lookThrough)) {

            Process process = Runtime.getRuntime().exec("cmd /C echo %userprofile%");//get user's directory
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String dirStr;//从cmd执行结果得到的初始化路径 get initial path from the result of cmd.exe
            File dir;


            DirectoryChooser directoryChooser = new DirectoryChooser();//目录选择器 init directoryChooser
            directoryChooser.setTitle("载入目录");//初始化窗口标题 init title

            if ((dirStr = br.readLine()) != null) {
                dir = new File(dirStr);//初始化目录对象 initialize directory object
                directoryChooser.setInitialDirectory(dir);//初始化选择路径 initialize default selected directory
            }
            File selectedDir = directoryChooser.showDialog(new Stage());
            if(selectedDir!=null) {
                dirInput.setText(selectedDir.getPath());//反馈路径到路径输入框 callback the directory to the input field
                initTableData(selectedDir);//装载文件表格 wired table view
            }
        }
    }
    /**
     * 手动载入目录
     * @param actionEvent 事件类型
     */
    public void loadDir(ActionEvent actionEvent) {
        if(actionEvent.getSource()!=loadButton) return ;
        String dirStr = dirInput.getText();
        File dir = new File(dirStr);
        if(dirStr.equals("")||!dir.isDirectory()){
            showAlert("错误","载入出错","无法载入一个不正确的目录");
            return;
        }
        initTableData(dir);
    }
    /**
     * 匹配表格中文件名包含匹配字串的文件对象，并重新装载表格 match the file object that contains inputted string in the table view and rewired the table view
     * @param actionEvent 事件类型
     */
    public void matchFileObject(ActionEvent actionEvent) {
        if(actionEvent.getSource()!=matchButton){
            return ;
        }
        String matchStr = str1.getText();
        if(matchStr.equals("")){
            showAlert("错误","匹配错误","匹配字串为空");
            return;
        }
        ObservableList<MyFile> notMatchFileList =  FXCollections.observableArrayList();
        ObservableList<MyFile> observableList = fileTableView.getItems();

        //反向匹配
        for(MyFile mf : observableList){
            String fileName = mf.getFileName();
            if(!fileName.contains(matchStr)){
                notMatchFileList.add(mf);

            }
        }
        observableList.removeAll(notMatchFileList);//删除表格的可视列表中的不匹配文件对象 delete the unmatched object of the table view
        matchButton.setDisable(true);//禁用匹配按钮 disabled the match button
        str1.clear();
    }
    /**
     * 用新字串(newStr)替换表格中文件名包含旧字串(oldStr)的文件(以替换方式更名) replace the old string by new string in the file name
     * @param actionEvent 事件类型
     */
    public void replaceFileName(ActionEvent actionEvent) {
        if(actionEvent.getSource()!=replaceButton) return;
        String oldStr = str1.getText();//旧字段 old string
        String newStr = str3.getText();//新字段 new string
        Optional<ButtonType> result;
        if(oldStr.equals("")||newStr.equals("")){
            result = showAlert("警告",
                    "空字段",
                    "主要字段存在空字段，这是您希望的吗？");
        }else{
            result = showAlert("警告",
                    "注意",
                    "操作无法从本工具撤销，请检查操作对象与相关字段无误后确定！");
        }
        if(result.isPresent()&&result.get() !=ButtonType.OK) return;
        ObservableList<MyFile> observableList = fileTableView.getItems();
        for(MyFile mf:observableList){
            if(mf.getFileName().contains(oldStr)){
                String newFileName = mf.getMyFile().getPath().replace(oldStr,newStr);
                //更名 rename
                File dest = new File(newFileName);
                File targetFile = mf.getMyFile();
                if(targetFile.renameTo(dest)){
                    mf.setFileName(dest.getName());
                    mf.setMyFile(dest);
                }
            }
        }
        str1.clear();
        str3.clear();

    }
    /**
     * 格式化文件名 组合字段1+序列+组合字段2  the format of file name is filed1 + index + filed2
     * @param actionEvent 触发的事件
     */
    public void formatFileName(ActionEvent actionEvent) {
        if(actionEvent.getSource()!=updateButton){
            return ;
        }

        ObservableList<MyFile> observableList = fileTableView.getItems();
        String name1 = str1.getText();//文件名组合1
        String indexStr = str2.getText();//起始序列
        String name2 = str3.getText();//文件名组合2
        Optional<ButtonType> result;
        int index ;
        try{
            index = Integer.parseInt(indexStr);
        }catch (NumberFormatException nfe){
            showAlert("错误","输入格式","第二字段必须为数字作为您的起始序号");
            return ;
        }
        if(name1.equals("")||indexStr.equals("")){
            result = showAlert("警告",
                    "空字段",
                    "主要字段存在空字段，这是您希望的吗？");
        }else{
            result = showAlert("警告",
                    "注意",
                    "操作无法从本工具撤销，请检查操作对象与相关字段无误后确定！");

        }
        if(result.isPresent()&&result.get()!= ButtonType.OK) return ;

        for(MyFile mf:observableList){

            String newFilenamePath = mf.getMyFile().getParent()+"\\"+
                    name1+
                    index+name2+mf.getType();
//                    mf.getMyFile().getName().substring(mf.getMyFile().getName().lastIndexOf("."));//拼接后缀 append suffix
            //更名 rename
            File dest = new File(newFilenamePath);
            File targetFile = mf.getMyFile();
            if(targetFile.renameTo(dest)){
                mf.setFileName(dest.getName());
                mf.setMyFile(dest);
                index++;
            }

        }
        //刷新视图 refresh text filed
        str1.clear();
        str2.clear();
        str3.clear();
    }
    /**
     * 弹出一个警告框 pop up a alert dialog
     * @param title 警告框标题
     * @param headTitle 信息标题
     * @param contentText 信息内容
     * @return 返回按钮类型
     */
    private Optional<ButtonType> showAlert(String title, String headTitle, String contentText){
        //确认 错误 警告 信息
        Alert alert;
        switch (title){
            case "警告" :
                alert = new Alert(Alert.AlertType.CONFIRMATION);
            break;
            case "错误" :
                alert = new Alert(Alert.AlertType.ERROR);
            break;
            case "信息" :
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            default:
                alert = new Alert(Alert.AlertType.NONE);

        }
        alert.setTitle(title);
        alert.setHeaderText(headTitle);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
}
