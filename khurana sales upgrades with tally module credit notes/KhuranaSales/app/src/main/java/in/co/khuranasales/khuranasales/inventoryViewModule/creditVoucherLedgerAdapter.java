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
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_ledger;

public class creditVoucherLedgerAdapter extends ArrayAdapter<credit_note_ledger> {

    public Context context;
    public ArrayList<credit_note_ledger> credit_note_ledgers_list = new ArrayList<>();
    public creditVoucherLedgerAdapter(Context context, ArrayList<credit_note_ledger> credit_note_ledgers_list) {
        super(context, R.layout.credit_note_ledger_list_row, credit_note_ledgers_list);
        this.context = context;
        this.credit_note_ledgers_list = credit_note_ledgers_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.credit_note_ledger_list_row, parent, false);
        // 3. Get the two text view from the rowView
        TextView ledger_name = (TextView)rowView.findViewById(R.id.ledger_name);
        TextView ledger_amount = (TextView)rowView.findViewById(R.id.ledger_amount);

        credit_note_ledger ledger = credit_note_ledgers_list.get(position);
        ledger_name.setText(ledger.getLedger_name());
        ledger_amount.setText("Rs. "+(-Double.parseDouble(ledger.getLedger_amount()))+" /-");

        // 5. return rowView
        return rowView;
    }

    @Override
    public int getPosition(@Nullable credit_note_ledger ledger) {
        return super.getPosition(ledger);
    }
}
