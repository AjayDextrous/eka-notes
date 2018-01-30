package io.eka.ekanotes;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ajay on 21/01/18.
 */

public class NoteDialogFragment extends DialogFragment {

    private Context context;
    OnNotesUpdateListener onUpdateListener;
    private BasicNote note = null;

    public static NoteDialogFragment newNote(Context context){
        NoteDialogFragment noteDialogFragment = new NoteDialogFragment();
        if(!(context instanceof OnNotesUpdateListener)){
            try {
                throw new Exception(context.getClass().toString() + " must Implement OnNotesUpdateListener");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            noteDialogFragment.onUpdateListener = (OnNotesUpdateListener) context;
        }
        noteDialogFragment.context = context;
        return noteDialogFragment;
    }

    public static NoteDialogFragment editNote(Context context, BasicNote note){
        NoteDialogFragment noteDialogFragment = new NoteDialogFragment();
        if(!(context instanceof OnNotesUpdateListener)){
            try {
                throw new Exception(context.getClass().toString() + " must Implement OnNotesUpdateListener");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            noteDialogFragment.onUpdateListener = (OnNotesUpdateListener) context;
        }
        noteDialogFragment.context = context;
        noteDialogFragment.note = note;
        return noteDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_note, null);
        View okButton = view.findViewById(R.id.button_ok);
        View cancelButton = view.findViewById(R.id.button_cancel);
        final EditText titleEditText = view.findViewById(R.id.note_title);
        final EditText contentEditText = view.findViewById(R.id.note_content);
        if(note != null){
            titleEditText.setText(note.getTitle());
            contentEditText.setText((CharSequence) note.getContent());
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleEditText.getText().toString().isEmpty()){
                    Toast.makeText(context,"Title cannot be empty",Toast.LENGTH_SHORT);
                    return;
                }else {

                    if(note != null){
                        note.setTitle(titleEditText.getText().toString());
                        note.setContent(contentEditText.getText().toString());
                        onUpdateListener.onUpdateNote(note);
                    }else{
                        BasicNote newNote = new BasicNote();
                        newNote.setTitle(titleEditText.getText().toString());
                        newNote.setContent(contentEditText.getText().toString());
                        onUpdateListener.onAddNote(newNote);
                    }
                    titleEditText.setText("");
                    contentEditText.setText("");
                    dismiss();
                }
            }
        });

        return view;
    }
}
