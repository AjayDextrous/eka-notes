package io.eka.ekanotes.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ajay on 21/01/18.
 */

public class ViewUtils {

    public static AlertDialog getConfirmationDialog(Context context, String title, String message, String positiveConfirmation, String negativeConfirmation, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(negativeConfirmation, null);
        builder.setPositiveButton(positiveConfirmation, onClickListener);
        return builder.create();
    }
}