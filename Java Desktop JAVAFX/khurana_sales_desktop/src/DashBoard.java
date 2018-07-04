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
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author amura
 */
public class DashBoard extends Application {
    
    
    
    public VBox Card(String title, int index, String color)
    {
        VBox vbox = new VBox();
        vbox.setPrefSize(20, 220);
        HBox hbox = new HBox();
        hbox.setPrefWidth(220);
        hbox.setPrefHeight(45);
        hbox.setPadding(new Insets(10,20,10,20));
        hbox.setStyle("-fx-background-color: "+color+";\n");
        hbox.setAlignment(Pos.CENTER);
        Text text = new Text("      "+title);
        text.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        text.setFill(Paint.valueOf("#ffffff"));
        text.setFont(Font.font("Arial black", FontPosture.REGULAR, 15));
        Image image  = new Image(khurana_final.class.getResource("/icons/"+index+".png").toExternalForm());
        ImageView imageview = new ImageView(image);
        hbox.getChildren().add(imageview);
        hbox.getChildren().add(text);
        vbox.setStyle("-fx-background-radius: 300%;\n"+"-fx-background: #ffffff;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        vbox.getChildren().add(hbox);
        HBox hbox_new = new HBox();
        hbox_new.setMinHeight(250);
        hbox_new.setMinWidth(300);
        hbox_new.setStyle("-fx-background-color: White;\n"+"-fx-background-radius: 0 0 9 9;\n" + 
    	        "    -fx-border-radius: 0 0 9 9;");
        vbox.getChildren().add(hbox_new);
        vbox.setMinSize(300, 200);
        return vbox;
    }
    private VBox sidePane()
    {
       VBox vbox  = new VBox();
       vbox.setPrefWidth(230);
       vbox.setSpacing(20);
       BorderPane pane =new BorderPane();
       Image image = new Image(khurana_final.class.getResource("/icons/account.png").toExternalForm());
       ImageView imageview = new ImageView(image);
       imageview.setFitHeight(40);
       imageview.setFitWidth(40);
       pane.setCenter(imageview);
       pane.setPrefSize(230, 50);
       HBox box = new HBox();
       box.setStyle("-fx-background-color: Blue");
       box.setPrefSize(230, 110);
       box.getChildren().add(pane);
       vbox.getChildren().add(box);
         Text text = new Text("   My Account");
       text.setFill(Paint.valueOf("#ffffff"));
       vbox.getChildren().add(text);
     
       String [] texts = {"Login","SignUp","MyCart","Account"};
       VBox account_vbox = new VBox();
       for(int i = 1;i <5;i++)
       {
           account_vbox.getChildren().add(Item(i,texts[i-1]));
       }
       vbox.getChildren().add(account_vbox);
       vbox.setStyle("-fx-background-color:#2d3041");
       Text text1 = new Text("   My Security");
       text1.setFill(Paint.valueOf("#ffffff"));
       vbox.getChildren().add(text1);
       VBox security_vbox = new VBox();
       String security_array[] = {"Forgot", "Set Key"};
       for(int i = 5;i <7;i++)
       {
           security_vbox.getChildren().add(Item(i,security_array[i-5]));
       }
       vbox.getChildren().add(security_vbox);
       Text text2 = new Text("   App Support");
       text2.setFill(Paint.valueOf("#ffffff"));
       vbox.getChildren().add(text2);
       String support_array[] = {"Help!", "Share", "Cst Care", "About", "Logout"};
       VBox support_vbox = new VBox();
       for(int i = 7;i <12;i++)
       {
           support_vbox.getChildren().add(Item(i,support_array[i-7]));
       }
       vbox.getChildren().add(support_vbox);
       return vbox;
    }
    
    public void menuDecorator(Button btn, Pane pane , HBox box_text)
    {
        btn.setOnMouseEntered(value->{btn.setStyle("-fx-background-color: black");
        pane.setStyle("-fx-background-color: blue");box_text.setStyle("-fx-background-color: black");});
        btn.setOnMouseExited(value->{btn.setStyle("-fx-background-color: #212121");
        pane.setStyle("-fx-background-color: #212121");box_text.setStyle("-fx-background-color: #212121");});
        box_text.setOnMouseEntered(value->{btn.setStyle("-fx-background-color: black");
        pane.setStyle("-fx-background-color: blue"); box_text.setStyle("-fx-background-color: black");});
        box_text.setOnMouseExited(value->{btn.setStyle("-fx-background-color: #212121");
        pane.setStyle("-fx-background-color: #212121");box_text.setStyle("-fx-background-color: #212121");});
        
    }
    private HBox Item(int index, String text)    
    {
        
        Image image;
        image = new Image(khurana_final.class.getResource("/icons/"+index+".png").toExternalForm());
        ImageView imageView = new ImageView(image);
        Button btn = new Button();
        btn.setStyle("-fx-background-color: #212121");
        Pane paneIndicator = new Pane();
        paneIndicator.setPrefSize(5, 50);
        paneIndicator.setStyle("-fx-background-color: #212121");
        btn.setGraphic(imageView);
        btn.setPrefWidth(120);
        btn.setPrefHeight(50);
        Text text_elem = new Text(text);
        text_elem.setFill(Paint.valueOf("#ffffff"));
        HBox box_text = new HBox();
        box_text.setPrefSize(100, 50);
        box_text.setAlignment(Pos.CENTER_LEFT);
        box_text.getChildren().add(text_elem);
        box_text.setStyle("-fx-background-color: #212121");
        menuDecorator(btn,paneIndicator,box_text);
        
        HBox box = new HBox();
        box.getChildren().add(paneIndicator);
        box.getChildren().add(btn);
        box.getChildren().add(box_text);
        return box;
    }
    @Override
    public void start(Stage primaryStage) {
        HBox pane = new HBox();
        pane.getChildren().add(sidePane());
        
        Line line = new Line();
        line.setStartX(50);
        line.setStartY(100);
        line.setEndX(650);
        line.setEndY(100);
        Text text = new Text("Welcome To Khurana Sales !");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.NORMAL, 30));
        text.setX(100);
        text.setY(50);
        
