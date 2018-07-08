import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Product_Stock extends Application {

	public  ObservableList<StockItem> dataList =
	          FXCollections.observableArrayList();
	

	public  ObservableList<OrderDetail> dataListOrders =
	          FXCollections.observableArrayList();
	
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
	        return box;
	        
	    }
    
	    public TableView<StockItem> get_stock_table()
	    {
	    	TableView<StockItem> tableView = new TableView<>();
	    	
	    	 tableView.setEditable(true);
	    	 Callback<TableColumn, TableCell> cellFactory =
	                 new Callback<TableColumn, TableCell>() {
	                     public TableCell call(TableColumn p) {
	                         return new EditingCell();
	                     }
	                 };
             TableColumn columnName = new TableColumn("Name");
             columnName.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Name"));  
             
             columnName.setMinWidth(300);
             TableColumn columnStock = new TableColumn("Stock");
             columnStock.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Stock"));
             
             columnStock.setCellFactory(cellFactory);
             columnStock.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setBrand(t.getNewValue());
                         }
                     });
             
             TableColumn columnmop = new TableColumn("Price MOP");
             columnmop.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Price_MOP"));
             
             columnmop.setCellFactory(cellFactory);
             columnmop.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setPrice_MOP(t.getNewValue());
                         }
                     });
             
             TableColumn columnmrp = new TableColumn("Price MRP");
             columnmrp.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Price_MRP"));
             
             columnmrp.setCellFactory(cellFactory);
             columnmrp.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setPrice_MRP(t.getNewValue());
                         }
                     });
             
             TableColumn columnksprice = new TableColumn("Price KS");
             columnksprice.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Price_KS"));
             
             columnksprice.setCellFactory(cellFactory);
             columnksprice.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setPrice_KS(t.getNewValue());
                         }
                     });
             
             tableView.setItems(dataList);
             tableView.getColumns().addAll(columnName, columnStock,columnmop,columnmrp,columnksprice);
             tableView.setMaxHeight(400);
             tableView.setStyle("");
             return tableView;
	    	
	    }
	    
	    public TableView<OrderDetail> get_order_table()
	    {
	    	TableView<OrderDetail> tableView = new TableView<>();
	    	
	    	 tableView.setEditable(true);
	    	 Callback<TableColumn, TableCell> cellFactory =
	                 new Callback<TableColumn, TableCell>() {
	                     public TableCell call(TableColumn p) {
	                         return new EditingCellOrders();
	                     }
	                 };
             TableColumn columnName = new TableColumn("Name");
             columnName.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_product_name"));  
             
             columnName.setMinWidth(300);
             TableColumn columnOrderNumber = new TableColumn("Order Number");
             columnOrderNumber.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_invoice_number"));
             
             columnOrderNumber.setCellFactory(cellFactory);
             columnOrderNumber.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<OrderDetail, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<OrderDetail, String> t) {
                             ((OrderDetail)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setSales_order_invoice_number(t.getNewValue());
                         }
                     });
             
             TableColumn ColumnProductCount = new TableColumn("Product Count");
             ColumnProductCount.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_product_count"));
             
             ColumnProductCount.setCellFactory(cellFactory);
             
             TableColumn ColumnProductPrice = new TableColumn("Product Price");
             ColumnProductPrice.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_price"));
             
             ColumnProductPrice.setCellFactory(cellFactory);
            
             TableColumn ColumnEndUserName = new TableColumn("Customer Name");
             ColumnEndUserName.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_end_user_name"));
             
             ColumnEndUserName.setCellFactory(cellFactory);
            
             TableColumn ColumnEndUserPhone = new TableColumn("Customer Phone");
             ColumnEndUserPhone.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("sales_order_end_user_phone"));
             
             ColumnEndUserPhone.setCellFactory(cellFactory);
            
             TableColumn ColumnBatchesSelected = new TableColumn("Batches Selected");
             ColumnBatchesSelected.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("batches_selected"));
             
             ColumnBatchesSelected.setCellFactory(cellFactory);
            
             TableColumn ColumnPromoterName = new TableColumn("Promoter Name");
             ColumnPromoterName.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("promoter_name"));
             
             ColumnPromoterName.setCellFactory(cellFactory);
            
             TableColumn ColumnCashPaymentAmount = new TableColumn("Cash Amount");
             ColumnCashPaymentAmount.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("cash_payment_amount"));
             
             ColumnCashPaymentAmount.setCellFactory(cellFactory);
             
             TableColumn ColumnCardPayment = new TableColumn("Card Payment");
             ColumnCardPayment.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("card_payment"));
             
             ColumnCardPayment.setCellFactory(cellFactory);
             
             TableColumn ColumnChequePayment = new TableColumn("Cheque Payment");
             ColumnChequePayment.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("cheque_payment"));
             
             ColumnChequePayment.setCellFactory(cellFactory);
             
             TableColumn ColumnFinancePayment = new TableColumn("Finance Payment");
             ColumnFinancePayment.setCellValueFactory(
                     new PropertyValueFactory<OrderDetail,String>("finance_payment"));
             
             ColumnFinancePayment.setCellFactory(cellFactory);
             
             tableView.setItems(dataListOrders);
             tableView.getColumns().addAll(columnName, columnOrderNumber, ColumnProductCount,
            		 ColumnProductPrice, ColumnEndUserName, ColumnEndUserPhone, ColumnBatchesSelected 
            		 ,ColumnPromoterName, ColumnCashPaymentAmount, ColumnCardPayment,ColumnChequePayment, ColumnFinancePayment);
             tableView.setMaxHeight(400);
             tableView.setStyle("");
             return tableView;
	    	
	    }
	    
	    
	    class EditingCellOrders extends TableCell<OrderDetail, String> {
	    	 
	        private TextField textField;
	       
	        public EditingCellOrders() {}
	       
	        @Override
	        public void startEdit() {
	            super.startEdit();
	           
	            if (textField == null) {
	                createTextField();
	            }
	           
	            setGraphic(textField);
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            textField.selectAll();
	        }
	        @Override
	        public void cancelEdit() {
	            super.cancelEdit();
	           
	            setText(String.valueOf(getItem()));
	            setContentDisplay(ContentDisplay.TEXT_ONLY);
	        }
	        
	        @Override
	        public void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	           
	            if (empty) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                if (isEditing()) {
	                    if (textField != null) {
	                        textField.setText(getString());
	                    }
	                    setGraphic(textField);
	                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	                } else {
	                    setText(getString());
	                    setContentDisplay(ContentDisplay.TEXT_ONLY);
	                }
	            }
	        }
	        private void createTextField() {
	            textField = new TextField(getString());
	            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
	            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	               
	                @Override
	                public void handle(KeyEvent t) {
	                    if (t.getCode() == KeyCode.ENTER) {
	                        commitEdit(textField.getText());
	                    } else if (t.getCode() == KeyCode.ESCAPE) {
	                        cancelEdit();
	                    }
	                }
	            });
	        }
	       
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }

	   
	    }
	    
	    class EditingCell extends TableCell<StockItem, String> {
	    	 
	        private TextField textField;
	       
	        public EditingCell() {}
	       
	        @Override
	        public void startEdit() {
	            super.startEdit();
	           
	            if (textField == null) {
	                createTextField();
	            }
	           
	            setGraphic(textField);
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            textField.selectAll();
	        }
	        @Override
	        public void cancelEdit() {
	            super.cancelEdit();
	           
	            setText(String.valueOf(getItem()));
	            setContentDisplay(ContentDisplay.TEXT_ONLY);
	        }
	        
	        @Override
	        public void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	           
	            if (empty) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                if (isEditing()) {
	                    if (textField != null) {
	                        textField.setText(getString());
	                    }
	                    setGraphic(textField);
	                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	                } else {
	                    setText(getString());
	                    setContentDisplay(ContentDisplay.TEXT_ONLY);
	                }
	            }
	        }
	        private void createTextField() {
	            textField = new TextField(getString());
	            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
	            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	               
	                @Override
	                public void handle(KeyEvent t) {
	                    if (t.getCode() == KeyCode.ENTER) {
	                        commitEdit(textField.getText());
	                    } else if (t.getCode() == KeyCode.ESCAPE) {
	                        cancelEdit();
	                    }
	                }
	            });
	        }
	       
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }

	   
	    }
	        
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
			HBox main_view = server_scene();
	        Scene scene = new Scene(main_view);
			stage.setScene(scene);
	        stage.setTitle("Khurana Business Solutions");
	        stage.show();
	        send_http_request();
	        send_http_request_orders();
	       
	}
	
	public HBox server_scene()
	{
		HBox main_view = new HBox();
		VBox side_pane = sidePane();
		side_pane.setId("my_side_pane");
		main_view.getChildren().add(side_pane);
		side_pane.setMinWidth(230);
		main_view.setPrefSize(2200, 1000);
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxWidth(1200);
       
        BorderPane border_pane_main =  new BorderPane();
        border_pane_main.setPrefSize(2200, 1000);
	     
        HBox search_box_container = new HBox();
        search_box_container.setPrefSize(1000, 20);;
        search_box_container.setStyle("-fx-background-color: #1A237E;\n"+"-fx-background-radius: 10 10 10 10;\n" + 
    	        "    -fx-border-radius: 0 0 9 9;");
        HBox searchbox =new HBox();
        searchbox.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        searchbox.setPrefSize(1000, 20);
        search_box_container.getChildren().add(searchbox);
        search_box_container.setMargin(searchbox, new Insets(3,3,3,3));
        borderPane.setTop(search_box_container);
        
        Image image = new Image(Product_Stock.class.getResource("/icons/ic_trash_search.png").toExternalForm());
        ImageView clear_search = new ImageView();
        
        Image image_search = new Image(Product_Stock.class.getResource("/icons/search.png").toExternalForm());
        ImageView search = new ImageView(image_search);
        search.setFitHeight(17);
        search.setFitWidth(17);
        
        clear_search.setImage(image);
        clear_search.setFitHeight(30);
        clear_search.setFitWidth(30);
        
        TextArea search_area  = new TextArea();
        search_area.setPrefSize(600,20);
        search_area.setPromptText("Search......");
       
        BorderPane search_box_pane = new BorderPane();
        search_box_pane.setMaxHeight(20);
        search_box_pane.setLeft(clear_search);
        search_box_pane.setRight(search);
        search_box_pane.setCenter(search_area);
        
        search_box_pane.setPrefSize(1200, 20);
        search_box_pane.setMargin(clear_search, new Insets(4,0,0,0));
        search_box_pane.setMargin(search, new Insets(12,8,0,8));
        searchbox.getChildren().add(search_box_pane);
        borderPane.setMargin(search_box_container, new Insets(10,175,0,23));
        TableView table = get_stock_table();
        TableView table_orders = get_order_table();
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table_orders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        Label label_top = new Label("Khurana Sales Stock Book");
        label_top.setFont(new Font("Arial", 15));
       
        VBox box = new VBox();
        box.getChildren().add(label_top);
        box.getChildren().add(table);
        box.setMargin(label_top, new Insets(20,0,10,0));
        box.setMargin(table, new Insets(0,0,10,0));
        borderPane.setCenter(box);
        borderPane.setMargin(box, new Insets(0,20,0,20));
        borderPane.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        border_pane_main.setTop(borderPane);
        
        BorderPane borderPaneLower = new BorderPane();
        borderPaneLower.setPrefSize(1500, 500);
        borderPaneLower.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        Label label = new Label("Khurana Sales Orders Book");
        label.setFont(new Font("Arial", 15));
        
        borderPaneLower.setTop(label);
        borderPaneLower.setCenter(table_orders);
        borderPaneLower.setMargin(label, new Insets(10,20,0,20));
        borderPaneLower.setMargin(table_orders, new Insets(0,20,0,20));
        
        border_pane_main.setCenter(borderPaneLower);
	    
        border_pane_main.setMargin(borderPane, new Insets(10,10,10,10));
        border_pane_main.setMargin(borderPaneLower, new Insets(10,10,10,10));
        
        
        ScrollPane pane = new ScrollPane();
        pane.setContent(border_pane_main);
        main_view.getChildren().add(pane);
        return main_view;
	}
	public  void send_http_request_orders()
	{
		try {
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_orders_desktop.php";
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
		     System.out.println(response.toString());
		     in.close();
		     JSONArray array =  new JSONArray(response.toString());
		     for(int i  = 0 ; i < array.length();i++)
		     {	 JSONObject object = array.getJSONObject(i);
		    	 dataListOrders.add(new OrderDetail(object.getString("sales_order_product_name"),object.getString("sales_order_number")
		    			 ,object.getString("sales_order_product_count"),object.getString("sales_order_price"),object.getString("sales_order_end_user_name"),
		    			 object.getString("sales_order_end_user_phone"),object.getString("batches_selected"),object.getString("user_name"),
		    			 object.getString("cash_payment_amount"),object.getString("paytm_payment"),object.getString("finance_payment"),object.getString("card_payment"),object.getString("cheque_payment")));
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
			String url ="http://khuranasales.co.in/work/get_products.php?brand=Samsung";
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
		    	 dataList.add(new StockItem(object.getString("Name"),object.getString("stock"),object.getString("mrp"),object.getString("mop"),object.getString("ksprice")));
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
