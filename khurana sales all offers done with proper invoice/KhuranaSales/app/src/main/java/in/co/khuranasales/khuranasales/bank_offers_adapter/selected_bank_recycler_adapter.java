package in.co.khuranasales.khuranasales.bank_offers_adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class selected_bank_recycler_adapter extends RecyclerView.Adapter<selected_bank_recycler_adapter.ViewHolder> {
    public ArrayList<String> banks;
    public recyclerViewItemClickListener item_click_listener;
    public selected_bank_recycler_adapter(ArrayList<String> banks, recyclerViewItemClickListener item_click_listener)
    {
        this.banks = banks;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public selected_bank_recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_bank_row, parent, false);
        selected_bank_recycler_adapter.ViewHolder viewHolder = new selected_bank_recycler_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(selected_bank_recycler_adapter.ViewHolder holder, int position) {
        holder.bank_name.setText(banks.get(position));
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bank_name;
        public ImageView delete;
        public EditText price;

        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            bank_name = (TextView)itemView.findViewById(R.id.bank_name);
            delete = (ImageView)itemView.findViewById(R.id.remove_bank);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
