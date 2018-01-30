package io.eka.ekanotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;


public class HomeScreenActivity extends EkaActivity implements OnNotesUpdateListener{

    List<BasicNote> notesArray;
    ListView notesList;
    FloatingActionButton fab;
    int currentNotePos = -1;
    private Box<BasicNote> notesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesBox = ((EkaApplication) getApplication()).getBoxStore().boxFor(BasicNote.class);
//        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.init_prefs_file_key), Context.MODE_PRIVATE);
//        boolean welcomeNoteAdded = sharedPref.getBoolean(getString(R.string.sharedprefs_welcome_note_added),false);
//        if(!welcomeNoteAdded){
//            BasicNote sampleNote = new BasicNote();
//            sampleNote.setTitle(getString(R.string.global_welcome_title));
//            sampleNote.setContent(getString(R.string.global_welcome_help_text));
//            notesBox.put(sampleNote);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putBoolean(getString(R.string.sharedprefs_welcome_note_added),true);
//            editor.apply();
//        }

        final NoteDialogFragment newNoteDialogFragment = NoteDialogFragment.newNote(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNoteDialogFragment.show(getFragmentManager().beginTransaction(), "DialogFragment");
            }
        });

        notesList = (ListView) findViewById(R.id.notes_list_view);
        notesArray = new ArrayList<>();
        notesArray = notesBox.getAll();
        //notesArray.add(sampleNote);

        notesList.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return notesArray.size();
            }

            @Override
            public BasicNote getItem(int i) {
                return notesArray.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.basic_list_item,null,false);
                ((TextView)view.findViewById(R.id.note_title)).setText(getItem(i).getTitle());
                ((TextView)view.findViewById(R.id.note_content)).setText((String)getItem(i).getContent());
                return view;
            }
        });
        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        onDeleteNote(notesArray.get(i));
                        ((BaseAdapter)notesList.getAdapter()).notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                };
                UIUtils.getConfirmationDialog(HomeScreenActivity.this,"","are you sure you want to delete this note?",getString(R.string.ui_ok),getString(R.string.ui_cancel),onClickListener).show();
                return true;
            }
        });
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentNotePos = i;
                NoteDialogFragment editNoteDialogFragment = NoteDialogFragment.editNote(HomeScreenActivity.this,notesArray.get(i));
                editNoteDialogFragment.show(getFragmentManager().beginTransaction(), "DialogFragment");
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddNote(BasicNote note) {
        notesArray.add(note);
        notesBox.put(note);
        ((BaseAdapter)notesList.getAdapter()).notifyDataSetChanged();
        Snackbar.make(fab, "New Note Added", Snackbar.LENGTH_LONG)
                .setAction("Edit", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    @Override
    public void onUpdateNote(BasicNote note) {
        notesArray.set(currentNotePos,note);
        notesBox.put(note);
        ((BaseAdapter)notesList.getAdapter()).notifyDataSetChanged();
    }

    private void onDeleteNote(BasicNote note) {
        notesArray.remove(note);
        notesBox.remove(note);
        ((BaseAdapter)notesList.getAdapter()).notifyDataSetChanged();
        Snackbar.make(fab, "Note Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }
}
