package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PromoterListAdapter extends RecyclerView.Adapter<PromoterListAdapter.ViewHolder> {

    public ArrayList<String> promoters;
    public ArrayList<ProductListElement> productListElements = new ArrayList<>();
    public Context context;
    public recyclerViewItemClickListener itemClickListener;
    public PromoterListAdapter(Context context, ArrayList<String> promoters)
    {
        this.context = context;
        this.promoters = promoters;
    }


    public PromoterListAdapter(Context context, ArrayList<ProductListElement> products, int i)
    {
        this.context = context;
        this.productListElements =  products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.promoter_name_list_layout,parent,false);

       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(context.getClass().getSimpleName().equals("Product_view_activity")) {
            ProductListElement productListElement = productListElements.get(position);
            holder.name.setText((position + 1) + ":    " + productListElements.get(position).getProduct_name());
            if (productListElement.getProduct_index() == -1) {
                productListElement.setProduct_index(position);
            }
        }
        else
        {
            holder.name.setText((position+1)+":    "+promoters.get(position));
            holder.promoter_name_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int promoter_position = PromotersRecyclerAdapter.find_promoter(promoters.get(position));
                    SoldItemPromotersActivity.promoters_recycler.smoothScrollToPosition(promoter_position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(context.getClass().getSimpleName().equals("Product_view_activity"))
        {
            return productListElements.size();
        }
        else
        {
            return promoters.size();
        }
    }

    public void setClickListener(recyclerViewItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public LinearLayout promoter_name_layout;
        public ViewHolder(View itemView) {
            super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        promoter_name_layout = (LinearLayout)itemView.findViewById(R.id.promoter_name_layout);
        if(context.getClass().getSimpleName().equals("Product_view_activity"))
        {
            itemView.setOnClickListener(this);
        }
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
