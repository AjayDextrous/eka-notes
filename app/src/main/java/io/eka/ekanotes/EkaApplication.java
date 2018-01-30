package io.eka.ekanotes;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by ajay on 26/01/18.
 */

public class EkaApplication extends Application {

    public static final String TAG = "ObjectBoxExample";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(EkaApplication.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }

        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");

        Box<BasicNote> notesBox = getBoxStore().boxFor(BasicNote.class);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.init_prefs_file_key), Context.MODE_PRIVATE);
        boolean welcomeNoteAdded = sharedPref.getBoolean(getString(R.string.sharedprefs_welcome_note_added),false);
        if(!welcomeNoteAdded){
            BasicNote sampleNote = new BasicNote();
            sampleNote.setTitle(getString(R.string.global_welcome_title));
            sampleNote.setContent(getString(R.string.global_welcome_help_text));
            notesBox.put(sampleNote);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.sharedprefs_welcome_note_added),true);
            editor.apply();
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
