package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class inventoriesViewCreditNoteAdapter extends RecyclerView.Adapter<inventoriesViewCreditNoteAdapter.ViewHolder> {
    public ArrayList<creditNote> inventories_objects = new ArrayList<>();
    public Activity mActivity;
    public String class_name;
    private static final int NUM_OF_TILE_COLORS = 8;
    public Resources res = null;
    public TypedArray mColors;
    public recyclerViewItemClickListener listener ;
    public inventoriesViewCreditNoteAdapter(ArrayList<creditNote> inventories_objects, Activity activity, String class_name , recyclerViewItemClickListener mlistener)
    {
        this.mActivity = activity;
        this.inventories_objects = inventories_objects;
        this.class_name = class_name;
        this.listener = mlistener;
    }
    private int pickColor(String key) {
        // String.hashCode() is not supposed to change across java versions, so
        // this should guarantee the same key always maps to the same color
        res = mActivity.getResources();
         mColors  = res.obtainTypedArray(R.array.letter_tile_colors);;
        final int color = Math.abs(key.hashCode()) % NUM_OF_TILE_COLORS;
        try {
            return mColors.getColor(color, Color.BLACK);
        } finally {
            mColors.recycle();
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (class_name)
        {
            case "creditNote":
                creditNote note = inventories_objects.get(position);
                holder.credit_note_ledger_name.setText(note.getCredit_ledger_name());
                holder.credit_note_narration.setText(note.getNarration());
                holder.credit_note_amount.setText("Rs.  "+note.getCredit_amount()+" / -");
                holder.credit_note_date.setText(note.getCredit_date());
                holder.colored_initial_view.setText(""+note.getCredit_ledger_name().charAt(0));
                Drawable backgroundDrawable = holder.colored_initial_view.getBackground();
                DrawableCompat.setTint(backgroundDrawable, pickColor(note.getCredit_ledger_name()));
                holder.colored_initial_view.setBackground(backgroundDrawable);
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      switch (class_name) {
          case "creditNote":
              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_note_list_view_row_layout, parent, false);
              ViewHolder viewHolder = new ViewHolder(view);
              return viewHolder;
      }
      return  null;
    }

    @Override
    public int getItemCount() {
        return inventories_objects.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        public TextView credit_note_ledger_name;
        public TextView credit_note_amount;
        public TextView credit_note_narration;
        public TextView colored_initial_view;
        public TextView credit_note_date;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            credit_note_amount = (TextView)itemView.findViewById(R.id.credit_note_amount);
            credit_note_narration = (TextView)itemView.findViewById(R.id.credit_note_narration);
            credit_note_ledger_name = (TextView)itemView.findViewById(R.id.credit_note_ledger_name);
            credit_note_date = (TextView)itemView.findViewById(R.id.credit_note_date);
            colored_initial_view = (TextView)itemView.findViewById(R.id.colored_initial_view);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}
