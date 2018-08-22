package in.co.khuranasales.khuranasales;

/**
 * Created by Ankush khurana on 7/30/2017.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    private List<Address> productItems;
    public Adapter2(String[] dataArgs){
        dataSource = dataArgs;
    }
    public Adapter2(Activity activity, List<Address> movieItems) {
        this.activity = activity;
        this.productItems = movieItems;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_final_address_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Address a=productItems.get(position);
        if(a.getStatus())
        {
            holder.view1.setVisibility(View.INVISIBLE);
            holder.view2.setVisibility(View.VISIBLE);
        }
        else if(!a.getStatus())
        {
            holder.view1.setVisibility(View.VISIBLE);
            holder.view2.setVisibility(View.INVISIBLE);
        }
        holder.text1.setText("Name:     "+a.getAddress());
        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position=holder.getAdapterPosition();
                for (int d=0;d<productItems.size();d++)
                {
                   productItems.get(d).setStatus(false);

                }
                productItems.get(position).setStatus(true);
                Buy_final_activity.adapter2.notifyDataSetChanged();
            }
        });
        holder.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
               productItems.get(position).setStatus(false);
                Buy_final_activity.adapter2.notifyDataSetChanged();
            }
        });
       }
    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text1;
        public ImageView view1;
        public ImageView view2;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Address a=productItems.get(position);
                }
            });
            text1=(TextView)itemView.findViewById(R.id.name);
           view1=(ImageView)itemView.findViewById(R.id.colorless_tick);
            view2=(ImageView)itemView.findViewById(R.id.colored_tick);


        }

    }
}