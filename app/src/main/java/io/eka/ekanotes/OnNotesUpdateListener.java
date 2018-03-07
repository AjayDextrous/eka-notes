package io.eka.ekanotes;

import io.eka.ekanotes.models.BasicNote;

/**
 * Created by ajay on 21/01/18.
 */

public interface OnNotesUpdateListener {

    void onAddNote(BasicNote note);
    void onUpdateNote (BasicNote note);

}
