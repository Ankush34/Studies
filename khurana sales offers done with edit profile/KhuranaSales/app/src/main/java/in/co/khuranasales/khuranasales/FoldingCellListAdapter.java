package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Product> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    public FoldingCellListAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
        colors.add(Color.parseColor("#1976D2"));
        colors.add(Color.parseColor("#303F9F"));
        colors.add(Color.parseColor("#00B8D4"));
        colors.add(Color.parseColor("#F4511E"));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // get item for selected view
        final Product item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.reinvoice = (TextView)cell.findViewById(R.id.reinvoice);
            viewHolder.request_cancellation_text = (TextView)cell.findViewById(R.id.request_cancellation_text);
            viewHolder.top_bar_content_layout = (RelativeLayout)cell.findViewById(R.id.top_bar_content_layout);
            viewHolder.products_bought_side_bar = (RelativeLayout)cell.findViewById(R.id.products_bought_side_bar);
            viewHolder.price = (TextView) cell.findViewById(R.id.title_price);
            viewHolder.time = (TextView) cell.findViewById(R.id.title_time_label);
            viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
            viewHolder.fromAddress = (TextView) cell.findViewById(R.id.title_from_address);
            viewHolder.toAddress = (TextView) cell.findViewById(R.id.title_to_address);
            viewHolder.to_address_content_view = (TextView) cell.findViewById(R.id.to_address);
            viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
            viewHolder.pledgePrice = (TextView) cell.findViewById(R.id.title_pledge);
            viewHolder.total= (TextView) cell.findViewById(R.id.total);
            viewHolder.paid= (TextView) cell.findViewById(R.id.title_weight);
            viewHolder.paid1=(TextView) cell.findViewById(R.id.head_image_right_text);
            viewHolder.qant= (TextView) cell.findViewById(R.id.head_image_left_text);
            viewHolder.price1=(TextView) cell.findViewById(R.id.head_image_center_text);
            viewHolder.head = (ImageView) cell.findViewById(R.id.head_image);
            viewHolder.order_id = (TextView)cell.findViewById(R.id.order_id);
            viewHolder.side_strip = (RelativeLayout)cell.findViewById(R.id.relative_layout_side_strip);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.price.setText(""+item.getPrice_mop());
      // viewHolder.time.setText(item.getTime());
      //  viewHolder.fromAddress.setText(item.getFromAddress());
      // viewHolder.toAddress.setText(item.getToAddress());
        if(item.getOrder_status().equals("CANCELLED"))
        {
            viewHolder.side_strip.setBackgroundColor(getContext().getResources().getColor(R.color.red));
            viewHolder.request_cancellation_text.setText("REQUEST Reorder");
        }
        else
        {
            viewHolder.side_strip.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            viewHolder.request_cancellation_text.setText("REQUEST Cancellation");
        }
        viewHolder.top_bar_content_layout.setBackgroundColor(colors.get(position%4));
        viewHolder.products_bought_side_bar.setBackgroundColor(colors.get(position%4));
        viewHolder.date.setText(item.get_sales_order_date());
        viewHolder.requestsCount.setText(String.valueOf(item.get_Stock()));
        viewHolder.qant.setText(String.valueOf(item.get_Stock()));
        viewHolder.pledgePrice.setText(""+(item.get_Stock()*item.getPrice_mop()));
        viewHolder.total.setText(""+(item.get_Stock()*item.getPrice_mop()));
        viewHolder.fromAddress.setText(item.get_Name());
        viewHolder.paid.setText(""+(item.get_Stock()*item.getPrice_mop()));
        viewHolder.paid1.setText(""+(item.get_Stock()*item.getPrice_mop()));
        viewHolder.price1.setText(""+(item.get_Stock()*item.getPrice_mop()));
        viewHolder.to_address_content_view.setText(""+item.get_to_address());
        viewHolder.toAddress.setText(""+item.get_to_address());
        final FoldingCell finalCell = cell;
        viewHolder.reinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new getDataAndCreatenvoice().execute(item.getSales_order_invoice_number());
            }
        });
        viewHolder.request_cancellation_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCell.fold(false);
                registerFold(position);
                if(item.getOrder_status().equals("CANCELLED"))
                {
                    update_status(item.get_sales_order_number(),position,viewHolder,"ORDERED");

                }
                else
                {
                    update_status(item.get_sales_order_number(),position,viewHolder,"CANCELLED");
                }
            }
        });
        String link[]=item.get_link().split(",");
        Picasso.with(getContext())
                .load(link[0])
                .into(viewHolder.head);
        viewHolder.order_id.setText("# Order No."+item.getSales_order_invoice_number());
        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    // View lookup cache
    private static class ViewHolder {
       ImageView head ;
        TextView reinvoice;
        TextView price;
        TextView price1;
        TextView pledgePrice;
        TextView paid;
        TextView paid1;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
        TextView total;
        TextView qant;
        TextView order_id;
        TextView to_address_content_view ;
        RelativeLayout products_bought_side_bar;
        RelativeLayout top_bar_content_layout;
        RelativeLayout side_strip;
        TextView request_cancellation_text;

    }
    public void update_status(Integer product_order_number, final int position, final ViewHolder viewHolder, final String status)
    {
        JsonArrayRequest request_cancellation = new JsonArrayRequest(AppConfig.update_order_status + product_order_number+"&&order_status="+status, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject obj = response.getJSONObject(0);
                    if(obj.getBoolean("success"))
                    {
                        Toast.makeText(getContext(),"Successfully "+status,Toast.LENGTH_LONG).show();
                        if(status.equals("CANCELLED"))
                        {
                            getItem(position).setOrder_status("CANCELLED");
                            viewHolder.side_strip.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                            viewHolder.request_cancellation_text.setText("REQUEST Reorder");
                        }
                        else if(status.equals("ORDERED"))
                        {
                            getItem(position).setOrder_status("ORDERED");
                            viewHolder.side_strip.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                            viewHolder.request_cancellation_text.setText("REQUEST Cancellation");
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Sorry could not cancel your order",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error: ",error.getMessage());
                Toast.makeText(getContext(),"Some error has taken place",Toast.LENGTH_LONG).show();
            }
        });
        request_cancellation.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(request_cancellation);
    }

    class getDataAndCreatenvoice extends AsyncTask<Integer,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... invoice_number) {
            JsonArrayRequest invoice_request = new JsonArrayRequest(AppConfig.get_invoice_data_reinvoice + invoice_number[0], new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        Log.d("Invoice",""+response.toString());
                        JSONObject object = response.getJSONObject(0);
                        String customer_name = object.getString("customer_name");
                        String customer_phone = object.getString("customer_phone");
                        String invoice_date = object.getString("invoice_date");
                        String customer_address = object.getString("customer_address");
                        String promoter_email = object.getString("promoter_email");
                        String total_payment_amount = object.getString("total_payment");
                        String customer_gst = object.getString("customer_gst");
                        String customer_email = object.getString("customer_email");
                        String cash_payment = object.getString("cash_payment");
                        String paytm_payment = object.getString("paytm_payment");
                        String card_payment = object.getString("card_payment");
                        String finance_payment = object.getString("finance_payment");
                        String financer = object.getString("financer");
                        JSONArray products = object.getJSONArray("products_info");
                        ArrayList<Product> products_list = new ArrayList<>();
                        for(int i  =0 ; i < products.length(); i++)
                        {
                            JSONObject pro_json = products.getJSONObject(i);
                            Product pro = new Product();
                            pro.set_Name(pro_json.getString("product_name"));
                            String batch_selected[] = pro_json.getString("batches_selected").split(",");
                            for(int j = 0; j < batch_selected.length;j++)
                            {
                                Batch batch = new Batch("",batch_selected[j]);
                                pro.addSelected_batch_numbers(batch);
                            }
                            pro.setPrice_mop(Integer.parseInt(pro_json.getString("price_mop")));
                            pro.set_Stock(Integer.parseInt(pro_json.getString("product_count")));
                            pro.setProduct_HSN(pro_json.getString("product_hsn"));
                            products_list.add(pro);
                        }
                        Intent intent = new Intent(getContext(),Invoice_Activity.class);
                        intent.putExtra("class_name","final_cart_activity");
                        intent.putExtra("customer_name", customer_name);
                        intent.putExtra("customer_phone", customer_phone);
                        intent.putExtra("customer_email", customer_email);
                        intent.putExtra("customer_address", customer_address);
                        intent.putExtra("promoter_email", promoter_email);
                        intent.putExtra("customer_gst", customer_gst);
                        intent.putExtra("total_payment",total_payment_amount);
                        intent.putExtra("Card",Integer.parseInt(object.getString("card_payment")));
                        intent.putExtra("Cash",Integer.parseInt(object.getString("cash_payment")));
                        intent.putExtra("Cheque",Integer.parseInt(object.getString("cheque_payment")));
                        intent.putExtra("Paytm",Integer.parseInt(object.getString("paytm_payment")));
                        intent.putExtra("Finance",Integer.parseInt(object.getString("finance_payment")));
                        intent.putExtra("Financer",object.getString("financer"));
                        intent.putExtra("total_cgst_amount",object.getString("total_cgst_amount"));
                        intent.putExtra("total_sgst_amount",object.getString("total_sgst_amount"));
                        intent.putParcelableArrayListExtra("products", products_list);
                        getContext().startActivity(intent);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(invoice_request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
