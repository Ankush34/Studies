package in.co.khuranasales.khuranasales.manageByInventoryModule;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class inventoryItemAdapter extends RecyclerView.Adapter<inventoryItemAdapter.ViewHolder> {

    public Activity mActivity;
    public ArrayList<inventoryItem> items;
    public recyclerViewItemClickListener inventory_click_listener;
    private int lastPosition = -1;
    public inventoryItemAdapter(Activity mActivity, ArrayList<inventoryItem> inventory_items, recyclerViewItemClickListener click_listener)
    {
        this.mActivity = mActivity;
        this.items = inventory_items;
        this.inventory_click_listener = click_listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        inventoryItem item= items.get(position);
        if(item.getType().equals("Category"))
        {
            holder.inventory_name.setText(item.getName());
            if(item.getType().equals("Category"))
            {
                holder.inventory_next.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.inventory_next.setVisibility(View.INVISIBLE);
            }
            holder.item_type.setText(item.getCategory());
            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inventory_click_listener.onItemClick(v, position);

                }
            });
            holder.inventory_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inventory_click_listener.onItemClick(v, position);

                }
            });

        }
        else
        {
            holder.item_name.setText(item.getName());
            holder.item_stock.setText("QT("+item.getStock()+")");
            holder.mrp_price.setText(""+item.getMrp());
            holder.mop_price.setText(""+item.getMop());
            holder.ks_price.setText(""+item.getKs_price());
            holder.view_batch_numbers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inventory_click_listener.onItemClick(v, position);
                }
            });
            holder.layout_card_small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(),Product_desc_activity.class);
                    intent.putExtra("product_id",item.getProduct_id());
                    intent.putExtra("name",item.getName());
                    if(item.getLinks().equals(""))
                    {
                        intent.putExtra("link","NULL");
                    }
                    else
                    {
                        intent.putExtra("link",item.getLinks());
                    }
                    intent.putExtra("stock",item.getStock());
                    intent.putExtra("mrp",(int)item.getMrp());
                    intent.putExtra("mop",(int)item.getMop());
                    intent.putExtra("ksprice",(int)item.getKs_price());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item_recycler_layout_card, parent,false);
                inventoryItemAdapter.ViewHolder viewHolder = new inventoryItemAdapter.ViewHolder(view);
                return viewHolder;
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_card_items, parent,false);
                inventoryItemAdapter.ViewHolder viewHolder1 = new inventoryItemAdapter.ViewHolder(view1);
                return viewHolder1;
        }
        return null;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        inventoryItem item = items.get(position);
        switch (item.getType())
        {
            case "Category":
                return 0;
            case "Item":
                return 1;
        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        /* category card items */
        public TextView inventory_name;
        public ImageView inventory_next;
        public RelativeLayout main_layout;
        public TextView item_type;
        /* ----------------- */

        public TextView item_name;
        public TextView item_stock;
        public ImageView pencil;
        public TextView mrp_price;
        public TextView mop_price;
        public TextView ks_price;
        public FrameLayout layout_card_small;
        public ImageView view_batch_numbers;


        public ViewHolder(View itemView){
            super(itemView);
        inventory_name = (TextView)itemView.findViewById(R.id.inventory_name);
        inventory_next = (ImageView)itemView.findViewById(R.id.inventory_next);
        main_layout = (RelativeLayout)itemView.findViewById(R.id.layout_card);
        item_type = (TextView)itemView.findViewById(R.id.item_type);

        item_name = (TextView)itemView.findViewById(R.id.mobile_name);
        item_stock = (TextView)itemView.findViewById(R.id.stock);
        pencil = (ImageView)itemView.findViewById(R.id.pencil);
        mrp_price = (TextView)itemView.findViewById(R.id.mrp_price);
        mop_price = (TextView)itemView.findViewById(R.id.mop_price);
        ks_price = (TextView)itemView.findViewById(R.id.ks_price);
        layout_card_small = (FrameLayout)itemView.findViewById(R.id.list_item_card_small);
        view_batch_numbers = (ImageView)itemView.findViewById(R.id.view_batch_numbers);

        }

        @Override
        public void onClick(View v) {
        }
    }

}
