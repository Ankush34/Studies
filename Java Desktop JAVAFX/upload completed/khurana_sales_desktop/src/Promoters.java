import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Promoters  extends Application{
	public ObservableList<String> promoters = 
		    FXCollections.observableArrayList();
	public ObservableList<PromoterProduct> promotersproducts = 
		    FXCollections.observableArrayList();
	public ArrayList<Promoter> promoters_list =new ArrayList<>();
	public ObservableList<ProductInfoGoDown> productinfo = 
		    FXCollections.observableArrayList();
	public String from_date="26-06-2018";
	public String to_date="26-06-2018";
	public String promoter_email="khuranasales2015@gmail.com";
	public ArrayList<String> columns = new ArrayList<>();
	
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
	    
	    public TableView<PromoterProduct> get_promoters_table()
	    {
	    	TableView<PromoterProduct> tableView = new TableView<>();
	   
	    	 TableColumn columnName = new TableColumn("Product Name");
             columnName.setCellValueFactory(
                     new PropertyValueFactory<PromoterProduct,String>("product_name"));  
                          
             columnName.setMinWidth(300);
             
             TableColumn columnCount = new TableColumn("Count Sold");
             columnCount.setCellValueFactory(
                     new PropertyValueFactory<PromoterProduct,String>("product_count"));
            
             TableColumn columnDate = new TableColumn("Sold Date");
             columnDate.setCellValueFactory(
                     new PropertyValueFactory<PromoterProduct,String>("date"));
            
             TableColumn columnPrice = new TableColumn("Price Total");
             columnPrice.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("product_price"));
            
             TableColumn columnOrderStatus = new TableColumn("Order Status");
             columnOrderStatus.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("sales_order_status"));
            
             TableColumn columnInvoiceStatus = new TableColumn("Invoice Status");
             columnInvoiceStatus.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("invoice_status"));
            
             tableView.setItems(promotersproducts);
             tableView.getColumns().addAll(columnName, columnCount,columnDate,columnPrice, columnOrderStatus, columnInvoiceStatus);
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
                         
            columnName.setMinWidth(300);
          
            tableView.setItems(productinfo);
            tableView.getColumns().add(columnName);
            
            for (int i = 0; i < columns.size(); i++) {
                final int finalIdx = i;
                TableColumn<ProductInfoGoDown, String> column = new TableColumn<>(
                       columns.get(i)
                );
                column.setCellValueFactory(param ->
                        new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                tableView.getColumns().add(column);
            }
            tableView.setMaxHeight(400);
            tableView.setStyle("");
            return tableView;
	    }
	    
	@Override
	public void start(Stage primaryStage) throws Exception {
	    HBox main_view = serve_scene();
		Scene scene = new Scene(main_view);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Khurana Business Solutions Desk");
		primaryStage.show();
		 ExecutorService service = Executors.newFixedThreadPool(4);
		    service.submit(new Runnable() {
		        public void run() {
		        	send_http_request();
		        }
		    });
		 ExecutorService service1 = Executors.newFixedThreadPool(4);
		    service1.submit(new Runnable() {
		        public void run() {
		        	send_http_request_promoters_data();
		        }
		    });
		    ExecutorService service2 = Executors.newFixedThreadPool(4);
		    service2.submit(new Runnable() {
		        public void run() {
		        	send_http_request_batch_data();
		        }
		    });
	}
	
	public HBox serve_scene()
	{
		// TODO Auto-generated method stub
		HBox main_view = new HBox();
		main_view.setMinSize(1800, 1000);
		VBox side_pane = sidePane();
		side_pane.setId("my_side_pane");
		side_pane.setMinWidth(230);
		main_view.getChildren().add(side_pane);
		
		ScrollPane pane_internal_container = new ScrollPane();
		pane_internal_container.setPrefSize(1200, 1000);
		BorderPane main_internal_pane = new BorderPane();
		main_internal_pane.setPrefSize(1100, 1000);
		
		HBox top_box_internal_container = new HBox();
		top_box_internal_container.setPrefSize(1800, 500);
		
		VBox box_top_data = new VBox();
		box_top_data.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
		Label label_promoter_day_book = new Label("Promoter's Day Book ");
		TableView table_promoters = get_promoters_table();
		table_promoters.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table_promoters.setStyle("-fx-background-radius: 10 10 10 10;\n"+"-fx-font-size: 12;\n"+"-fx-font-family: Arial;\n");
		box_top_data.getChildren().add(label_promoter_day_book);
		box_top_data.getChildren().add(table_promoters);
		
		box_top_data.setMargin(label_promoter_day_book, new Insets(20,10,10,15));
		box_top_data.setMargin(table_promoters, new Insets(20,10,10,10));
		

		top_box_internal_container.getChildren().add(box_top_data);
		
		VBox box_top_selection = new VBox();
		box_top_selection.setPrefSize(400, 500);
		box_top_selection.setMinWidth(400);
		BorderPane pane_internal_box_selection = new BorderPane();
		BorderPane pane_title = new BorderPane();
		pane_title.setStyle("-fx-background-color: Blue;\n"+"-fx-background-radius: 10 10 0 0;\n");
		pane_title.setPrefSize(400, 50);
		Label label = new Label();
		label.setText("Promoter Data Filter");
		label.setTextFill(Color.web("#ffffff"));
		
		pane_title.setCenter(label);		
		pane_internal_box_selection.setTop(pane_title);
 
		DatePicker datePicker = new DatePicker();
		datePicker.setOnAction(event -> {
		    LocalDate date = datePicker.getValue();
		    System.out.println("Selected date: " + date);
		    from_date = date.toString();
		});
		datePicker.setMinWidth(300);
		
		DatePicker datePicker_to = new DatePicker();
		datePicker_to.setOnAction(event -> {
		    LocalDate date = datePicker_to.getValue();
		    System.out.println("Selected date: " + date);
		    to_date = date.toString();
		});
		datePicker_to.setMinWidth(300);
		
		VBox box_selection_list =  new VBox();
		box_selection_list.setPrefSize(300, 400);
		
		Label label_date_selection = new Label("Select From Date: ");
		VBox date_selection_vbox = new VBox();
		date_selection_vbox.getChildren().add(label_date_selection);
		date_selection_vbox.getChildren().add(datePicker);
		date_selection_vbox.setMargin(label_date_selection, new Insets(10,0,10,0));
		
		Label label_date_to_selection = new Label("Select To Date: ");
		VBox date_to_selection_vbox = new VBox();
		date_to_selection_vbox.getChildren().add(label_date_to_selection);
		date_to_selection_vbox.getChildren().add(datePicker_to);
		date_to_selection_vbox.setMargin(label_date_to_selection, new Insets(10,0,10,0));
		
		VBox promoter_selection_vbox = new VBox();
		ComboBox comboBox = new ComboBox(promoters);
		comboBox.setMinWidth(300);
		comboBox.setVisibleRowCount(4);
		comboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
	        for(int i =0;i <promoters_list.size();i++)
	        {
	        	if(promoters_list.get(i).getName().equals(newValue.toString()))
	        	{
	    			promoter_email = promoters_list.get(i).getEmail();
	    			System.out.println("promoters email: "+promoter_email);
	        	}
	        }
	    }
	    ); 
		promoter_selection_vbox.setMinWidth(300);
		Label label_promoters = new Label("Select Your Promoter");
		promoter_selection_vbox.getChildren().add(label_promoters);
		promoter_selection_vbox.getChildren().add(comboBox);
		promoter_selection_vbox.setMargin(label_promoters, new Insets(10,0,10,0));
		
		
	    Button button_get_details = new Button("Get Promoter's Inventory");
          button_get_details.setStyle("-fx-padding: 0.7em 0.57em;\n" +
         		 "    -fx-font-size: 12px;\n" +
         		 "    -jfx-button-type: RAISED;\n" +
         		 "    -fx-background-color: rgb(77,102,204);\n" +
         		 "    -fx-pref-width: 220;\n" +
         		 "    -fx-text-fill: WHITE;");
         
	   button_get_details.setOnMouseClicked(value->{
		   	send_http_request_promoters_data();
	   });
        box_selection_list.getChildren().add(date_selection_vbox);
		box_selection_list.getChildren().add(date_to_selection_vbox);
		box_selection_list.getChildren().add(promoter_selection_vbox);
		box_selection_list.getChildren().add(button_get_details);
		
		box_selection_list.setMargin(date_to_selection_vbox, new Insets(30,0,0,0));
		box_selection_list.setMargin(promoter_selection_vbox, new Insets(30,0,0,0));
		box_selection_list.setMargin(button_get_details, new Insets(60,0,0,50));
			
		pane_internal_box_selection.setCenter(box_selection_list);
		pane_internal_box_selection.setMargin(box_selection_list, new Insets(40,10,10,50));
		
		box_top_selection.getChildren().add(pane_internal_box_selection);
		
		top_box_internal_container.getChildren().add(box_top_selection);
		top_box_internal_container.setMargin(box_top_data, new Insets(10,10,10,10));
		top_box_internal_container.setMargin(box_top_selection, new Insets(10,10,10,10));

		box_top_selection.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
		
		
		VBox bottom_box_internal = new VBox();
		bottom_box_internal.setPrefSize(1000, 500);
		bottom_box_internal.setMaxWidth(1000);
		bottom_box_internal.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
		Label label_stock_book_godownwise  = new Label("Stock Book GoDown wise ");
		bottom_box_internal.getChildren().add(label_stock_book_godownwise);
		bottom_box_internal.setMargin(label_stock_book_godownwise, new Insets(20,10,10,20));
		TableView table_bottom = get_stock_godownwise_table();
		bottom_box_internal.setMargin(table_bottom, new Insets(20,10,10,10));
		table_bottom.setStyle("-fx-background-radius: 10 10 10 10;\n");
		table_bottom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		
		
		bottom_box_internal.getChildren().add(table_bottom);
		main_internal_pane.setTop(top_box_internal_container);
		main_internal_pane.setBottom(bottom_box_internal);
		main_internal_pane.setMargin(bottom_box_internal, new Insets(10,10,10,10));
		box_top_data.setMinSize(1000, 400);
		
		pane_internal_container.setContent(main_internal_pane);
		main_view.getChildren().add(pane_internal_container);
		return main_view;
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
	
	public  void send_http_request()
	{
		try {
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_promoter_names.php";
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
		    	 promoters.add(object.getString("promoter_name"));
		    	 Promoter promoter = new Promoter(object.getString("promoter_name"),object.getString("promoter_email"),object.getString("promoter_id"));
		    	 promoters_list.add(promoter);
		     }
		     }catch(Exception e)
		{
			e.printStackTrace();
		}
		  
	}

	public  void send_http_request_promoters_data()
	{
		try {
			promotersproducts.clear();
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_promoters_sold_desktop_filtered.php?email="+promoter_email+"&from="+from_date+"&to="+to_date;
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
		    	 PromoterProduct product = new PromoterProduct(object.getString("name"),object.getString("count"),object.getString("date"),object.getString("price"),object.getString("order_status"),object.getString("invoice_status"));
				promotersproducts.add(product);
		     }
		     }catch(Exception e)
		{
			e.printStackTrace();
		}
		  
	}

	public  void send_http_request_batch_data()
	{
		try {
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_products_with_batch_desktop.php";
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
		    	 ArrayList<String> batches = new ArrayList<>();
		    	 for(int j=0;j<columns.size();j++)
		    	 {
		    		 batches.add(object.getString(columns.get(j)).replaceAll(",", "\n"));
		    	 }
		    	 ProductInfoGoDown p = new ProductInfoGoDown(object.getString("name"),batches);
		    	 productinfo.add(p);
		     }
		     }catch(Exception e)
		{
			e.printStackTrace();
		}
		  
	}

	public static void main(String args[])
	{
		launch(args);
	}

}
