import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class StudentListViewCell extends ListCell<UploadProductPojo>{

	@Override
    public void updateItem(UploadProductPojo obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	VBox box = new VBox();
        	ImageView view  = new ImageView();
        	view.setFitHeight(120);
        	view.setFitWidth(180);
        	
        	VBox description_box = new VBox();
        	Text text_product_name = new Text("Product Name:   "+obj.getName());
        	Text text_product_source = new Text("Product Image Location:   "+obj.getImage_source());
        	Text text_product_date = new Text("Product Image Uploaded Date:   "+obj.getUploaded_date());
        	Text text_product_status = new Text("Product Image Upload Status:   "+obj.getUploaded_status());
        	description_box.getChildren().add(text_product_name);
        	description_box.getChildren().add(text_product_date);
        	description_box.getChildren().add(text_product_source);
        	description_box.getChildren().add(text_product_status);
        	description_box.setMargin(text_product_name, new Insets(5,5,5,5));
        	description_box.setMargin(text_product_date, new Insets(5,5,5,5));
        	description_box.setMargin(text_product_source, new Insets(5,5,5,5));
        	description_box.setMargin(text_product_status, new Insets(5,5,5,5));
        	
            JFXButton btn_upload = new JFXButton(" Delete Image ");
            btn_upload.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    					delete_image_url_from_server(obj.getName(), getItem().getImage_source());
    			}
    			});
            btn_upload.setStyle("-fx-background-color: #DC143C;\n" + 
               		"  -fx-background-radius: 16.4, 15;\n" + 
               		"  -fx-background-insets: -1.4, 0;\n" +
               		"	-fx-text-fill: WHITE;"+
               		"  -fx-border-radius: 15;\n" + 
               		"  -fx-pref-width: 120;\n"+
               		"  -fx-pref-height: 20;\n"+
               		"  -fx-border-width: 2;\n"+ 
               		"  -fx-padding: 0;\n" + 
               		"  -fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4);"+
               		"  -fx-font-size: 10;");
            description_box.getChildren().add(btn_upload);
            description_box.setMargin(btn_upload, new Insets(5,5,5,5));
        	System.out.println("picking data from cell: : "+obj.getImage_source());
        	File file = new File(obj.getImage_source());
        	
            if(obj.isFromUrl())
            {
            	if (obj.getImage_source() != null) {
                    Image image = new Image(obj.getImage_source(), true);
                    image.progressProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            System.out.println("Progress: " + Math.rint(newValue.doubleValue() * 100) + "%");
                        }
                    });
                    view.setImage(image);
                }
            }
            else
            {
            	  Image image = new Image(file.toURI().toString());
            	  view.setImage(image);       	
            }
            
            
        	GridPane pane = new GridPane();
        	pane.add(view, 0, 0);
        	pane.add(description_box, 1, 0);
        	pane.setMargin(description_box, new Insets(10,10,10,10));
        	pane.setMargin(view, new Insets(10,10,10,10));
        	box.getChildren().add(pane);
        	box.setStyle("-fx-background-color: #ffffff;\n"+"-fx-background-radius: 10 10 10 10;\n"+"-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 2, 2);");
        	setGraphic(box);
        }
    }	
	
	   public static void delete_image_url_from_server(String name, String url_to_delete)
       {
    	   System.out.println("You want to delete the url of product id : "+name);
    	   String url="http://khuranasales.co.in/work/delete_image_from_desktop.php";
   		try {
   			URL object=new URL(url);
   			HttpURLConnection con = (HttpURLConnection) object.openConnection();
   			con.setDoOutput(true);
       		con.setDoInput(true);
   			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
   			con.setRequestProperty("Accept", "application/json");
   			con.setRequestMethod("POST");
   			JSONObject parent = new JSONObject();
   			parent.put("product_name", name);
   			parent.put("url", url_to_delete);
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
   			    System.out.println("update links :" + sb.toString());  
   			} else {
   			    System.out.println(con.getResponseMessage());  
   			}  
   		}catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    
       }
}
