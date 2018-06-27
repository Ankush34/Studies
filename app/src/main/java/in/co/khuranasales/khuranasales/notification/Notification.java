package in.co.khuranasales.khuranasales.notification;

import android.util.Log;

public class Notification {
    int id;
    String title;
    String message;
    String type;
    String date;
    String product_id;
    String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Notification()
    {

    }
    public Notification(String title, String message,  String type, String Date, String product_id, String status)
    {
        this.title = title;
        this.message = message;
        this.type = type;
        this.date = Date;
        this.product_id = product_id;
        this.status = status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
