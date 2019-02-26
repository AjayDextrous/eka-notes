package io.eka.ekanotes.recycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.eka.ekanotes.HomeScreenActivity;
import io.eka.ekanotes.R;
import io.eka.ekanotes.models.BasicNote;

public class NotesCardAdapter extends RecyclerView.Adapter {
    private final Activity activity;
    private final List<BasicNote> notesArray;
    private OnItemClickListener listener;

    public NotesCardAdapter(Activity activity, List<BasicNote> notesArray) {
        this.activity = activity;
        this.notesArray = notesArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = activity.getLayoutInflater().inflate(R.layout.basic_grid_note_item,parent,false);
        return new NotesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((NotesListViewHolder) holder).title.setText(notesArray.get(position).getTitle());
        ((NotesListViewHolder) holder).content.setText(notesArray.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick((NotesListViewHolder) holder);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return listener.onItemLongClick((NotesListViewHolder) holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(NotesListViewHolder holder);
        boolean onItemLongClick(NotesListViewHolder holder);
    }
}
