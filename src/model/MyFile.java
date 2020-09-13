package model;

import javafx.beans.property.*;
import utility.FileInforFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

public class MyFile  {
    private final StringProperty fileName = new SimpleStringProperty();//文件名
    private final StringProperty type = new SimpleStringProperty();//文件类型
    private final StringProperty modifyTime = new SimpleStringProperty();//修改时间
    private final StringProperty createTime = new SimpleStringProperty();//创建时间
    private final StringProperty fSize = new SimpleStringProperty();//文件大小
    private File myFile;//文件

    public String getModifyTime() {
        return modifyTime.get();
    }

    public StringProperty modifyTimeProperty() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime.set(modifyTime);
    }

    public String getCreateTime() {
        return createTime.get();
    }

    public StringProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime.set(createTime);
    }

    public String getfSize() {
        return fSize.get();
    }

    public StringProperty fSizeProperty() {
        return fSize;
    }

    public void setfSize(String fSize) {
        this.fSize.set(fSize);
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    public MyFile(File file) {
        this.myFile = new File(file.getPath());
        this.fileName.set(file.getName());
        this.type.set(file.getName().substring(file.getName().lastIndexOf(".")));

//        BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(Paths.get(file.getPath()),
//                BasicFileAttributeView.class,
//                LinkOption.NOFOLLOW_LINKS);
//        BasicFileAttributes basicFileAttributes = basicFileAttributeView.readAttributes();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime.set(sdf.format(file.lastModified()));
        this.fSize.set(FileInforFormat.getPrintSize(file.length()));

    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

}
