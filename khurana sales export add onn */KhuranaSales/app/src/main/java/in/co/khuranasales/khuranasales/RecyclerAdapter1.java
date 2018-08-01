package in.co.khuranasales.khuranasales;
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

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    public AppConfig appConfig;
    private List<Product> productItems;
    public  recyclerViewItemClickListener mListener;
    public RecyclerAdapter1(String[] dataArgs){
        dataSource = dataArgs;
    }
    public RecyclerAdapter1(Activity activity, List<Product> movieItems, recyclerViewItemClickListener click_listener) {
        this.activity = activity;
        this.productItems = movieItems;
        mListener = click_listener;
        appConfig = new AppConfig(activity.getApplicationContext());

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_final_recycler_card1, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,mListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Product m=productItems.get(position);
        holder.text1.setText(m.get_Name());
        if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
        {
            holder.text2.setText(" "+m.getPrice_ks()+" /-");

        }
        else
        {
            holder.text2.setText(" "+m.getPrice_mop()+" /-");
        }
        holder.text3.setText(" "+m.get_Stock()+" Units");
        holder.text4.setText(" "+m.getPrice_mop()*m.get_Stock()+"  /-");
           }
    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text1;
        public TextView text2;
        public TextView text3;
        public TextView text4;
        public ImageView display_imei;
        public ViewHolder(final View itemView, recyclerViewItemClickListener click_listener) {
            super(itemView);
            mListener = click_listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Product m=productItems.get(position);
                }
            });
            text1=(TextView)itemView.findViewById(R.id.desc1);
            text2=(TextView)itemView.findViewById(R.id.ksprice);
            text3=(TextView)itemView.findViewById(R.id.bought);
            text4=(TextView)itemView.findViewById(R.id.total);
            display_imei = (ImageView)itemView.findViewById(R.id.display_imei);
            display_imei.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());
        }
    }
  }