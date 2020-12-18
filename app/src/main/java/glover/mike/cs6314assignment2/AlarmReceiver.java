package glover.mike.cs6314assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Mike Glover on 03/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context.getApplicationContext(), ActiveAlarmActivity.class);
        context.startActivity(i);
    }
}
