package io.eka.ekanotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ajay-5674 on 21/01/18.
 */

public class UIUtils {

    public static AlertDialog getConfirmationDialog(Context context, String title, String message, String positiveConfirmation, String negativeConfirmation, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(negativeConfirmation,null);
        builder.setPositiveButton(positiveConfirmation,onClickListener);
        return builder.create();
    }
}