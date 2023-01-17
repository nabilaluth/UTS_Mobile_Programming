package com.mobileprogramming.utsmobpro1904436.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mobileprogramming.utsmobpro1904436.CustomOnItemClickListener;
import com.mobileprogramming.utsmobpro1904436.NoteAddUpdateActivity;
import com.mobileprogramming.utsmobpro1904436.R;
import com.mobileprogramming.utsmobpro1904436.entity.CoffeeDrinkNote;
import java.util.ArrayList;

public class CoffeeDrinkNoteAdapter extends RecyclerView.Adapter<CoffeeDrinkNoteAdapter.NoteViewHolder>{

    private final ArrayList<CoffeeDrinkNote> listCoffeeDrinkNotes = new ArrayList<>();
    private final Activity activity;

    public CoffeeDrinkNoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<CoffeeDrinkNote> getListCoffeeDrinkNotes() {
        return listCoffeeDrinkNotes;
    }

    public void setListCoffeeDrinkNotes(ArrayList<CoffeeDrinkNote> listCoffeeDrinkNotes) {

        if (listCoffeeDrinkNotes.size() > 0) {
            this.listCoffeeDrinkNotes.clear();
        }
        this.listCoffeeDrinkNotes.addAll(listCoffeeDrinkNotes);

        notifyDataSetChanged();
    }

    public void addItem(CoffeeDrinkNote coffeeDrinkNote) {
        this.listCoffeeDrinkNotes.add(coffeeDrinkNote);
        notifyItemInserted(listCoffeeDrinkNotes.size() - 1);
    }

    public void updateItem(int position, CoffeeDrinkNote coffeeDrinkNote) {
        this.listCoffeeDrinkNotes.set(position, coffeeDrinkNote);
        notifyItemChanged(position, coffeeDrinkNote);
    }

    public void removeItem(int position) {
        this.listCoffeeDrinkNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listCoffeeDrinkNotes.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvTitle.setText(listCoffeeDrinkNotes.get(position).getTitle());
        holder.tvCategory.setText(listCoffeeDrinkNotes.get(position).getCategory());
        holder.tvDesc.setText(listCoffeeDrinkNotes.get(position).getDesc());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position1);
            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listCoffeeDrinkNotes.get(position1));
            activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
        }));
    }

    @Override
    public int getItemCount() {
        return listCoffeeDrinkNotes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvCategory, tvDesc;
        final CardView cvNote;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvCategory = itemView.findViewById(R.id.tv_item_category);
            tvDesc = itemView.findViewById(R.id.tv_item_desc);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }

}
