package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class expandedCreditNotesAdapter extends RecyclerView.Adapter<expandedCreditNotesAdapter.ViewHolder> {
    public ArrayList<creditNote> credit_notes_list = new ArrayList<creditNote>();
    public recyclerViewItemClickListener open_details_listener;
    public expandedCreditNotesAdapter(ArrayList<creditNote> credit_notes_list, recyclerViewItemClickListener listener)
    {
        this.open_details_listener = listener;
        this.credit_notes_list = credit_notes_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_note_single_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ledger_amount.setText("Rs   "+credit_notes_list.get(position).getCredit_amount()+" /-");
        holder.credit_note_date.setText(credit_notes_list.get(position).getCredit_date());
        holder.ledger_narration.setText(credit_notes_list.get(position).getNarration());
        holder.ledger_name.setText(credit_notes_list.get(position).getCredit_ledger_name());
    }

    @Override
    public int getItemCount() {
        return credit_notes_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener
    {
        public TextView ledger_name;
        public TextView ledger_amount;
        public TextView credit_note_date;
        public TextView ledger_narration;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ledger_name = (TextView)itemView.findViewById(R.id.ledger_name);
            ledger_narration = (TextView)itemView.findViewById(R.id.ledger_narration);
            credit_note_date = (TextView)itemView.findViewById(R.id.credit_note_date);
            ledger_amount = (TextView)itemView.findViewById(R.id.total_credit_amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                open_details_listener.onItemClick(v, getAdapterPosition());
        }
    }
}
