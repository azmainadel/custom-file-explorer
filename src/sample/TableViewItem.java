package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by xyntherys on 4/28/17.
 */

public class TableViewItem extends File {
    public File file;
    public String fileName;
    public long fileSize;
    public String dateModified;
    public String filePath;
    public ImageView imageView;

    public TableViewItem(File f) {
        super(String.valueOf(f));
        file = f;
        filePath = f.getAbsolutePath();
        fileName = f.getName();
        fileSize = f.length();
        dateModified = new SimpleDateFormat("dd/MM/yyyy").format(f.lastModified());
        imageView = new FileIcon(f).getImageView();

    }

    @Override public String getPath(){
        return filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
