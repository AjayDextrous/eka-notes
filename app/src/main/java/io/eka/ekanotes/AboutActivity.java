package io.eka.ekanotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ajay on 26/01/18.
 */

public class AboutActivity extends EkaActivity {

    private static final String TAG = "AboutActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView myText = findViewById(R.id.app_title);
        Log.d(TAG, myText.getText().toString());
        setSupportActionBar(toolbar);
    }
}
