import java.io.File;

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

    @Override
    public void start(Stage primaryStage) {
        TreeView<String> a = new TreeView<String>();
        BorderPane b = new BorderPane();
        ImageView d = new ImageView();
        d.setFitWidth(500);
        d.setFitHeight(500);
//        int width = 60;
//        int height = 60;
        //LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        //d.setLayoutParams(parms);

        d.setPreserveRatio(true);

        Button c = new Button("Load Folder");

        c.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DirectoryChooser dc = new DirectoryChooser();
                dc.setInitialDirectory(new File(System.getProperty("user.home")));
                File choice = dc.showDialog(primaryStage);
                if(choice == null || ! choice.isDirectory()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("The file is invalid.");
                    alert.showAndWait();
                } else {
                    a.setRoot(getNodesForDirectory(choice));
                }
                Image image = new Image(new File("file:///" + choice + "\\" + "game.jpg").toString());
                d.setImage(image);
            }
        });
        b.setTop(c);
        b.setCenter(a);
        b.setRight(d);

        primaryStage.setScene(new Scene(b, 800, 500));
        primaryStage.setTitle("Folder View");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for(File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }
}