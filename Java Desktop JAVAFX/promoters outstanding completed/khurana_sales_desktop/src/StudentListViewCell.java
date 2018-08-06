import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        	System.out.println("picking data from cell: : "+obj.getImage_source());
        	File file = new File(obj.getImage_source());
            Image image = new Image(file.toURI().toString());
        	view.setImage(image);
        	
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
}
