package in.co.khuranasales.khuranasales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "productsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "products";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_stock = "stock";
    private static final String KEY_price_mrp = "price_mrp";
    private static final String KEY_product_id = "product_id";
    private static final String KEY_price_mop = "price_mop";
    private static final String KEY_price_ks = "price_ks";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +KEY_product_id + " INTEGER," + KEY_NAME + " TEXT,"
                + KEY_stock + " INTEGER," +KEY_price_mrp + " INTEGER," + KEY_price_mop + " INTEGER," +KEY_price_ks + " INTEGER" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_product_id, product.getProduct_id());
        values.put(KEY_NAME, product.get_Name());
        values.put(KEY_stock, product.get_Stock());
        values.put(KEY_price_mrp,product.getPrice_mrp());
        values.put(KEY_price_mop,product.getPrice_mop());
        values.put(KEY_price_ks,product.getPrice_ks());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Product getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_stock }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(cursor.getString(2),cursor.getInt(3));
        product.setProduct_id(cursor.getInt(1));
        product.setPrice_mrp(cursor.getInt(4));
        product.setPrice_mop(cursor.getInt(5));
        product.setPrice_ks(cursor.getInt(6));

        // return contact
        cursor.close();
        db.close();
        return product;
    }

    // Getting All Contacts
    public List<Product> getAllProducts() {
        List<Product> contactList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product contact = new Product();
                contact.setProduct_id(cursor.getInt(1));
                contact.set_Name(cursor.getString(2));
                contact.set_Stock(Integer.parseInt(cursor.getString(3)));
                contact.setPrice_mrp(cursor.getInt(4));
                contact.setPrice_mop(cursor.getInt(5));
                contact.setPrice_ks(cursor.getInt(6));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        db.close();
        return contactList;
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();

        // return count
        return cursor.getCount();
    }
    public boolean check_avail(Integer product_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_CONTACTS,
                new String[]{KEY_product_id},
                KEY_product_id + "=?",
                new String[]{String.valueOf(product_id)}, null, null, null, null);
        cursor.moveToFirst();
        Log.d("cursor size",""+cursor.getCount());
        if (cursor.getCount()==0)
        {
            cursor.close();
            db.close();
            return false;
        }
        else
        {
            cursor.close();
            db.close();
            return true;
        }
    }
    public int see(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns ={KEY_stock};
        Cursor cursor = db.query(TABLE_CONTACTS,columns, "name='"+name+"'", null, null, null, null);
        cursor.moveToFirst();
        int con=0;
        if (cursor.getCount() > 0)
        {
            con = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return con;
    }

    public int get_bought_count(Integer product_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_CONTACTS,
                new String[]{KEY_stock},
                KEY_product_id + "=?",
                new String[]{String.valueOf(product_id)}, null, null, null, null);
        Log.d("product_id",""+product_id);
        cursor.moveToFirst();
        int count=cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
    public void set_bought_count(Product p1,int count) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, p1.get_Name());
        values.put(KEY_product_id,p1.getProduct_id());
        values.put(KEY_stock,count);
        values.put(KEY_price_mrp,p1.getPrice_mrp());
        values.put(KEY_price_mop,p1.getPrice_mop());
        values.put(KEY_price_ks,p1.getPrice_ks());
        int d= db.update(TABLE_CONTACTS,values,"name=? ",new String[]{p1.get_Name()});
        db.close();

    }
    public void delete_entry(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS,"name=?",new String[]{name});
        db.close();
    }
}
