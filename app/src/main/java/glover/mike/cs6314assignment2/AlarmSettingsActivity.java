package glover.mike.cs6314assignment2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmSettingsActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button setAlarmBtn;
    TextView alarmHourTV;
    TextView alarmMinTV;
    ToggleButton toggleButton;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    AlarmManager alarmManager;
    Calendar calendar;

    Intent alarmIntent;
    PendingIntent pendingIntent;

    int alarmHour;
    int alarmMin;

    boolean isSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                110, alarmIntent, 0);

        prefs = getSharedPreferences("Prefs", MODE_PRIVATE);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        setAlarmBtn = (Button) findViewById(R.id.alarmSetBtn);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmHourTV = (TextView) findViewById(R.id.alarmHourTV);
        alarmMinTV = (TextView) findViewById(R.id.alarmMinTV);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        alarmHour = prefs.getInt("alarmHour", 12);
        alarmMin = prefs.getInt("alarmMin", 0);

        timePicker.setHour(alarmHour);
        timePicker.setMinute(alarmMin);

        alarmHourTV.setText(String.valueOf(alarmHour));
        if (alarmMin < 10){
            alarmMinTV.setText("0" + alarmMin);
        } else {
            alarmMinTV.setText(String.valueOf(alarmMin));
        }
        if (prefs.getBoolean("alarmSet", false)){
            toggleButton.setChecked(true);
        }

        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager.cancel(pendingIntent);

                calendar = Calendar.getInstance();

                alarmHour = timePicker.getHour();
                alarmMin = timePicker.getMinute();

                alarmHourTV.setText(String.valueOf(alarmHour));

                if (alarmMin < 10){
                    alarmMinTV.setText("0" + alarmMin);
                } else {
                    alarmMinTV.setText(String.valueOf(alarmMin));
                }

                if ((alarmMin < calendar.get(Calendar.MINUTE) && alarmHour == calendar.get(Calendar.HOUR_OF_DAY))
                        || alarmHour < calendar.get(Calendar.HOUR_OF_DAY)){
                    long curMillis = calendar.getTimeInMillis();
                    calendar.setTimeInMillis(curMillis + (1000 * 60 * 24));
                }

                calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        alarmHour,
                        alarmMin,
                        0
                );

                long alarmTimeInMillis = calendar.getTimeInMillis();

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);

                editor = prefs.edit();
                editor.putInt("alarmHour", alarmHour);
                editor.putInt("alarmMin", alarmMin);
                editor.putBoolean("alarmSet", true);
                editor.putLong("alarmTimeInMillis", alarmTimeInMillis);
                editor.commit();

                isSet = true;

                toggleButton.setChecked(true);
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    if (!isSet){
                        calendar = Calendar.getInstance();

                        long curTimeInMillis = calendar.getTimeInMillis();

                        calendar.set(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                alarmHour,
                                alarmMin,
                                0);

                        long defaultMillis = calendar.getTimeInMillis();

                        long alarmTimeInMillis = prefs.getLong("alarmTimeInMillis", defaultMillis);

                        while (alarmTimeInMillis < curTimeInMillis){
                            alarmTimeInMillis += (1000 * 60 * 24);
                        }

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);

                        editor = prefs.edit();
                        editor.putBoolean("alarmSet", true);
                        editor.putLong("alarmTimeInMillis", alarmTimeInMillis);
                        editor.commit();
                    }
                } else {

                    PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                            111, alarmIntent, 0);

                    alarmManager.cancel(pendingIntent);
                    alarmManager.cancel(snoozePendingIntent);

                    isSet = false;

                    editor = prefs.edit();
                    editor.putBoolean("alarmSet", false);
                    editor.commit();
                }
            }
        });

    }
}