        Text text1 = new Text("Empowering Multi-Business Solution");
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.MEDIUM, 15));
        text1.setX(220);
        text1.setY(75);
        
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
                System.out.println("Started Updating");
            }
        });
        btn.setLayoutX(70);
        btn.setLayoutY(120);
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
        btn1.setLayoutY(180);
        
        Group group = new Group(btn1 , btn, line, text, text1);
        //   pane.getChildren().add(group);
        
        pane.setSpacing(20);
        Text dashboard_title  = new Text("My DashBoard");
        text.setFont(Font.font("Arial black", FontPosture.REGULAR, 20));
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 5, 0, 0));
        gridPane.add(dashboard_title,1, 0);
        Line line_1 = new Line();
        line_1.setStyle("-fx-stroke: blue");
        line_1.setStartX(10);
        line_1.setStartY(20);
        line_1.setEndX(900);
        line_1.setEndY(20);
        gridPane.add(line_1, 1, 2);
        Circle circle = new Circle();
        circle.setRadius(55);
        circle.setCenterX(20);
        circle.setCenterY(20);
        circle.setStyle("-fx-stroke: blue");
        circle.setFill(Paint.valueOf("#3F51B5"));
        Text welcome_text = new Text("Welcome !");
        welcome_text.setFont(Font.font("Arial"));
        welcome_text.setStyle("-fx-stroke: #ffffff");
        welcome_text.setBoundsType(TextBoundsType.VISUAL); 
        StackPane stack = new StackPane();
        stack.getChildren().add(circle);
        stack.getChildren().add(welcome_text);
        stack.setMargin(circle, new Insets(-30,0,60,0));
        stack.setMargin(welcome_text, new Insets(-30,0,60,0));
        
        gridPane.add(stack, 2, 2);
        gridPane.setMargin(line_1, new Insets(-30,0,50,0));
        GridPane inner_grid = new GridPane();
        inner_grid.setVgap(15);
        inner_grid.setHgap(22);
        VBox card_generate_reports = Card("Generate Reports",14,"#1A237E" ); 
        VBox card_manage_account = Card("Manage Accounts",13,"#673AB7"); 
        
        inner_grid.add(card_generate_reports, 0, 0);
        inner_grid.setMargin(card_generate_reports, new Insets(-90,0,80,0));
        inner_grid.add(card_manage_account, 1, 0);
        inner_grid.setMargin(card_manage_account, new Insets(-90,0,80,0));
        inner_grid.add(Card("Perform Operations",12,"#C51162" ), 0, 1);
        inner_grid.add(Card("Generate Notes",15,"#2196F3"), 1, 1);
        gridPane.add(inner_grid, 1, 3);
        pane.getChildren().add(gridPane);
        Scene scene = new Scene(pane, 1500, 1000);
        primaryStage.setTitle("Khurana Business Solutions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
