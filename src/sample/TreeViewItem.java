package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by xyntherys on 4/26/17.
 */

public class TreeViewItem extends TreeItem {

    public File file;
    public String fileName;
    public String filePath;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;

    public TreeViewItem(File f, String fn, ImageView iv) {
        super(fn, iv);
        this.file = f;
        this.fileName = fn;
        filePath = f.getAbsolutePath();
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this, file));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            isLeaf = file.isFile();
        }

        return isLeaf;
    }

    @Override
    public String toString(){
        return filePath;
    }


    private ObservableList<TreeItem<String>> buildChildren(TreeItem<String> TreeItem, File f) {
        //File f = TreeItem.getValue();

        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<TreeItem<String>> children = FXCollections
                        .observableArrayList();

                for (File childFile : files) {

                    children.add(new TreeViewItem(childFile, childFile.getName(), new FileIcon(childFile).getImageView()));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }



}
