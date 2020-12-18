package glover.mike.cs6314assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    ClockSurfaceView clockSurfaceView = null;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int clockFrameColour;
    int clockMarkColour;
    int clockHandColour;
    boolean clockFrameChoice;
    int clockFrameStyle;
    boolean clockMinMarks;
    int clockHourMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Prefs", MODE_PRIVATE);

        if (!prefs.contains("initialized")){
            editor = prefs.edit();

            editor.putBoolean("initialized", true);

            editor.putInt("clockFrameColour", Color.RED);
            editor.putString("clockFrameColourString", "Red");

            editor.putInt("clockMarkColour", Color.RED);
            editor.putString("clockMarkColourString", "Red");

            editor.putInt("clockHandColour", Color.RED);
            editor.putString("clockHandColourString", "Red");

            editor.putBoolean("clockFrameChoice", true);
            editor.putString("clockFrameChoiceString", "Yes");

            editor.putInt("frameStyle", 0);
            editor.putString("clockFrameStyleString", "Circle");

            editor.putBoolean("minMarks", true);
            editor.putString("clockMinMarksChoiceString", "Yes");

            editor.putInt("hourMarks", 2);
            editor.putString("clockHourMarksChoiceString", "Numerals + Dots");

            editor.commit();
        }

        clockFrameColour = prefs.getInt("clockFrameColour", Color.RED);
        clockFrameChoice = prefs.getBoolean("clockFrameChoice", true);
        clockFrameStyle = prefs.getInt("frameStyle", 0);

        clockMarkColour = prefs.getInt("clockMarkColour", Color.RED);
        clockMinMarks = prefs.getBoolean("minMarks", true);
        clockHourMarks = prefs.getInt("hourMarks", 2);

        clockHandColour = prefs.getInt("clockHandColour", Color.RED);

        clockSurfaceView = new ClockSurfaceView(this, 300, clockFrameColour, clockFrameChoice,
                clockFrameStyle, clockMarkColour, clockMinMarks, clockHourMarks, clockHandColour);

        setContentView(clockSurfaceView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clockFrameColour = prefs.getInt("clockFrameColour", Color.RED);
        clockFrameChoice = prefs.getBoolean("clockFrameChoice", true);
        clockFrameStyle = prefs.getInt("frameStyle", 0);

        clockMarkColour = prefs.getInt("clockMarkColour", Color.RED);
        clockMinMarks = prefs.getBoolean("minMarks", true);
        clockHourMarks = prefs.getInt("hourMarks", 2);

        clockHandColour = prefs.getInt("clockHandColour", Color.RED);

        clockSurfaceView.onResumeSurfaceView(clockFrameColour, clockFrameChoice, clockFrameStyle,
                clockMarkColour, clockMinMarks, clockHourMarks, clockHandColour);
    }

    @Override
    protected void onPause() {
        super.onPause();
        clockSurfaceView.onPauseSurfaceView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPreferences){
            Intent i = new Intent(this, PreferencesActivity.class);
            startActivity(i);
        } else if (id == R.id.menuAlarms){
            Intent i = new Intent(this, AlarmSettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
