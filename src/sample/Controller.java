package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;



public class Controller implements Initializable{

    public int viewType = 2; //1 for details view, 2 for tiles view
    public String path;


    @FXML
    private TreeView<String> treeView;

    @FXML
    private Button tilesViewButton;

    @FXML
    private TextField filePath;

    @FXML
    private Button backButton;

    @FXML
    private TableView<TableViewItem> tableView;

    @FXML
    private TableColumn<TableViewItem, ImageView> icon;

    @FXML
    private TableColumn<TableViewItem, String> filename;

    @FXML
    private TableColumn<TableViewItem, Long> filesize;

    @FXML
    private TableColumn<TableViewItem, Long> datemodified;

    @FXML
    private Button tableViewButton;

    @FXML
    private ScrollPane tilesScroll;

    @FXML
    private TilePane tilesView;

    private ObservableList<TableViewItem> observableList = FXCollections.observableArrayList();

    @FXML
    void switchToTiles(javafx.scene.input.MouseEvent event) {
        //viewType = 2;
        tilesScroll.setVisible(true);
        tilesView.setVisible(true);
        tableView.setVisible(false);
        makeTilesView();
    }

    @FXML
    void switchToTable(javafx.scene.input.MouseEvent event) {
        //viewType = 1;
        tilesView.setVisible(false);
        tilesScroll.setVisible(false);
        tableView.setVisible(true);
        makeTableView();
    }

    @FXML
    void goBack(javafx.scene.input.MouseEvent event) {
        File file = new File(path);
        path = file.getParentFile().getAbsolutePath();
        filePath.setText(path);

        makeTilesView();
        makeTableView();
    }

    @FXML
    void expandTree(javafx.scene.input.MouseEvent event) {
        if(event.getClickCount() == 2){
            TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();

            treeItem.setExpanded(false);
            filePath.setText(treeItem.toString());
            path = treeItem.toString();

            makeTilesView();
            makeTableView();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setVisible(false);
        tilesScroll.setVisible(true);

        path = Paths.get("").toAbsolutePath().toString();
        filePath.setText(path);
        makeTilesView();
//      makeTableView();

        Image image = new Image("icon.png", 25, 25, true, true);
        ImageView imageView = new ImageView(image);

        TreeItem<String> root;

        File[] list;
        list = File.listRoots();

        root = new TreeItem(list[0].getAbsolutePath(), imageView);
        list = list[0].listFiles();

        for(File f: list){
            root.getChildren().add(new TreeViewItem(f, f.getAbsolutePath(), getImageView(f)));
        }


        treeView.setRoot(root);

        tilesView.setVgap(5);
        tilesView.setHgap(20);
        tilesView.setPrefColumns(5);
        tilesView.setTileAlignment(Pos.CENTER);
        tilesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tilesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tilesScroll.setFitToHeight(true);
        tilesScroll.setFitToWidth(true);
        tilesScroll.setContent(tilesView);

        filename.setCellValueFactory(new PropertyValueFactory<TableViewItem, String>("fileName"));
        filesize.setCellValueFactory(new PropertyValueFactory<TableViewItem, Long>("fileSize"));
        datemodified.setCellValueFactory(new PropertyValueFactory<TableViewItem, Long>("dateModified"));
        icon.setCellValueFactory(new PropertyValueFactory<TableViewItem, ImageView>("imageView"));

        tableView.setItems(observableList);

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TableViewItem tableViewItem = tableView.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2) {
                    filePath.setText(tableViewItem.getPath());

                    if (tableViewItem != null) {
                        path = tableViewItem.getPath();
                        makeTableView();
                    }
                }
            }

        });

    }



    public void makeTilesView(){
        File file = new File(path);

        if(!file.isFile()){
            File[] list;
            list = file.listFiles();

            tilesView.getChildren().clear();

            if(list != null){
                for(File file1: list){
                    if(list != null){
                        String filename = file1.getName();
                        TilesViewItem tilesViewItem = new TilesViewItem(file1, filename);

                        tilesViewItem.setOnMouseClicked(event -> {
                            if(event.getClickCount() == 2){
                                path = tilesViewItem.toString();
                                filePath.setText(path);
                                makeTilesView();
                            }

                        });

                        tilesView.getChildren().add(tilesViewItem);
                    }
                }
            }
        }
    }


    public void makeTableView(){
        observableList.clear();
        File file = new File(path);

        if(!file.isFile()){
            File[] list;
            list = file.listFiles();

            for(File f: list){
                TableViewItem tableViewItem = new TableViewItem(f);
                observableList.add(tableViewItem);
            }
        }

        tableView.setItems(observableList);

    }

    public ImageView getImageView(File f){
        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(f);

        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        ImageView iv = new ImageView(fxImage);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setFitWidth(18);

        return iv;
    }


}
