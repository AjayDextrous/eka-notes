package io.eka.ekanotes.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.eka.ekanotes.R;

/**
 * Created by ajay-5674 on 24/02/18.
 */

public class NotesListViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView content;

    //public int position;

    public NotesListViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        content = itemView.findViewById(R.id.note_content);
    }
}
