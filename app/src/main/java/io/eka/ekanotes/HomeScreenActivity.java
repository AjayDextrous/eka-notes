package io.eka.ekanotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.eka.ekanotes.models.BasicNote;
import io.eka.ekanotes.recycler.ItemOffsetDecoration;
import io.eka.ekanotes.recycler.NotesCardAdapter;
import io.eka.ekanotes.recycler.NotesListViewHolder;
import io.eka.ekanotes.utils.ViewUtils;
import io.objectbox.Box;


public class HomeScreenActivity extends EkaActivity implements OnNotesUpdateListener {

    List<BasicNote> notesArray;
    //    ListView notesList;
    RecyclerView notesList;
    RecyclerView.Adapter adapter;
    FloatingActionButton fab;
    int currentNotePos = -1;
    private Box<BasicNote> notesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesBox = ((EkaApplication) getApplication()).getBoxStore().boxFor(BasicNote.class);
        final NoteDialogFragment newNoteDialogFragment = NoteDialogFragment.newNote(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNoteDialogFragment.show(getFragmentManager().beginTransaction(), "DialogFragment");
            }
        });

//        notesList = (ListView) findViewById(R.id.notes_list_view);
        notesList = findViewById(R.id.notes_recycler);
        notesArray = new ArrayList<>();
        notesArray = notesBox.getAll();
        //notesArray.add(sampleNote);

//        adapter = new NotesListAdapter(this,notesArray);
        adapter = new NotesCardAdapter(this, notesArray);
        ((NotesCardAdapter) adapter).setOnItemClickListener(new NotesCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotesListViewHolder holder) {
                currentNotePos = holder.getAdapterPosition();
                NoteDialogFragment editNoteDialogFragment = NoteDialogFragment.editNote(HomeScreenActivity.this, notesArray.get(holder.getAdapterPosition()));
                editNoteDialogFragment.show(getFragmentManager().beginTransaction(), "DialogFragment");
            }

            @Override
            public boolean onItemLongClick(final NotesListViewHolder holder) {

                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        onDeleteNote(notesArray.get(holder.getAdapterPosition()));
                        (notesList.getAdapter()).notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                };
                ViewUtils.getConfirmationDialog(HomeScreenActivity.this, "", "are you sure you want to delete this note?", getString(R.string.ui_ok), getString(R.string.ui_cancel), onClickListener).show();
                return true;
            }
        });
        notesList.setAdapter(adapter);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//        ((LinearLayoutManager)manager).setOrientation(LinearLayoutManager.VERTICAL);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesList.getContext(),
//                ((LinearLayoutManager)manager).getOrientation());
//        notesList.setLayoutManager(manager);
//        notesList.addItemDecoration(dividerItemDecoration);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        RecyclerView.ItemDecoration decorator = new ItemOffsetDecoration(this, R.dimen.cardview_offset);
        notesList.setLayoutManager(manager);
        notesList.addItemDecoration(decorator);


         /*notesList.setAdapter(new BaseAdapter() {

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
                view = getLayoutInflater().inflate(R.layout.basic_list_item, null, false);
                ((TextView) view.findViewById(R.id.note_title)).setText(getItem(i).getTitle());
                ((TextView) view.findViewById(R.id.note_content)).setText((String) getItem(i).getContent());
                return view;
            }
        });
        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        onDeleteNote(notesArray.get(i));
                        ((BaseAdapter) notesList.getAdapter()).notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                };
                ViewUtils.getConfirmationDialog(HomeScreenActivity.this, "", "are you sure you want to delete this note?", getString(R.string.ui_ok), getString(R.string.ui_cancel), onClickListener).show();
                return true;
            }
        });
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentNotePos = i;
                NoteDialogFragment editNoteDialogFragment = NoteDialogFragment.editNote(HomeScreenActivity.this, notesArray.get(i));
                editNoteDialogFragment.show(getFragmentManager().beginTransaction(), "DialogFragment");
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddNote(BasicNote note) {
        notesArray.add(note);
        notesBox.put(note);
        (notesList.getAdapter()).notifyDataSetChanged();
        Snackbar.make(fab, "New Note Added", Snackbar.LENGTH_LONG)
                .setAction("Edit", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    @Override
    public void onUpdateNote(BasicNote note) {
        notesArray.set(currentNotePos, note);
        notesBox.put(note);
        (notesList.getAdapter()).notifyDataSetChanged();
    }

    private void onDeleteNote(BasicNote note) {
        notesArray.remove(note);
        notesBox.remove(note);
        (notesList.getAdapter()).notifyDataSetChanged();
        Snackbar.make(fab, "Note Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }
}
