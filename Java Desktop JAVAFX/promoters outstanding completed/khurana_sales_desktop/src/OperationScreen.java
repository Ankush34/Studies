import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	StringBuilder builder = new StringBuilder();
	
	public ArrayList<String> columns = new ArrayList<>();
	public static File outputFile ;
	public static ArrayList<ProductBatch> products_with_batch = new ArrayList<>();
	public Text text ;
	
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
     
       String [] texts = {"Login","SignUp","MyCart","DashBoard"};
       VBox account_vbox = new VBox();
       for(int i = 1;i <5;i++)
       {
           account_vbox.getChildren().add(Item(i,texts[i-1]));
       }
       account_vbox.setId("account_helpers_container");
       
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
       support_vbox.setId("support_helpers_container");
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
        if(text.equals("DashBoard"))
        {
        	box.setId("my_dashboard");
        }
        if(text.equals("Logout"))
        {
        	box.setId("my_logout");
        }
        return box;
    }
    public TableView<product_upload_details> get_product_upload_table()
    {
    	TableView<product_upload_details> tableView = new TableView<>();
   
    	 TableColumn columnName = new TableColumn("Product Name");
         columnName.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("name"));  
                      
         columnName.setMinWidth(350);
         columnName.setStyle("-fx-font-weight:lighter;\n"+"-fx-font-size: 12;");
        
         TableColumn columnCount = new TableColumn("Product Brand");
         columnCount.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("brand"));
         columnCount.setMinWidth(200);
         columnCount.setStyle("-fx-font-weight:lighter;\n"+"-fx-font-size: 12;");
         
         TableColumn columnDate = new TableColumn("Product Count");
         columnDate.setCellValueFactory(
                 new PropertyValueFactory<PromoterProduct,String>("stock"));
         columnDate.setMinWidth(200);
         columnDate.setStyle("-fx-font-weight:lighter;\n"+"-fx-font-size: 12;");
        
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
                     
        columnName.setMinWidth(350);
        columnName.setStyle("-fx-font-weight:lighter;\n"+"-fx-font-size: 12;");
        
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
            column.setStyle("-fx-font-weight:lighter;\n"+"-fx-font-size: 12;");
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
    	card_content.getChildren().add(table);
		card_content.setMargin(table, new Insets(10,10,10,10));
	   	BorderPane pane = new BorderPane();
    	JFXButton btn_upload = new JFXButton();
        btn_upload.setText("Start Uploading Product Details");
        btn_upload.setStyle("-fx-background-color: #3d5afe;\n" + 
        		"  -fx-background-radius: 16.4, 15;\n" + 
        		"  -fx-background-insets: -1.4, 0;\n" +
        		"	-fx-text-fill: WHITE;"+
        		"  -fx-border-radius: 15;\n" + 
        		"  -fx-pref-width: 250;\n"+
        		"  -fx-pref-height: 20;\n"+
        		"  -fx-border-width: 2;\n"+ 
        		"  -fx-padding: 0;\n" + 
        		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
        		"  -fx-font-size: 12;");
        btn_upload.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	ExecutorService service = Executors.newFixedThreadPool(4);
			    service.submit(new Runnable() {
			        public void run() {
			        	operate_on_godaddy();
			        }
			    });
            }
        });
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
    	card_content.getChildren().add(table);
    	card_content.setMargin(table, new Insets(10,10,10,10));
    	BorderPane pane = new BorderPane();
    	JFXButton btn_upload = new JFXButton();
        btn_upload.setText("Start Uploading Batch Details");
        btn_upload.setStyle("-fx-background-color: #ff6d00;\n" + 
        		"  -fx-background-radius: 16.4, 15;\n" + 
        		"  -fx-background-insets: -1.4, 0;\n" +
        		"	-fx-text-fill: WHITE;"+
        		"  -fx-border-radius: 15;\n" + 
        		"  -fx-pref-width: 250;\n"+
        		"  -fx-pref-height: 20;\n"+
        		"  -fx-border-width: 2;\n"+ 
        		"  -fx-padding: 0;\n" + 
        		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
        		"  -fx-font-size: 12;");
        btn_upload.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// update_batch_numbers();
			    //add_batch_numbers();	
				ExecutorService service = Executors.newFixedThreadPool(4);
			    service.submit(new Runnable() {
			        public void run() {
			        	upload_batches_go_daddy();
			        }
			    });
				}
        	
        });
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
        		"  -fx-font-size: 12;");
        pane.setRight(btn_upload);
        pane.setMargin(btn_upload, new Insets(10,50,10,10));
        card_content.getChildren().add(pane);
    	return card;
    }
    public VBox get_data_upload_details()
    {
    	VBox card = new VBox();
    	ScrollPane internal_scroll  = new ScrollPane();
    	text = new Text("Your All Upload Details : ");
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
		
		Scene scene = new Scene(serve_scene());
		primaryStage.setTitle("Khurana Business Solution Desk");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public HBox serve_scene()
	{
		HBox box = new HBox();
		BorderPane pane = new BorderPane();
		pane.setPrefHeight(1200);
		pane.setPrefWidth(1500);
		VBox side_pane = sidePane();
		side_pane.setMinWidth(233);
		side_pane.setId("my_side_pane");
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
		
		central_grid.setMargin(card_upload_product, new Insets(10,10,0,10));
		central_grid.setMargin(card_upload_batch_details, new Insets(10,10,0,0));
		central_grid.setMargin(card_upload_ledgers_details, new Insets(0,10,10,10));
		central_grid.setMargin(data_upload_complete_details, new Insets(0,10,10,0));
		
		new JMetro(JMetro.Style.LIGHT).applyTheme(scroller);
		scroller.setContent(central_grid);
		pane.setCenter(scroller);
		pane.setId("my_pane");
		box.getChildren().add(side_pane);
		box.getChildren().add(pane);
		return box;
	}
	
	public JSONArray get_product_by_name(JSONObject parent)
	{
		System.out.println("try to fetch details about products....");
		builder.append("try to fetch details about products...."+"\n");
		text.setText(builder.toString());
		String url="http://khuranasales.co.in/work/get_product_by_name_desktop.php";
		HttpURLConnection con = null;
		JSONArray json_array = null;
		try {
			URL object=new URL(url);
			con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(parent.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
			    BufferedReader br = new BufferedReader(
			            new InputStreamReader(con.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }
			     json_array = new JSONArray(sb.toString());
			} else {
			    System.out.println(con.getResponseMessage());
			}
		 	} catch (Exception e) {
			e.printStackTrace();
		    }		
        return json_array;
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
	
	public void send_product_online(ArrayList<String> insert_brand, ArrayList<String> insert_product_names, ArrayList<Integer> insert_stock) {
		String url="http://khuranasales.co.in/work/insert_product_desktop.php";
		try {
			URL object=new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			JSONObject parent = new JSONObject();
			JSONArray array = new JSONArray();
			for(int i = 0 ;i < insert_brand.size();i++)
			{
				JSONObject my_obj = new JSONObject();
				my_obj.put("product_name", insert_product_names.get(i));
				my_obj.put("product_stock", insert_stock.get(0));
				my_obj.put("product_brand", insert_brand.get(i));
				array.put(my_obj);
			}
			parent.put("insert", array);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(parent.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
			    BufferedReader br = new BufferedReader(
			            new InputStreamReader(con.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }
			    br.close();
			    System.out.println("insert product online: " + sb.toString());  
			} else {
			    System.out.println(con.getResponseMessage());  
			}  

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void update_batch_numbers(ArrayList<ProductBatch> products, ArrayList<Integer> batches) {
		String url="http://khuranasales.co.in/work/update_batch_numbers_desktop.php";
		try {
			URL object=new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			JSONObject parent = new JSONObject();
			JSONArray array = new JSONArray();
		
			for(int j  = 0; j < products.size();j++)
			{
				JSONObject data  = new JSONObject();
				data.put("batch_id", batches.get(j));
				JSONObject batch_details = new JSONObject();
				for(Map.Entry<String, ArrayList<String>> entry : products.get(j).batch_numbers_with_location.entrySet())
	        	{
					batch_details.put(entry.getKey(), String.join(",", entry.getValue()));
		        }
				data.put("batch_details",batch_details);
				array.put(data);
			}
			parent.put("update", array);
				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(parent.toString());
				wr.flush();
				StringBuilder sb = new StringBuilder();  
				int HttpResult = con.getResponseCode(); 
				if (HttpResult == HttpURLConnection.HTTP_OK) {
				    BufferedReader br = new BufferedReader(
				            new InputStreamReader(con.getInputStream(), "utf-8"));
				    String line = null;  
				    while ((line = br.readLine()) != null) {  
				        sb.append(line + "\n");  
				    }
				    br.close();
				    System.out.println("" + sb.toString());  
				} else {
				    System.out.println(con.getResponseMessage());  
				}	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void add_batch_numbers(ArrayList<ProductBatch> products_for_insert, ArrayList<Integer> product_ids_for_insert, ArrayList<Integer> quantity_for_insert) {
		String url="http://khuranasales.co.in/work/add_batch_desktop.php";
		try {
			URL object=new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			JSONObject parent = new JSONObject();
			JSONArray array = new JSONArray();
			for(int i = 0 ; i <products_for_insert.size();i++)
			{
				JSONObject data = new JSONObject();
				data.put("product_id", product_ids_for_insert.get(i));
				data.put("pieces_available", quantity_for_insert.get(i));
				ArrayList<String> columns = new ArrayList<>();
				ArrayList<String> values = new ArrayList<>();
				for(Map.Entry<String, ArrayList<String>> entry : products_for_insert.get(i).batch_numbers_with_location.entrySet())
	        	{
					columns.add(entry.getKey());
					values.add(String.join(",",entry.getValue()));
	        	}
				data.put("columns", columns);
				data.put("values", values);
				array.put(data);
			}
			parent.put("insert", array);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(parent.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
			    BufferedReader br = new BufferedReader(
			            new InputStreamReader(con.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }
			    br.close();
			    System.out.println("" + sb.toString());  
			} else {
			    System.out.println(con.getResponseMessage());  
			}  
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void update_product_stock(ArrayList<Integer> update_stock, ArrayList<String> update_product_name) {	
		String url="http://khuranasales.co.in/work/update_product_stock_desktop.php";
		try {
			URL object=new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
    		con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			JSONObject parent = new JSONObject();
			JSONArray array = new JSONArray();
			for(int i = 0; i < update_stock.size();i++)
			{
				JSONObject data = new JSONObject();
				data.put("stock", update_stock.get(i));
				data.put("name", update_product_name.get(i));
				array.put(data);
			}
			parent.put("update", array);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(parent.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
    		    BufferedReader br = new BufferedReader(
			            new InputStreamReader(con.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }
			    br.close();
			    System.out.println("update product stock: " + sb.toString());  
			} else {
			    System.out.println(con.getResponseMessage());  
			}  
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send_tally_request_for_product()
	{
		try {
			
			String Url = "http://localhost:9000/"; 

			String SOAPAction = "";

			String Voucher ="<ENVELOPE>"+ 
							"<HEADER>"+ 
							"<VERSION>1</VERSION>"+ 
							"<TALLYREQUEST>Export</TALLYREQUEST>"+ 
							"<TYPE>Data</TYPE>"+ 
							"<ID>Stock Summary</ID>"+ 
							"</HEADER>"+ 
							"<BODY>"+ 
							"<DESC>"+ 
							"<STATICVARIABLES>"+ 
							"<EXPLODEFLAG>Yes</EXPLODEFLAG>"+ 
							"<SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>"+ 
							"<SVCURRENTCOMPANY>'Khurana Sales - 2017-2018'</SVCURRENTCOMPANY>"+ 
							"</STATICVARIABLES>"+
							"</DESC>"+ 
							"</BODY>"+ 
							"</ENVELOPE>";
			// Create the connection where we're going to send the file.
			URL url = new URL(Url);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;


			ByteArrayInputStream bin = new ByteArrayInputStream(Voucher.getBytes());
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			// Copy the SOAP file to the open connection.

			copy(bin,bout); 

			byte[] b = bout.toByteArray();

			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			// Everything's set up; send the XML that was read in to b.
			OutputStream out = httpConn.getOutputStream();
			out.write(b);
			out.close();

			// Read the response and write it to standard out.

			InputStreamReader isr =
			new InputStreamReader(httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			String inputLine;
			Date date = new Date();
			 outputFile = new File("C:\\Users\\Public\\"+date.toString().replaceAll("[:, ]","")+".txt");	
			FileOutputStream fos=new FileOutputStream(outputFile,true);
		
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				fos.write((inputLine+"\n").getBytes());
				fos.flush();
				}
			in.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void copy(InputStream in, OutputStream out)
		throws IOException {
		synchronized (in) {
		synchronized (out) {
	
		byte[] buffer = new byte[256];
		while (true) {
		int bytesRead = in.read(buffer);
		if (bytesRead == -1) {
		break;
		}
		out.write(buffer, 0, bytesRead);
			}
			}
		}
	}
	
	public void operate_on_godaddy()
	{
		outputFile = new File("/home/amura/Desktop/khurana sales stok 09072017.xml");
		 Vector<String> products=new Vector<>(); 
		 Vector<Integer> stock=new Vector<>();
		 try { 
			 	Date date = new Date(); 
			 	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 	Document doc = dBuilder.parse(outputFile);
			 	doc.getDocumentElement().normalize();
			 	System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
			 	NodeList nList = doc.getElementsByTagName("DSPACCNAME");
			 	for (int temp = 0; temp < nList.getLength(); temp++) {
			 		Node nNode = nList.item(temp);
			 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 			Element eElement = (Element) nNode;
			 			products.addElement(eElement.getElementsByTagName("DSPDISPNAME").item(0).getTextContent());
			 		}
			 	}
			 	NodeList nList1 = doc.getElementsByTagName("DSPSTKINFO");
			 	for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
			 		NodeList nNode2 = (NodeList) nList1.item(temp1);
			 		for (int temp2 = 0; temp2 < nNode2.getLength(); temp2++) {
			 			Node nNode3 = nNode2.item(temp2);
			 			if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
			 				Element eElement1 = (Element) nNode3;
			 				String q=eElement1.getElementsByTagName("DSPCLQTY").item(0).getTextContent();
			 				String arr[]=q.split(" ");
			 				try{
			 					stock.addElement(Integer.parseInt(arr[0]));
			 				}catch(NumberFormatException nfe)
			 				{
			 					stock.addElement(0);
			 				}
			 			}
			 		}
			 	}
			}catch (Exception e) {
				e.printStackTrace();
			}
		System.out.println("Size for Products: "+products.size());
		builder.append("Size for Products: "+products.size()+"\n");
		System.out.println("Size for stock: "+stock.size());
		builder.append("Size for stock: "+stock.size()+"\n");
		text.setText(builder.toString());
		
		String[] name = new String[products.size()];
		JSONObject parent = new JSONObject();
		for(int d=0;d<products.size();d++)
		{   
			name[d] = products.get(d);
		}
		JSONArray result = null;
		try {
			parent.put("parent", name);
			result = get_product_by_name(parent);
			System.out.println(result.toString());
			JSONArray array = new JSONArray(result.toString());
			ArrayList<Integer> update_stock = new ArrayList<>();
			ArrayList<String> update_product_name = new ArrayList<>();
			ArrayList<Integer> insert_stock = new ArrayList<>();
			ArrayList<String> insert_product_names = new ArrayList<>();
			ArrayList<String> insert_brand = new ArrayList<>();
			for(int i = 0; i<array.length();i++)
			{
				JSONObject my_obj = array.getJSONObject(i);
				if(my_obj.getString("found").equals("true"))
				{
					update_stock.add(stock.elementAt(i));
					update_product_name.add(products.elementAt(i));					
					product_upload_details p = new product_upload_details();
					p.setName(products.elementAt(i));
					p.setStock(""+stock.elementAt(i));
					p.setBrand(products.elementAt(i).split(" ")[0]);
					products_upload_details.add(p);
				}
				else
				{
					if(stock.get(i) == 0)
					{
						System.out.println("category: "+products.get(i));
						continue;
					}
					System.out.println("inserting...");
					insert_stock.add(stock.elementAt(i));
					insert_product_names.add(products.elementAt(i));
					insert_brand.add(products.elementAt(i).split(" ")[0]);
					product_upload_details p = new product_upload_details();
					p.setName(products.elementAt(i));
					p.setStock(""+stock.elementAt(i));
					p.setBrand(products.elementAt(i).split(" ")[0]);
					products_upload_details.add(p);
				}
			}
			if(insert_product_names.size() > 0 )
			{
				send_product_online(insert_brand,insert_product_names,insert_stock);
			}
			if(update_product_name.size() > 0)
			{
				update_product_stock(update_stock, update_product_name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void tally_request_batch_numbers()
	{
		try {
		
		String Url = "http://localhost:9000/"; 

		String SOAPAction = "";

		String  item_wise_deep="<ENVELOPE>"+
				 "<HEADER>"+
				 "<TALLYREQUEST>Export Data</TALLYREQUEST>"+
				 "</HEADER>"+
				 "<BODY>"+
				 "<EXPORTDATA>"+
				 "<REQUESTDESC>"+
				 "<STATICVARIABLES>"+
				 "<SVCURRENTCOMPANY>'Khurana Sales - 2017-2018'</SVCURRENTCOMPANY>"+
				 "<SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>"+
				 "<EXPLODEALLLEVELS>YES</EXPLODEALLLEVELS>"+
				 "<EXPLODEFLAG>YES</EXPLODEFLAG>"+
				 "<DSPSHOWALLACCOUNTS>Yes</DSPSHOWALLACCOUNTS>"+
				 "<DSPSHOWOPENING>Yes</DSPSHOWOPENING>"+
				 "<DSPSHOWINWARDS>YES</DSPSHOWINWARDS>"+
				 "<DSPSHOWOUTWARDS>YES</DSPSHOWOUTWARDS>"+
				 "<DSPSHOWCLOSING>Yes</DSPSHOWCLOSING>"+
				 "<ISITEMWISE>Yes</ISITEMWISE>"+
				 "</STATICVARIABLES>"+
				 "<REPORTNAME>Stock Summary</REPORTNAME>"+
				 "</REQUESTDESC>"+
				 "</EXPORTDATA>"+
				 "</BODY>"+
				 "</ENVELOPE>";

		// Create the connection where we're going to send the file.
		URL url = new URL(Url);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;


		ByteArrayInputStream bin = new ByteArrayInputStream(item_wise_deep.getBytes());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.

		copy_new(bin,bout); 

		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);
		out.close();

		// Read the response and write it to standard out.

		InputStreamReader isr =
		new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		String inputLine;
		Date date = new Date();
		 outputFile = new File("C:\\Users\\Public\\"+date.toString().replaceAll("[:, ]","")+"batch.xml");	
		FileOutputStream fos=new FileOutputStream(outputFile,true);
	
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			fos.write((inputLine+"\n").getBytes());
			fos.flush();
			}
		in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void copy_new(InputStream in, OutputStream out)
	throws IOException {
		synchronized (in) {
			synchronized (out) {
				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) {break;}
					out.write(buffer, 0, bytesRead);
				}
			}			
		}
	
	}
	
	
	public void upload_batches_go_daddy()
	{
		System.out.println("uploading has started.....");
		builder.append("uploading has started....."+"\n");
		text.setText(builder.toString());
		boolean in_batch =  false;
			try {
				 outputFile = new File("/home/amura/Desktop/batch details.xml");	
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc;
				doc = dBuilder.parse(outputFile);
				doc.getDocumentElement().normalize();
				NodeList envelope = doc.getElementsByTagName("ENVELOPE");
				NodeList list_child_nodes = envelope.item(0).getChildNodes();
				boolean repeating = false;
				for(int i = 0; i < list_child_nodes.getLength() ; i ++)
				{
					if(list_child_nodes.item(i).getNodeName().equals("DSPACCNAME"))
					{
						ProductBatch productBatch = new ProductBatch();
						productBatch.name = list_child_nodes.item(i).getChildNodes().item(1).getTextContent();
						products_with_batch.add(productBatch);
						in_batch = false;
					}
					if(list_child_nodes.item(i).getNodeName().equals("SSBATCHNAME"))
					{
						in_batch = true;
					    NodeList child_nodes_batch = list_child_nodes.item(i).getChildNodes();
						NodeList nodes_new = list_child_nodes.item(i+2).getChildNodes();
						for(int j = 0; j < list_child_nodes.item(i+2).getChildNodes().getLength();j++)
						{
						
							if(nodes_new.item(j).getNodeName().equals("DSPSTKOP"))
							{
							 NodeList child_nodes = nodes_new.item(j).getChildNodes();
							}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKIN"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
										}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKOUT"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
							}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKCL"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
							if(child_nodes.item(1).getTextContent().equals(""))
							{
								
							}
							else
							{
								 if(products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.get(child_nodes_batch.item(3).getTextContent()) == null)
									{
								    	ArrayList<String> batch = new ArrayList<>();
										batch.add(child_nodes_batch.item(1).getTextContent());
										products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.put(child_nodes_batch.item(3).getTextContent(), batch); 
									}
									else
									{
										products_with_batch.get(products_with_batch.size()-1).batch_numbers_with_location.get(child_nodes_batch.item(3).getTextContent()).add(child_nodes_batch.item(1).getTextContent());
									}
							}
							}

						}
						i = i + 2;
					}
					if(list_child_nodes.item(i).getNodeName().equals("DSPSTKINFO") && in_batch == false)
					{
						NodeList nodes_new = list_child_nodes.item(i).getChildNodes();
						for(int j = 0; j < list_child_nodes.item(i).getChildNodes().getLength();j++)
						{
							if(nodes_new.item(j).getNodeName().equals("DSPSTKOP"))
							{
							 NodeList child_nodes = nodes_new.item(j).getChildNodes();
							}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKIN"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
									products_with_batch.get(products_with_batch.size()-1).total_quantity_in = child_nodes.item(1).getTextContent(); 
									
							}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKOUT"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
					
									products_with_batch.get(products_with_batch.size()-1).total_quantity_out = child_nodes.item(1).getTextContent(); 
							}
							if(nodes_new.item(j).getNodeName().equals("DSPSTKCL"))
							{

								 NodeList child_nodes = nodes_new.item(j).getChildNodes();
									products_with_batch.get(products_with_batch.size()-1).total_quantity_available =child_nodes.item(1).getTextContent(); 
							}

						}
					}
				}
				
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		JSONObject parent =new JSONObject();
		String name[] = new String[products_with_batch.size()];
		for(int d=0;d<products_with_batch.size();d++)
		{ 
			
			name[d] = products_with_batch.get(d).name;
		}
		JSONArray result = null;
		try {
			parent.put("parent", name);
			 result = get_product_by_name(parent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayList<ProductBatch> products_for_updation = new ArrayList<>();
		ArrayList<ProductBatch> products_for_insert = new ArrayList<>();
		ArrayList<Integer> product_ids_for_insert = new ArrayList<>();
		ArrayList<Integer> quantity_for_insert = new ArrayList<>();
		ArrayList<Integer> batches = new ArrayList<>();
		for(int i =0;i < result.length();i++)
		{
			try {
				JSONObject my_obj = result.getJSONObject(i);
				if(my_obj.getString("found").equals("true"))
				{
					int product_id = my_obj.getInt("product_id");	
					int Batch_id = my_obj.getInt("batch_id");
					if(products_with_batch.get(i).batch_numbers_with_location.size() == 0)
			    	{
						continue;
			    	}
					else
					{
						if(Batch_id != 0)
						{
							products_for_updation.add(products_with_batch.get(i));
							batches.add(Batch_id);	
							ArrayList<String> batch_numbers = new ArrayList<>();
							try {
								for(Map.Entry<String, ArrayList<String>> entry : products_with_batch.get(i).batch_numbers_with_location.entrySet())
					        	{
									batch_numbers.add(String.join(",", entry.getValue()).toString().replaceAll(",", "\n"));
						        }
								ProductInfoGoDown p = new ProductInfoGoDown(products_with_batch.get(i).name,batch_numbers);
								products_with_godown.add(p);	
							}catch(Exception e)
							{
								e.printStackTrace();
							}
												}
						else if(Batch_id == 0)
						{
						 if(products_with_batch.get(i).total_quantity_available.equals(""))
						 {
							 quantity_for_insert.add(Integer.parseInt(products_with_batch.get(i).total_quantity_available.split(" ")[0]));		 
						 }
						 else
						 {
							 quantity_for_insert.add(0);	
						 }
						 products_for_insert.add(products_with_batch.get(i));
						 product_ids_for_insert.add(product_id);
						 ArrayList<String> batch_numbers = new ArrayList<>();
						 try {
								for(Map.Entry<String, ArrayList<String>> entry : products_with_batch.get(i).batch_numbers_with_location.entrySet())
					        	{
									batch_numbers.add(String.join(",", entry.getValue()).toString().replaceAll(",", "\n"));
						        }
								ProductInfoGoDown p = new ProductInfoGoDown(products_with_batch.get(i).name,batch_numbers);
								products_with_godown.add(p);	
							}catch(Exception e)
							{
								e.printStackTrace();
							}
												
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		if(products_for_updation.size() > 0)
		{
			update_batch_numbers(products_for_updation, batches);			
		}
		if(products_for_insert.size()>0)
		{
			add_batch_numbers(products_for_insert, product_ids_for_insert,quantity_for_insert);
		}
	}
	public static void main(String[] args) 
	{
		launch(args);
	}

}
