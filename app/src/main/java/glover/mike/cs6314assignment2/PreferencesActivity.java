package glover.mike.cs6314assignment2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PreferencesActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Spinner frameColourSpinner;
    Spinner markColourSpinner;
    Spinner handColourSpinner;
    Spinner frameSpinner;
    Spinner frameStyleSpinner;
    Spinner minMarkSpinner;
    Spinner hourMarkSpinner;

    String[] clockFrameColourStrings = {"White", "Gray", "Red", "Blue", "Green", "Yellow", "Magenta", "Cyan"};
    int[] clockFrameColours = {Color.WHITE, Color.GRAY, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN};
    String curClockFrameColourString;

    String[] clockHandColourStrings = {"White", "Gray", "Red", "Blue", "Green", "Yellow", "Magenta", "Cyan"};
    int[] clockHandColours = {Color.WHITE, Color.GRAY, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN};
    String curClockHandColourString;

    String[] clockMarkColourStrings = {"White", "Gray", "Red", "Blue", "Green", "Yellow", "Magenta", "Cyan"};
    int[] clockMarkColours = {Color.WHITE, Color.GRAY, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN};
    String curClockMarkColourString;

    String[] clockFrameChoiceStrings = {"Yes", "No"};
    boolean[] clockFrameChoices = {true, false};
    String curClockFrameChoiceString;

    String[] clockFrameStyleStrings = {"Circle", "Square"};
    int[] clockFrameStyles = {0, 1};
    String curClockFrameStyleString;

    String[] minMarksChoiceStrings = {"Yes", "No"};
    boolean[] minMarksChoices = {true, false};
    String curMinMarksChoiceString;

    String[] hourMarksChoiceStrings = {"Numerals", "Dots", "Numerals + Dots"};
    int[] hourMarksChoices = {0, 1, 2};
    String curHourMarksChoiceString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        curClockFrameColourString = prefs.getString("clockFrameColourString", "Red");
        curClockMarkColourString = prefs.getString("clockMarkColourString", "Red");
        curClockHandColourString = prefs.getString("clockHandColourString", "Red");
        curClockFrameChoiceString = prefs.getString("clockFrameChoiceString", "Yes");
        curClockFrameStyleString = prefs.getString("clockFrameStyleString", "Circle");
        curMinMarksChoiceString = prefs.getString("clockMinMarksChoiceString", "Yes");
        curHourMarksChoiceString = prefs.getString("clockHourMarksChoiceString", "Numerals + Dots");

        frameColourSpinner = findViewById(R.id.frameColourSpinner);
        markColourSpinner = findViewById(R.id.markColourSpinner);
        handColourSpinner = findViewById(R.id.handColourSpinner);
        frameSpinner = findViewById(R.id.frameSpinner);
        frameStyleSpinner = findViewById(R.id.frameStyleSpinner);
        minMarkSpinner = findViewById(R.id.minMarksSpinner);
        hourMarkSpinner = findViewById(R.id.hourMarksSpinner);

        ArrayAdapter<String> frameColourArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clockFrameColourStrings);
        frameColourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> markColourArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clockMarkColourStrings);
        markColourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> handColourArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clockHandColourStrings);
        handColourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> frameChoiceArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clockFrameChoiceStrings);
        frameChoiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> frameStyleArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clockFrameStyleStrings);
        frameChoiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> minMarksArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, minMarksChoiceStrings);
        frameChoiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> hourMarksArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, hourMarksChoiceStrings);
        frameChoiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int curFrameColourPos = frameColourArrayAdapter.getPosition(curClockFrameColourString);
        int curMarkColourPos = markColourArrayAdapter.getPosition(curClockMarkColourString);
        int curHandColourPos = handColourArrayAdapter.getPosition(curClockHandColourString);
        int curFrameChoicePos = frameChoiceArrayAdapter.getPosition(curClockFrameChoiceString);
        int curFrameStylePos = frameStyleArrayAdapter.getPosition(curClockFrameStyleString);
        int curMinMarksPos = minMarksArrayAdapter.getPosition(curMinMarksChoiceString);
        int curHourMarksPos = hourMarksArrayAdapter.getPosition(curHourMarksChoiceString);

        frameColourSpinner.setAdapter(frameColourArrayAdapter);
        frameColourSpinner.setSelection(curFrameColourPos);
        frameColourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockFrameColourString", clockFrameColourStrings[i]);
                editor.putInt("clockFrameColour", clockFrameColours[i]);
                editor.commit();
                frameColourSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        markColourSpinner.setAdapter(markColourArrayAdapter);
        markColourSpinner.setSelection(curMarkColourPos);
        markColourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockMarkColourString", clockMarkColourStrings[i]);
                editor.putInt("clockMarkColour", clockMarkColours[i]);
                editor.commit();
                markColourSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        handColourSpinner.setAdapter(handColourArrayAdapter);
        handColourSpinner.setSelection(curHandColourPos);
        handColourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockHandColourString", clockHandColourStrings[i]);
                editor.putInt("clockHandColour", clockHandColours[i]);
                editor.commit();
                handColourSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frameSpinner.setAdapter(frameChoiceArrayAdapter);
        frameSpinner.setSelection(curFrameChoicePos);
        frameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockFrameChoiceString", clockFrameChoiceStrings[i]);
                editor.putBoolean("clockFrameChoice", clockFrameChoices[i]);
                Log.i("New Clock Frame Choice", "New Frame Choice: " + clockFrameChoices[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frameStyleSpinner.setAdapter(frameStyleArrayAdapter);
        frameStyleSpinner.setSelection(curFrameStylePos);
        frameStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockFrameStyleString", clockFrameStyleStrings[i]);
                editor.putInt("frameStyle", clockFrameStyles[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        minMarkSpinner.setAdapter(minMarksArrayAdapter);
        minMarkSpinner.setSelection(curMinMarksPos);
        minMarkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockMinMarksChoiceString", minMarksChoiceStrings[i]);
                editor.putBoolean("minMarks", minMarksChoices[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hourMarkSpinner.setAdapter(hourMarksArrayAdapter);
        hourMarkSpinner.setSelection(curHourMarksPos);
        hourMarkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("clockHourMarksChoiceString", hourMarksChoiceStrings[i]);
                editor.putInt("hourMarks", hourMarksChoices[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
