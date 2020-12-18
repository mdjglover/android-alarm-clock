package glover.mike.cs6314assignment2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ActiveAlarmActivity extends AppCompatActivity {
    Button stopAlarmBtn;
    Button snoozeBtn;

    TextView alarmHourTV;
    TextView alarmMinTV;

    Calendar calendar;

    int alarmMin;
    int alarmHour;

    boolean snoozeSet;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        prefs = getSharedPreferences("Prefs", MODE_PRIVATE);

        stopAlarmBtn = (Button) findViewById(R.id.activeAlarmStopBtn);
        snoozeBtn = (Button) findViewById(R.id.activeAlarmSnoozeBtn);
        alarmHourTV = (TextView) findViewById(R.id.activeAlarmHourTV);
        alarmMinTV = (TextView) findViewById(R.id.activeAlarmMinTV);

        if (prefs.getBoolean("snoozeSet", false)){
            calendar = Calendar.getInstance();
            alarmHour = calendar.get(Calendar.HOUR_OF_DAY);
            alarmMin = calendar.get(Calendar.MINUTE);
        } else {
            alarmHour = prefs.getInt("alarmHour", 12);
            alarmMin = prefs.getInt("alarmMin", 0);
        }

        alarmHourTV.setText(String.valueOf(alarmHour));
        if (alarmMin < 10){
            alarmMinTV.setText("0" + alarmMin);
        } else {
            alarmMinTV.setText(String.valueOf(alarmMin));
        }

        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();

        stopAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();

                Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        110, alarmIntent, 0);

                calendar = Calendar.getInstance();

                long alarmTimeInMillis = prefs.getLong("alarmTimeInMillis", calendar.getTimeInMillis())
                        + (1000 * 60 * 24);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);

                editor = prefs.edit();
                editor.putBoolean("snoozeSet", false);
                editor.putLong("alarmTimeInMillis", alarmTimeInMillis);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();

                Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        111, alarmIntent, 0);

                calendar = Calendar.getInstance();

                long alarmTimeInMillis = calendar.getTimeInMillis() + (1000 * 60);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);

                editor = prefs.edit();
                editor.putBoolean("snoozeSet", true);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
