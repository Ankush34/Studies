package in.co.khuranasales.khuranasales.add_new_product;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.ledger.Ledger;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class search_ledger_adapter extends RecyclerView.Adapter<search_ledger_adapter.ViewHolder> {
    public ArrayList<Ledger> ledgers;
    public recyclerViewItemClickListener item_click_listener;
    public search_ledger_adapter(ArrayList<Ledger> ledgers, recyclerViewItemClickListener item_click_listener)
    {
        this.ledgers = ledgers;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public search_ledger_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_name_list_layout, parent, false);
        search_ledger_adapter.ViewHolder viewHolder = new search_ledger_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(search_ledger_adapter.ViewHolder holder, int position) {
        holder.product.setText(ledgers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ledgers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            product = (TextView)itemView.findViewById(R.id.name);
            product.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}

