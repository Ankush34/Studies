package in.co.khuranasales.khuranasales.inventoryViewModule;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_item;

public class creditVoucherItemAdapter extends ArrayAdapter<credit_note_item> {

    public Context context;
    public ArrayList<credit_note_item> credit_note_items_list = new ArrayList<>();
    public creditVoucherItemAdapter(Context context, ArrayList<credit_note_item> credit_note_items) {
        super(context, R.layout.credit_note_item_list_row, credit_note_items);
        this.context = context;
        this.credit_note_items_list = credit_note_items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.credit_note_item_list_row, parent, false);
        // 3. Get the two text view from the rowView
        TextView item_name = (TextView)rowView.findViewById(R.id.item_name);
        TextView item_price = (TextView)rowView.findViewById(R.id.item_price);
        TextView item_actual_quantity = (TextView)rowView.findViewById(R.id.item_actual_quantity);
        TextView item_billed_quantity = (TextView)rowView.findViewById(R.id.item_billed_quantity);
        TextView item_ledgers_billed = (TextView)rowView.findViewById(R.id.item_ledgers_billed);
        TextView item_imei_included = (TextView)rowView.findViewById(R.id.item_imei_included);
        TextView item_account_ledgers = (TextView)rowView.findViewById(R.id.item_accounting_ledgers_billed);
        TextView item_gross_amount = (TextView)rowView.findViewById(R.id.item_gross_amount);

        credit_note_item item = credit_note_items_list.get(position);
        item_name.setText(item.getItem_name());
        item_price.setText(item.getRate());
        item_actual_quantity.setText(item.getActual_quantity());
        item_billed_quantity.setText(item.getBilled_quantity());

        StringBuilder ledgers_builder = new StringBuilder();
        for(int  i = 0 ; i < item.ledgers_included.size(); i++)
        {
            ledgers_builder.append(""+item.ledgers_included.get(i).getLedger_name()+"\n( Rs: "+(-Double.parseDouble(item.ledgers_included.get(i).getLedger_amount()))+" )"+" \n\n");
        }
        item_ledgers_billed.setText(ledgers_builder.toString());


        StringBuilder accounting_ledgers_builder = new StringBuilder();
        for(int  i = 0 ; i < item.batches.size(); i++)
        {
            accounting_ledgers_builder.append(""+item.account_ledgers_included.get(i).getLedger_name()+"\n( Rs: "+(-(Double.parseDouble(item.account_ledgers_included.get(i).getLedger_amount())))+" )"+" \n\n");
        }
        item_account_ledgers.setText(accounting_ledgers_builder);

        StringBuilder batches_builder = new StringBuilder();
        for(int  i = 0 ; i < item.batches.size(); i++)
        {
            batches_builder.append(""+item.batches.get(i).getBatch()+"\n("+item.batches.get(i).getGodown_location()+")"+" \n\n");
        }
        item_imei_included.setText(batches_builder.toString());
        item_gross_amount.setText("Rs. "+(-(Double.parseDouble(item.getItem_amount())))+" /-");
        // 5. retrn rowView
        return rowView;
    }

    @Override
    public int getPosition(@Nullable credit_note_item item) {
        return super.getPosition(item);
    }
}
