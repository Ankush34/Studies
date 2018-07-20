import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

public class OperationScreen extends Application {
	public ObservableList<product_upload_details> products_upload_details = 
		    FXCollections.observableArrayList();
	public ObservableList<ProductInfoGoDown> products_with_godown = 
		    FXCollections.observableArrayList();
	public ObservableList<LedgerObject> ledgers = 
		    FXCollections.observableArrayList();
	
	public ArrayList<String> columns = new ArrayList<>();
	public VBox Card(String title, String color)
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
        hbox.getChildren().add(text);
        vbox.setStyle("-fx-background-radius: 300%;\n"+"-fx-background: #ffffff;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        vbox.getChildren().add(hbox);	
        VBox hbox_new = new VBox();
        hbox_new.setMinHeight(325);
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
    
    public TableView<product_upload_details> get_product_upload_table()
    {
    	TableView<product_upload_details> tableView = new TableView<>();
   
    	 TableColumn columnName = new TableColumn("Product Name");
         columnName.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("name"));  
                      
         columnName.setMinWidth(200);
         columnName.setStyle("-fx-font-weight:lighter;");
        
         TableColumn columnCount = new TableColumn("Product Brand");
         columnCount.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("brand"));
         columnCount.setMinWidth(200);
         columnCount.setStyle("-fx-font-weight:lighter;");
         
