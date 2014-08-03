package com.gmtuca.regexmaze.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by gmtuk on 02/08/2014.
 */
public class CurrentRunActivity extends Activity {

    private ProgressBar runProgressBar;
    private TextView runProgressTexView;
    private Button runButton, abortButton;

    private static final int nRunStages = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_run_layout);

        runButton = (Button) findViewById(R.id.run_button);
        runButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PlayActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        abortButton = (Button) findViewById(R.id.abortButton);
        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateManager.removeAnswersArray(CurrentRunActivity.this);

                reset();
            }
        });

        runProgressBar = (ProgressBar) findViewById(R.id.runProgressBar);
        runProgressTexView = (TextView) findViewById(R.id.runProgressTextView);

        reset();
    }

    public void reset(){
        runProgressBar.setProgress(0);
        runProgressTexView.setText("0 / " + nRunStages);
        runButton.setText("RUN");
        abortButton.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int currentStageIndex = preferences.getInt(StateManager.CURRENT_STAGE_INDEX, 0);
        boolean runHasStarted = preferences.getBoolean(StateManager.RUN_HAS_STARTED, false);

        abortButton.setEnabled(runHasStarted);

        runProgressBar.setProgress(100 * currentStageIndex / nRunStages);
        runProgressTexView.setText(currentStageIndex + " / " + nRunStages);

        runButton.setText((runHasStarted) ? "RESUME" : "RUN");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
