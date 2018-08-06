import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Product_Stock extends Application {

	public  ObservableList<StockItem> dataList =
	          FXCollections.observableArrayList();
	
	private final String CrLf = "\r\n";
	public  ObservableList<OrderDetail> dataListOrders =
	          FXCollections.observableArrayList();
	public ObservableList<UploadProductPojo> uploaded_products_list = FXCollections.observableArrayList();
	private void httpConn(String image_path, String product_name, UploadProductPojo pro) {
        URLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
         try {
        	String product_name_encoded = URLEncoder.encode(product_name, "UTF-8") ;
            URL url = new URL("http://khuranasales.co.in/work/upload_image.php?product_name="+product_name_encoded);
            System.out.println("url:" + url);
            conn = url.openConnection();
            conn.setDoOutput(true);

            String postData = "";
            System.out.println("picking data from path: "+image_path);
            FileInputStream imgIs = new FileInputStream(image_path);
            byte[] imgData = new byte[imgIs.available()];
            imgIs.read(imgData);
 
            String message1 = "";
            message1 += "-----------------------------4664151417711" + CrLf;
            message1 += "Content-Disposition: form-data; name=\"uploadedfile\"; filename="+timeStamp+".jpg"
                    + CrLf;
            message1 += "Content-Type: image/jpg" + CrLf;
            message1 += CrLf;

            // the image is sent between the messages in the multipart message.

            String message2 = "";
            message2 += CrLf + "-----------------------------4664151417711--"
                    + CrLf;

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=---------------------------4664151417711");
            // might not need to specify the content-length when sending chunked
            // data.
            conn.setRequestProperty("Content-Length", String.valueOf((message1
                    .length() + message2.length() + imgData.length)));

            System.out.println("open os");
            os = conn.getOutputStream();

            System.out.println(message1);
            os.write(message1.getBytes());

            // SEND THE IMAGE
            int index = 0;
            int size = 1024;
            do {
                System.out.println("write:" + index);
                if ((index + size) > imgData.length) {
                    size = imgData.length - index;
                }
                os.write(imgData, index, size);
                index += size;
            } while (index < imgData.length);
            System.out.println("written:" + index);

            System.out.println(message2);
            os.write(message2.getBytes());
            os.flush();

            System.out.println("open is");
            is = conn.getInputStream();

            char buff = 512;
            int len;
            byte[] data = new byte[buff];
            do {
                System.out.println("READ");
                len = is.read(data);

                if (len > 0) {
                    System.out.println(new String(data, 0, len));
                }
            } while (len > 0);

            System.out.println("DONE");
            pro.setUploaded_status("DONE");
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Close connection");
            try {
                os.close();
            } catch (Exception e) {
            }
            try {
                is.close();
            } catch (Exception e) {
            }
            try {

            } catch (Exception e) {
            }
        }
    }
	
	public VBox get_header(String product_name)
	{
		VBox main_box =new VBox();
		BorderPane pane = new BorderPane();
		Text label = new Text("Product Image Upload");
		pane.setLeft(label);
		label.setFont(Font.font("Arial", FontWeight.LIGHT, 13));
       Text product_name_label = new Text(product_name);
		product_name_label.setFont(Font.font("Arial", FontWeight.LIGHT, 13));
		pane.setCenter(product_name_label);
		Image image = new Image(khurana_final.class.getResource("/icons/menu_icon_black.png").toExternalForm());
	    ImageView imageview = new ImageView(image);
	    pane.setRight(imageview);
	    imageview.setFitHeight(40);
	    imageview.setFitWidth(40);
	    pane.setMargin(label, new Insets(20,10,10,20));
	    pane.setMargin(product_name_label, new Insets(10,10,10,-60));
	    pane.setMargin(imageview, new Insets(10,10,10,10));
	    main_box.getChildren().add(pane);
	    return main_box;
	}
	public VBox get_centre()
	{
		 ListView<UploadProductPojo> listView = new ListView<>();
		 listView.setItems(uploaded_products_list);
	     listView.setCellFactory(studentListView -> new StudentListViewCell());
	     VBox box = new VBox();
	     box.getChildren().add(listView);
		return box;
	}
	public VBox get_footer(String product_name, Stage stage)
    {
        VBox vbox = new VBox();
        vbox.setPrefSize(20, 50);
        vbox.setStyle("-fx-background-radius: 300%;\n"+"-fx-background: #ffffff;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);");
        HBox hbox_new = new HBox();
        hbox_new.setMinHeight(50);
        hbox_new.setMinWidth(300);
        BorderPane pane = new BorderPane();
        FileChooser fileChooser = new FileChooser();
        JFXButton btn_upload = new JFXButton(" Upload Image ");
        TextField field = new TextField();
        btn_upload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 File file = fileChooser.showOpenDialog(stage);
                 if (file != null) {
                     field.setText(file.getAbsolutePath());
                     String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                     UploadProductPojo pro = new UploadProductPojo(file.getAbsolutePath(),product_name,timeStamp,"Pending");
                     uploaded_products_list.add(pro);
                     ExecutorService service = Executors.newFixedThreadPool(4);
      			    service.submit(new Runnable() {
      			        public void run() {
      			          httpConn(file.getAbsolutePath(),product_name,pro);
      			        }
      			    });
                 } 
			}
		});
        field.setMinWidth(500);
        field.setMinHeight(35);
        field.setPromptText("  File Path Here  ");
        btn_upload.setStyle("-fx-background-color: #3d5afe;\n" + 
           		"	-fx-text-fill: WHITE;"+
           		"  -fx-border-radius: 15;\n" + 
           		"  -fx-pref-width: 120;\n"+
           		"  -fx-pref-height: 30;\n"+
           		"  -fx-border-width: 2;\n"+ 
           		"  -fx-padding: 0;\n" + 
           		"  -fx-font-size: 10;");
        pane.setRight(btn_upload);
        pane.setLeft(field);
        pane.setMargin(field, new Insets(10,10,10,10));
        pane.setMargin(btn_upload ,new Insets(10,10,10,10));
        pane.setMinWidth(760);
        hbox_new.setStyle("-fx-background-color: White;\n"+"-fx-background-radius: 9 9 9 9;\n" + 
    	        "    -fx-border-radius: 9 9 9 9;");
        hbox_new.getChildren().add(pane);
        vbox.getChildren().add(hbox_new);
        vbox.setMinSize(300, 50);
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
       public void update_stock(String name, String stock)
       {
    	   System.out.println("You Just updated stock count");
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
   			JSONObject data = new JSONObject();
   			data.put("stock", stock);
   			data.put("name", name);
   			array.put(data);
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
       
       public void update_mop(String name , String mop)
       {
    	   System.out.println("You Just updated mop price of: "+name+" and value: "+mop);
    	   String url="http://khuranasales.co.in/work/update_product_mop_desktop.php";
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
   			JSONObject data = new JSONObject();
   			data.put("mop", mop);
   			data.put("name", name);
   			array.put(data);
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
   			    System.out.println("update product mop: " + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       }
       
       public void update_mrp(String name , String mrp)
       {
    	   System.out.println("You Just updated mop price of: "+name+" and value: "+mrp);
    	   String url="http://khuranasales.co.in/work/update_product_mrp_desktop.php";
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
   			JSONObject data = new JSONObject();
   			data.put("mrp", mrp);
   			data.put("name", name);
   			array.put(data);
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
   			    System.out.println("update product mop: " + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       }
       
       public void update_ks_price(String name , String ks_price)
       {
    	   System.out.println("You Just updated mop price of: "+name+" and value: "+ks_price);
    	   String url="http://khuranasales.co.in/work/update_product_ks_price_desktop.php";
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
   			JSONObject data = new JSONObject();
   			data.put("ks_price", ks_price);
   			data.put("name", name);
   			array.put(data);
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
   			    System.out.println("update product mop: " + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       }
       
       public void update_tax(String name , String tax)
       {
    	   System.out.println("You Just updated tax of: "+name+" and tax: "+tax);
    	   String url="http://khuranasales.co.in/work/update_product_tax_desktop.php";
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
   			JSONObject data = new JSONObject();
   			data.put("tax", tax);
   			data.put("name", name);
   			array.put(data);
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
   			    System.out.println("update product tax: " + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       }
       
       public void update_hsn(String name , String hsn)
       {
    	   System.out.println("You Just updated hsn of: "+name+" and hsn: "+hsn);
    	   String url="http://khuranasales.co.in/work/update_product_hsn_desktop.php";
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
   			JSONObject data = new JSONObject();
   			data.put("hsn", hsn);
   			data.put("name", name);
   			array.put(data);
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
   			    System.out.println("update product hsn: " + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
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
                             ExecutorService service = Executors.newFixedThreadPool(4);
             			    service.submit(new Runnable() {
             			        public void run() {
             			      		update_stock(t.getRowValue().getName(),t.getNewValue());
             			        }
             			    });
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
                             ExecutorService service = Executors.newFixedThreadPool(4);
              			    service.submit(new Runnable() {
              			        public void run() {
              			      		update_mop(t.getRowValue().getName(),t.getNewValue());
              			        }
              			    });
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
                             ExecutorService service = Executors.newFixedThreadPool(4);
               			    service.submit(new Runnable() {
               			        public void run() {
               			      		update_mrp(t.getRowValue().getName(),t.getNewValue());
               			        }
               			    });
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
                             ExecutorService service = Executors.newFixedThreadPool(4);
                             service.submit(new Runnable() {
                			        public void run() {
                			      		update_ks_price(t.getRowValue().getName(),t.getNewValue());
                			        }
                			    });
                         }
                     });
             
             TableColumn columntax = new TableColumn("Product GST");
             columntax.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("product_tax"));
             
             columntax.setCellFactory(cellFactory);
             columntax.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setProduct_tax(t.getNewValue());
                             ExecutorService service = Executors.newFixedThreadPool(4);
                             service.submit(new Runnable() {
                			        public void run() {
                			      		update_tax(t.getRowValue().getName(),t.getNewValue());
                			        }
                			    });
                         }
                     });
             
             TableColumn columnhsn = new TableColumn("Product HSN");
             columnhsn.setCellValueFactory(
                     new PropertyValueFactory<StockItem,String>("Product_HSN"));
             
             columnhsn.setCellFactory(cellFactory);
             columnhsn.setOnEditCommit(
                     new EventHandler<TableColumn.CellEditEvent<StockItem, String>>() {
                         @Override public void handle(TableColumn.CellEditEvent<StockItem, String> t) {
                             ((StockItem)t.getTableView().getItems().get(
                                     t.getTablePosition().getRow())).setProduct_HSN(t.getNewValue());
                             ExecutorService service = Executors.newFixedThreadPool(4);
                             service.submit(new Runnable() {
                			        public void run() {
                			      		update_hsn(t.getRowValue().getName(),t.getNewValue());
                			        }
                			    });
                         }
                     });
             TableColumn actionCol = new TableColumn("Action");
             actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
             Callback<TableColumn<StockItem, String>, TableCell<StockItem, String>> cellFactory1 = 
             new Callback<TableColumn<StockItem, String>, TableCell<StockItem, String>>() {
            	@Override
         	public TableCell call(final TableColumn<StockItem, String> param) {
        	 	final TableCell<StockItem, String> cell = new TableCell<StockItem, String>() {
                 	@Override
                 	public void updateItem(String item, boolean empty) {
                	 	super.updateItem(item, empty);
                     	if (empty) {
                         setGraphic(null);
                         setText(null);
                     	} 	else {
                     		 JFXButton btn = new JFXButton("Upload Image");
                            btn.setStyle("-fx-background-color: #3d5afe;\n" + 
                           		"  -fx-background-radius: 16.4, 15;\n" + 
                           		"  -fx-background-insets: -1.4, 0;\n" +
                           		"	-fx-text-fill: WHITE;"+
                           		"  -fx-border-radius: 15;\n" + 
                           		"  -fx-pref-width: 250;\n"+
                           		"  -fx-pref-height: 20;\n"+
                           		"  -fx-border-width: 2;\n"+ 
                           		"  -fx-padding: 0;\n" + 
                           		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
                           		"  -fx-font-size: 10;");
                         	btn.setOnAction(event -> {
                        	 	StockItem item1 = getTableView().getItems().get(getIndex());
                        	 	System.out.println(item1.getName());
                        	 	Stage stage = new Stage();
                        	 	BorderPane pane =  new BorderPane();
                        	 	pane.setPrefHeight(500);
                        	 	pane.setPrefWidth(800);
                        	 	pane.setTop(get_header(item1.getName()));
                        	 	VBox bottom = new VBox();
                        	 	bottom = get_footer(item1.getName(),stage);
                        	 	pane.setBottom(bottom);
                        	 	pane.setMargin(bottom, new Insets(10,10,10,10));
                        	 	
                        	 	VBox center = new VBox();
                        	 	center = get_centre();
                        	 	pane.setCenter(center);
                        	 	pane.setMargin(center, new Insets(10,10,10,10));
                        	 	Scene scene = new Scene(pane);
                        	 	stage.setX(230+165);
                        	 	stage.setY(100);
                        	 	stage.setScene(scene);
                        	 	stage.show();
                         	});
                          	setGraphic(btn);
                         	setText(null);
                     	}
                 	}
             	};
             	return cell;
         		}
             };
             actionCol.setCellFactory(cellFactory1);
             tableView.setItems(dataList);
             tableView.getColumns().addAll(columnName, columnStock,columnmop,columnmrp,columnksprice, columntax, columnhsn, actionCol);
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
	       
	   	 ExecutorService service = Executors.newFixedThreadPool(4);
		    service.submit(new Runnable() {
		        public void run() {

			        send_http_request();
			
		        }
		    });
		 ExecutorService service1 = Executors.newFixedThreadPool(4);
		    service1.submit(new Runnable() {
		        public void run() {
		      	   send_http_request_orders();
		      	     
		        }
		    });
	       
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
        search_area.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                	String words[] = search_area.getText().split(" ");
                    String search_string = new String("%");
                     for (String word : words) {
                         if (word.equals(" ")) {
                             continue;
                         } else {
                             search_string = search_string + word + "%";
                         }
                     }
                     try {
                         search_string = java.net.URLEncoder.encode(search_string, "UTF-8");
                         search_products(search_string);
                     } catch (UnsupportedEncodingException e) {
                         e.printStackTrace();
                     }
                   
                }
            }
        });
        
        search.setOnMouseClicked(value->{
        	String words[] = search_area.getText().split(" ");
           String search_string = new String("%");
            for (String word : words) {
                if (word.equals(" ")) {
                    continue;
                } else {
                    search_string = search_string + word + "%";
                }
            }
            try {
                search_string = java.net.URLEncoder.encode(search_string, "UTF-8");
                search_products(search_string);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
           
        });
       
        clear_search.setOnMouseClicked(value -> {search_area.setText("");});

        
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
	
	public void search_products(String search_string)
	{
		try {
			dataList.clear();
			System.out.println("");
			String url ="http://khuranasales.co.in/work/get_searched_products_desktop.php?search="+search_string;
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
		    	 dataList.add(new StockItem(object.getString("Name"),object.getString("stock"),object.getString("mrp"),object.getString("mop"),object.getString("ksprice"),object.getString("tax"),object.getString("hsn")));
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
		    	 dataList.add(new StockItem(object.getString("Name"),object.getString("stock"),object.getString("mrp"),object.getString("mop"),object.getString("ksprice"), object.getString("tax"),object.getString("hsn")));
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
