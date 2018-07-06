

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;  
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author amura
 */
public class LoginPage extends Application {
	
	 public VBox Card(String title, int index, String color)
	    {
	        VBox vbox = new VBox();
	        vbox.setPrefSize(10, 200);
	        HBox hbox = new HBox();
	        hbox.setPrefWidth(180);
	        hbox.setPrefHeight(45);
	        hbox.setPadding(new Insets(10,20,10,20));
	        hbox.setStyle("-fx-background-color: "+color+";\n"+"-fx-background-radius: 10 10 0 0;\n");
	        hbox.setAlignment(Pos.CENTER);
	        Text text = new Text("      "+title);
	        text.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
	        text.setFill(Paint.valueOf("#ffffff"));
	        Image image  = new Image(khurana_final.class.getResource("/icons/"+index+".png").toExternalForm());
	        ImageView imageview = new ImageView(image);
	        hbox.getChildren().add(imageview);
	        hbox.getChildren().add(text);
	        vbox.setStyle("-fx-background-radius: 300%;\n"+"-fx-background: #ffffff;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
	        vbox.getChildren().add(hbox);
	        HBox hbox_new = new HBox();
	        hbox_new.setMinHeight(285);
	        hbox_new.setId("my_hbox");
	        hbox_new.setStyle("-fx-background-color: White;\n"+"-fx-background-radius: 0 0 18 18;\n" + 
	        "    -fx-border-radius: 0 0 18 18;");
	        vbox.getChildren().add(hbox_new);
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
	       box.setStyle	("-fx-background-color: Blue");
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
	        paneIndicator.setPrefSize(15, 30);
	        paneIndicator.setStyle("-fx-background-color: #212121");
	        btn.setGraphic(imageView);
	        btn.setPrefWidth(150);
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
	    
	    public BorderPane signup_box()
	    {
	    	 BorderPane pane = new BorderPane();
	    	 pane.setPrefWidth(300);
	    	 TextField textField = new TextField();
	    	 textField.setMaxWidth(200);
	    	 textField.setPadding(new Insets(10,10,10,10));
	         VBox name_box = new VBox();
	         Text tex = new Text();
	         tex.setText("Name");
	         tex.setFill(Color.BLACK);
	         name_box.getChildren().add(tex);
	         name_box.getChildren().add(textField);
	         name_box.setMargin(textField, new Insets(10,0,0,0));
	         
	         
	         
	         VBox box_password_email = new VBox();
	         
	         TextField textField3 = new TextField();
	    	 textField3.setMaxWidth(200);
	    	 textField3.setPadding(new Insets(10,10,10,10));
	         VBox email_box = new VBox();
	         Text tex3 = new Text();
	         tex3.setText("Email");
	         tex3.setFill(Color.BLACK);
	         email_box.getChildren().add(tex3);
	         email_box.getChildren().add(textField3);
	         email_box.setMargin(textField3, new Insets(10,0,0,0));
	         
	         
	         PasswordField textField2 = new PasswordField();
	    	 textField2.setMaxWidth(200);
	    	 textField2.setPadding(new Insets(10,10,10,10));
	         VBox password_box = new VBox();
	         Text tex2 = new Text();
	         tex2.setText("Password");
	         tex2.setFill(Color.BLACK);
	         password_box.getChildren().add(tex2);
	         password_box.getChildren().add(textField2);
	         password_box.setMargin(textField2, new Insets(10,0,0,0));
	         
	         box_password_email.getChildren().add(email_box);
	         box_password_email.getChildren().add(password_box);
	         
	         box_password_email.setMargin(email_box, new Insets(0,0,0,0));
	         box_password_email.setMargin(password_box, new Insets(20,0,0,0));
	         
	         Button button = new Button("Sign Up");
	         button.setStyle("-fx-padding: 0.7em 0.57em;\n" +
	        		 "    -fx-font-size: 14px;\n" +
	        		 "    -jfx-button-type: RAISED;\n" +
	        		 "    -fx-background-color: rgb(77,102,204);\n" +
	        		 "    -fx-pref-width: 120;\n" +
	        		 "    -fx-text-fill: WHITE;");
	         
	         pane.setTop(name_box);
	         pane.setCenter(box_password_email);
	         pane.setBottom(button);
	         
	         pane.setMargin(name_box, new Insets(50,10,10,50));
	         pane.setMargin(box_password_email, new Insets(10,10,10,50));
	         pane.setMargin(button, new Insets(20,10,10,90));
	            
	         return pane;
	    }
	    public BorderPane login_box()
	    {
	    	 BorderPane pane = new BorderPane();
	    	 pane.setPrefWidth(300);
	    	 TextField textField = new TextField();
	    	 textField.setMaxWidth(200);
	    	 textField.setPadding(new Insets(10,10,10,10));
	    	 textField.setDisable(false);
	    	 textField.setEditable(true);
	    	 textField.setPromptText("ankushkhurana34@gmail.com");
	         
	         VBox email_box = new VBox();
	         Text tex = new Text();
	         tex.setText("Email");
	         tex.setFill(Color.BLACK);
	         email_box.getChildren().add(tex);
	         email_box.getChildren().add(textField);
	         email_box.setMargin(textField, new Insets(10,0,0,0));
	         
	         PasswordField textField2 = new PasswordField();
	    	 textField2.setMaxWidth(200);
	    	 textField2.setPadding(new Insets(10,10,10,10));
	    	 textField2.setDisable(false);
	    	 textField2.setEditable(true);
	         textField2.setPromptText("123456");
	         
	    	 VBox password_box = new VBox();
	         Text tex2 = new Text();
	         tex2.setText("Password");
	         tex2.setFill(Color.BLACK);
	         password_box.getChildren().add(tex2);
	         password_box.getChildren().add(textField2);
	         password_box.setMargin(textField2, new Insets(10,0,0,0));
	         
	         
	         Button button = new Button("Login");
	         button.setStyle("-fx-padding: 0.7em 0.57em;\n" +
	        		 "    -fx-font-size: 14px;\n" +
	        		 "    -jfx-button-type: RAISED;\n" +
	        		 "    -fx-background-color: rgb(77,102,204);\n" +
	        		 "    -fx-pref-width: 120;\n" +
	        		 "    -fx-text-fill: WHITE;");
	         
	         pane.setTop(email_box);
	         pane.setCenter(password_box);
	         pane.setBottom(button);
	         
	         pane.setMargin(email_box, new Insets(50,10,50,50));
	         pane.setMargin(password_box, new Insets(-30,10,50,50));
	         pane.setMargin(button, new Insets(-10,10,50,90));
	         
	         return pane;
	    }
    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-image: url(\"/icons/wallpaper.jpg\");\n" +"    -fx-background-repeat: stretch;   \n" +"    -fx-background-size: 1500 800;\n");
        StackPane pane2 = new StackPane();
        pane2.setStyle("-fx-background-color: black;\n" +"    -fx-background-repeat: stretch;   \n" +"    -fx-background-size: 1500 800;\n");
        pane2.setOpacity(0.5);
        pane.getChildren().add(pane2);
        Scene scene = new Scene(pane, 1500, 800);
        primaryStage.setTitle("Khurana Business Solutions Desk");
        primaryStage.setScene(scene);
        VBox vbox = new VBox();
        Image image  = new Image(LoginPage.class.getResource("/icons/login.png").toExternalForm());
        ImageView imageview = new ImageView(image);
        BorderPane pane_internal  = new BorderPane();
        pane_internal.setCenter(vbox);
        pane_internal.setMaxWidth(850);
        imageview.setFitHeight(100);
        imageview.setFitWidth(150);
        BorderPane border_new = new BorderPane();
        border_new.setCenter(imageview);
        border_new.setPadding(new Insets(50,0,0,100));
        BorderPane text_border_pane = new BorderPane();
        VBox login = Card(" Login ",1,"#1A237E");
        VBox signup = Card(" Sign up ",2,"#311B92");
        signup.setPrefWidth(310);
        signup.setMinHeight(220);
        login.setPrefWidth(310);
        HBox inside_login_card = (HBox)login.lookup("#my_hbox");
        HBox inside_signup_card = (HBox)signup.lookup("#my_hbox");
        
        inside_login_card.getChildren().add(login_box());
        inside_signup_card.getChildren().add(signup_box());
        inside_signup_card.setMinHeight(360);;
        text_border_pane.setLeft(login);
        text_border_pane.setRight(signup);
        text_border_pane.setPadding(new Insets(70,0,0,100));
        vbox.getChildren().add(border_new);
        vbox.getChildren().add(text_border_pane);
        BorderPane pane_internal_side_pane  = new BorderPane();
        pane_internal_side_pane.setLeft(sidePane());
        pane.getChildren().add(pane_internal);
        pane.getChildren().add(pane_internal_side_pane);
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
