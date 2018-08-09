package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPromoterProductListAdapter extends ArrayAdapter<Product> {

    public Context context;
    public ArrayList<Product> productsArrayList = new ArrayList<>();
    public MyPromoterProductListAdapter(Context context, ArrayList<Product> productArrayList) {
        super(context, R.layout.promoter_products_list_row, productArrayList);
        this.context = context;
        this.productsArrayList = productArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.promoter_products_list_row, parent, false);
        // 3. Get the two text view from the rowView
        TextView name = (TextView) rowView.findViewById(R.id.product_name);
        TextView price = (TextView) rowView.findViewById(R.id.product_sold_price);
        TextView customer_name = (TextView) rowView.findViewById(R.id.product_sold_customer_name);
        TextView customer_contact = (TextView) rowView.findViewById(R.id.product_sold_customer_phone);
        TextView quantity = (TextView) rowView.findViewById(R.id.product_sold_quantity);
        TextView order_status = (TextView) rowView.findViewById(R.id.product_sold_status);
        TextView invoice_status = (TextView) rowView.findViewById(R.id.product_sold_invoice_status);

        Log.d("position for inflation",""+position);
        name.setText(productsArrayList.get(position).get_Name());
        Product pro = productsArrayList.get(position);
        price.setText("Price:     Rs  "+pro.getPrice_ks()+"/-");
        customer_name.setText("Customer Name:    "+pro.getCustomer_name());
        customer_contact.setText("Contact:     "+pro.getCustomer_contact());
        quantity.setText("Quantity Sold:     "+pro.get_Stock()+" Units");
        order_status.setText("Order Status:     "+pro.getOrder_status());
        invoice_status.setText("Invoice Status:     "+pro.getInvoice_status());

        // 5. retrn rowView
        return rowView;
    }

    @Override
    public int getPosition(@Nullable Product item) {
        return super.getPosition(item);
    }
}
