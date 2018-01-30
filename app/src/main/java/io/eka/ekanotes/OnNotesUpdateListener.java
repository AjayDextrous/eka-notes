package io.eka.ekanotes;

/**
 * Created by ajay-5674 on 21/01/18.
 */

public interface OnNotesUpdateListener {

    void onAddNote(BasicNote note);
    void onUpdateNote (BasicNote note);

}
