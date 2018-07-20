
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 *
 * @author amura
 */
public class JavaFXApplication1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
   
        JFXButton btn = new JFXButton();
        btn.setText("Start Updating Data");
        btn.setStyle("-fx-padding: 0.7em 0.57em;\n" +
"    -fx-font-size: 14px;\n" +
"    -jfx-button-type: RAISED;\n" +
"    -fx-background-color: rgb(77,102,204);\n" +
"    -fx-pref-width: 200;\n" +
"    -fx-text-fill: WHITE;");
    
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                tally_request r = new tally_request();
                try {
                    r.SendToTally();
                    r.sendToGoDaddy();
                    r.SendToTallyBatch();
                    r.sendToGoDaddyBatch();
                } catch (IOException ex) {
                    Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Started Updating");
            }
        });
        btn.setLayoutX(70);
        btn.setLayoutY(100);
        Button btn1 = new Button();
        btn1.setText("Stop Updating Data");
              btn1.setStyle("-fx-padding: 0.7em 0.57em;\n" +
"    -fx-font-size: 14px;\n" +
"    -jfx-button-type: RAISED;\n" +
"    -fx-background-color: rgb(77,102,204);\n" +
"    -fx-pref-width: 200;\n" +
"    -fx-text-fill: WHITE;");
 
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Stopped Updating");
            }
        });
        
        btn1.setLayoutX(70);
        btn1.setLayoutY(160);
        
       Group group = new Group(btn1 , btn);
        Scene scene = new Scene(group, 300, 250);
        
        primaryStage.setTitle("Khurana Sales!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
