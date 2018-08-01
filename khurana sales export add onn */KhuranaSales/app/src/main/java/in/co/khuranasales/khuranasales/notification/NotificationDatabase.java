package in.co.khuranasales.khuranasales.notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product;

public class NotificationDatabase extends SQLiteOpenHelper {
 private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notificationManager";
    private static final String TABLE_CONTACTS = "notification";
    private static final String KEY_ID = "id";
    private static final String KEY_Title = "title";
    private static final String KEY_Message = "message";
    private static final String KEY_Type = "notification_type";
    private static final String KEY_Notification_Date = "notification_date";
    private static final String KEY_Product_Id = "product_id";
    private static final String KEY_Status = "notification_status";
    public NotificationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("NotificationDatabase","creating table...");
        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +KEY_Title + " TEXT," + KEY_Message + " TEXT,"
                + KEY_Type + " TEXT,"+ KEY_Notification_Date+ " TEXT," + KEY_Product_Id+ " TEXT," +KEY_Status+" TEXT" + ")";
        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

   public void addNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Title, notification.getTitle());
        values.put(KEY_Message, notification.getMessage());
        values.put(KEY_Type, notification.getType());
        values.put(KEY_Notification_Date, notification.getDate());
        values.put(KEY_Product_Id,notification.getProduct_id());
        values.put(KEY_Status,notification.getStatus());

       // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(cursor.getInt(0));
                notification.setTitle(cursor.getString(1));
                notification.setMessage(cursor.getString(2));
                notification.setType(cursor.getString(3));
                notification.setDate(cursor.getString(4));
                notification.setProduct_id(cursor.getString(5));
                notification.setStatus(cursor.getString(6));
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        db.close();
        return notifications;
    }

    public void delete_entry(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS,"name=?",new String[]{name});
        db.close();
    }

    public int get_notification_recent_count()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_CONTACTS+" where notification_status=?",new String[]{"Pending"});
        return cursor.getCount();
    }
    public void update_status(Notification n1, String status) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Title, n1.getTitle());
        values.put(KEY_Message,n1.getMessage());
        values.put(KEY_Type,n1.getType());
        values.put(KEY_Notification_Date,n1.getDate());
        values.put(KEY_Status,status);
        int d= db.update(TABLE_CONTACTS,values,"id=? ",new String[]{""+n1.getId()});
        db.close();

    }
}
