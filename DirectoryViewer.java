import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class DirectoryViewer extends Application {
    ImageView d = new ImageView();
    TreeView<String> a = new TreeView<String>();
    File choice;
//    Image image = new Image("");
    String str;
    @Override
    public void start(Stage primaryStage) {


        BorderPane b = new BorderPane();
        d.setFitWidth(500);
        d.setFitHeight(500);
//        int width = 60;
//        int height = 60;
        //LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        //d.setLayoutParams(parms);

        d.setPreserveRatio(true);

        Button c = new Button("Load Folder");
        Button open = new Button("Open Folder");


        c.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                DirectoryChooser dc = new DirectoryChooser();
                dc.setInitialDirectory(new File(System.getProperty("user.home")));
                choice = dc.showDialog(primaryStage);
                if (choice == null || !choice.isDirectory()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("The file is invalid.");
                    alert.showAndWait();
                } else {
                    a.setRoot(getNodesForDirectory(choice));
                }

            }
        });

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Runtime.getRuntime().exec("explorer.exe /select, " + str);
                    System.out.println("opner " + str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        b.setTop(c);
        b.setLeft(open);
        b.setCenter(a);
        b.setRight(d);

        a.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println("Re " + choice);
                    str = "file:///" + choice + "\\" + newValue.getValue();
                    System.out.printf(str);
                    Image image = new Image("file:///" + choice + "\\" + newValue.getValue() + "\\" + "game.jpg");
                    //The above game part needs to be changed so that it can read any image file under that.
                    d.setImage(image);
                    System.out.println("Selected Text : " + newValue.getValue());}
                );

        primaryStage.setScene(new Scene(b, 800, 500));
        primaryStage.setTitle("Folder View");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for (File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) {
                root.getChildren().add(new TreeItem<String>(f.getName()));
                System.out.println(directory.getName());
                System.out.println(f.getName());
            }
        }
        return root;
    }
}