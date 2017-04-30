package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by xyntherys on 4/28/17.
 */
public class TilesViewItem extends VBox {
    public File file;
    public String fileName;
    public String filePath;

    public TilesViewItem(File file, String fileName) {
        super();
        this.file = file;
        this.fileName = fileName;
        this.filePath = file.getAbsolutePath();

        super.getChildren().addAll(new FileIcon(file).getImageView(), new Label(fileName));
        super.setAlignment(Pos.CENTER);
        super.setMaxSize(120,80);
        super.setPrefSize(80, 80);
    }

    @Override public String toString(){
        return filePath;
    }


}
