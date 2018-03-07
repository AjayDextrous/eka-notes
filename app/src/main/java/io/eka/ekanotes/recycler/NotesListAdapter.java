package io.eka.ekanotes.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.eka.ekanotes.R;
import io.eka.ekanotes.models.BasicNote;


/**
 * Created by ajay on 24/02/18.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListViewHolder> {

    private final Context context;
    private final List<BasicNote> notesArray;
    private OnItemClickListener listener;

    public NotesListAdapter(Context context, List<BasicNote> notesArray) {
        this.context = context;
        this.notesArray = notesArray;
    }

    @Override
    public NotesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final NotesListViewHolder holder = new NotesListViewHolder(LayoutInflater.from(context).inflate(R.layout.basic_list_item, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return listener != null && listener.onItemLongClick(holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NotesListViewHolder holder, int position) {
        holder.title.setText(notesArray.get(position).getTitle());
        holder.content.setText(notesArray.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(NotesListViewHolder holder);

        boolean onItemLongClick(NotesListViewHolder holder);
    }
}