         TableColumn columnDate = new TableColumn("Product Count");
         columnDate.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("stock"));
         columnDate.setMinWidth(200);
         columnDate.setStyle("-fx-font-weight:lighter;");
        
         tableView.setItems(products_upload_details);
        tableView.getStylesheets().add(getClass().getResource("/desktop_app_css/table_styling.css").toExternalForm());
         tableView.getColumns().addAll(columnName, columnCount,columnDate);
         tableView.setMaxHeight(400);
         tableView.setStyle("");
         return tableView;
    	
    }
    
    public TableView<ProductInfoGoDown> get_stock_godownwise_table()
    {
    	send_http_get_columns();
    	TableView<ProductInfoGoDown> tableView = new TableView<>();
 	   
    	 TableColumn columnName = new TableColumn("Product Name");
        columnName.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("name"));  
                     
        columnName.setMinWidth(200);
        columnName.setStyle("-fx-font-weight:lighter;");
        
        tableView.setItems(products_with_godown);
        tableView.getColumns().add(columnName);
        
        for (int i = 0; i < columns.size(); i++) {
            final int finalIdx = i;
            TableColumn<ProductInfoGoDown, String> column = new TableColumn<>(
                   columns.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            column.setStyle("-fx-font-weight:lighter;");
            column.setMinWidth(200);
            tableView.getColumns().add(column);
        }
        tableView.setMaxHeight(400);
        return tableView;
    }
    
    public TableView<LedgerObject> get_ledgers_table()
    {
    	TableView<LedgerObject> tableView = new TableView<>();
    	   
   	 TableColumn columnName = new TableColumn("Ledger Name");
        columnName.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("name"));  
                     
        columnName.setMinWidth(200);
        columnName.setStyle("-fx-font-weight:lighter;");
       
        TableColumn columnContact = new TableColumn("Contact");
        columnContact.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("mobile"));
        columnContact.setMinWidth(200);
        columnContact.setStyle("-fx-font-weight:lighter;");
        
        TableColumn columnEmail = new TableColumn("Email");
        columnEmail.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("email"));
        columnEmail.setMinWidth(200);
        columnEmail.setStyle("-fx-font-weight:lighter;");
        
        TableColumn columnGST = new TableColumn("GSTIN No.");
        columnGST.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("gstin"));
        columnGST.setMinWidth(200);
        columnGST.setStyle("-fx-font-weight:lighter;");

        TableColumn columnState = new TableColumn("State");
        columnState.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("state"));
        columnState.setMinWidth(200);
        columnState.setStyle("-fx-font-weight:lighter;");
        
        TableColumn columnAddress = new TableColumn("Address");
        columnAddress.setCellValueFactory(
                new PropertyValueFactory<PromoterProduct,String>("address"));
        columnAddress.setMinWidth(200);
        columnAddress.setStyle("-fx-font-weight:lighter;");
       
        tableView.setItems(ledgers);
       tableView.getStylesheets().add(getClass().getResource("/desktop_app_css/table_styling.css").toExternalForm());
        tableView.getColumns().addAll(columnName, columnContact,columnEmail, columnGST, columnAddress, columnState);
        tableView.setMaxHeight(400);
        tableView.setStyle("");
        return tableView;
    }
    public VBox get_product_upload_card()
    {
    	VBox card = new VBox();
    	card = Card("Uploading Product Details ","#03A9F4");
    	TableView table = get_product_upload_table();
    	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	card.setMinWidth(600);
    	VBox card_content = (VBox) card.lookup("#my_hbox");
    	ScrollPane internal_scroll = new ScrollPane();
    	internal_scroll.setContent(table);
    	card_content.getChildren().add(internal_scroll);
		card_content.setMargin(internal_scroll, new Insets(10,10,10,10));
	   	BorderPane pane = new BorderPane();
    	JFXButton btn_upload = new JFXButton();
        btn_upload.setText("Start Updating Data");
        btn_upload.setStyle("-fx-background-color: #3d5afe;\n" + 
        		"  -fx-background-radius: 16.4, 15;\n" + 
        		"  -fx-background-insets: -1.4, 0;\n" +
        		"	-fx-text-fill: WHITE;"+
        		"  -fx-border-radius: 15;\n" + 
        		"  -fx-pref-width: 150;\n"+
        		"  -fx-pref-height: 20;\n"+
        		"  -fx-border-width: 2;\n"+ 
        		"  -fx-padding: 0;\n" + 
        		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
        		"  -fx-font-size: 10;");
        pane.setRight(btn_upload);
        pane.setMargin(btn_upload, new Insets(10,50,10,10));
        card_content.getChildren().add(pane);

		return card;	
    }
    
    public VBox get_batch_upload_card()
    {
    	VBox card = new VBox();
    	TableView table = get_stock_godownwise_table();
    	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	card = Card("Uploading Batch Details ","#FF9800");
    	card.setMinWidth(600);
    	VBox card_content = (VBox) card.lookup("#my_hbox");
    	ScrollPane internal_scroll = new ScrollPane();
    	internal_scroll.setContent(table);
    	card_content.getChildren().add(internal_scroll);
    	card_content.setMargin(internal_scroll, new Insets(10,10,10,10));
    	BorderPane pane = new BorderPane();
    	JFXButton btn_upload = new JFXButton();
        btn_upload.setText("Start Updating Data");
        btn_upload.setStyle("-fx-background-color: #ff6d00;\n" + 
        		"  -fx-background-radius: 16.4, 15;\n" + 
        		"  -fx-background-insets: -1.4, 0;\n" +
        		"	-fx-text-fill: WHITE;"+
        		"  -fx-border-radius: 15;\n" + 
        		"  -fx-pref-width: 150;\n"+
        		"  -fx-pref-height: 20;\n"+
        		"  -fx-border-width: 2;\n"+ 
        		"  -fx-padding: 0;\n" + 
        		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
        		"  -fx-font-size: 10;");
        pane.setRight(btn_upload);
        pane.setMargin(btn_upload, new Insets(10,50,10,10));
        card_content.getChildren().add(pane);
        return card;
    }
    public VBox get_legders_upload_card()
    {
    	VBox card = new VBox();
    	TableView table = get_ledgers_table();
    	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	card = Card("Uploading New Ledgers Details ","#ab47bc");
    	card.setMinWidth(600);
    	ScrollPane internal_scroll = new ScrollPane();
    	internal_scroll.setContent(table);
    	VBox card_content = (VBox) card.lookup("#my_hbox");
    	card_content.getChildren().add(internal_scroll);
    	card_content.setMargin(internal_scroll, new Insets(10,10,10,10));
    	BorderPane pane = new BorderPane();
    	JFXButton btn_upload = new JFXButton();
        btn_upload.setText("Start Updating Data");
        btn_upload.setStyle("-fx-background-color: #673ab7;\n" + 
        		"  -fx-background-radius: 16.4, 15;\n" + 
        		"  -fx-background-insets: -1.4, 0;\n" +
        		"	-fx-text-fill: WHITE;"+
        		"  -fx-border-radius: 15;\n" + 
        		"  -fx-pref-width: 150;\n"+
        		"  -fx-border-width: 2;\n"+ 
        		"  -fx-padding: 0;\n" + 
        		"  	-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
        		"  -fx-font-size: 10;");
        pane.setRight(btn_upload);
        pane.setMargin(btn_upload, new Insets(10,50,10,10));
        card_content.getChildren().add(pane);
    	return card;
    }
    public VBox get_data_upload_details()
    {
    	VBox card = new VBox();
    	ScrollPane internal_scroll  = new ScrollPane();
    	Text text = new Text("Your All Upload Details : ");
    	text.prefWidth(500);
    	text.setStyle("-fx-font-weight:lighter;");
    	internal_scroll.setContent(text);
        card = Card("Data Upload Complete Details","#536DFE");
    	card.setMinWidth(600);
    	VBox card_content = (VBox) card.lookup("#my_hbox");
    	card_content.getChildren().add(internal_scroll);
    	card_content.setMargin(internal_scroll, new Insets(20,10,10,30));
    	return card;
    }
	@Override    
	public void start(Stage primaryStage) throws Exception 
	{
		BorderPane pane = new BorderPane();
		pane.setPrefHeight(1200);
		pane.setPrefWidth(1500);
		VBox side_pane = sidePane();
		ScrollPane scroller = new ScrollPane();
		GridPane central_grid = new GridPane();
		central_grid.setVgap(15);
        central_grid.setHgap(22);
		VBox card_upload_product = get_product_upload_card();
		central_grid.add(card_upload_product, 0, 0);
		VBox card_upload_batch_details = get_batch_upload_card();
		central_grid.add(card_upload_batch_details, 1, 0);
		VBox card_upload_ledgers_details = get_legders_upload_card();
		central_grid.add(card_upload_ledgers_details, 0, 1);
		VBox data_upload_complete_details = get_data_upload_details();
		central_grid.add(data_upload_complete_details, 1, 1);
		
		central_grid.setMargin(card_upload_product, new Insets(20,10,10,10));
		central_grid.setMargin(card_upload_batch_details, new Insets(20,10,10,10));
		central_grid.setMargin(card_upload_ledgers_details, new Insets(20,10,10,10));
		central_grid.setMargin(data_upload_complete_details, new Insets(20,10,10,10));
		
		new JMetro(JMetro.Style.LIGHT).applyTheme(scroller);
		pane.setLeft(side_pane);
		scroller.setContent(central_grid);
		pane.setCenter(scroller);
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Khurana Business Solution Desk");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public  void send_http_get_columns()
	{
		try {
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_godown_locations_desktop.php";
		     URL obj = new URL(url);
		     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		     // optional default is GET
		     con.setRequestMethod("GET");
		     //add request header
		     con.setRequestProperty("User-Agent", "Mozilla/5.0");
		     int responseCode = con.getResponseCode();
		     System.out.println("\nSending 'GET' request to URL : " + url);
		     System.out.println("Response Code : " + responseCode);
		     BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream()));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
		     in.close();
		     JSONArray array =  new JSONArray(response.toString());
		     for(int i  = 0 ; i < array.length();i++)
		     {
		    	 JSONObject object = array.getJSONObject(i);
		    	 columns.add(object.getString("Column"));
		     }
		     }catch(Exception e)
		{
			e.printStackTrace();
		}
		  
	}
	public static void main(String[] args) 
	{
		launch(args);
	}

}
