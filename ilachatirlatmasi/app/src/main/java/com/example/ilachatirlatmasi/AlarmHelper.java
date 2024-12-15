package com.example.ilachatirlatmasi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmHelper {
    public static void setAlarm(Context context, Calendar calendar, String ilacIsmi, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ilacIsmi", ilacIsmi);

        // FLAG_IMMUTABLE eklendi
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmHelper", "Alarm kuruldu: " + calendar.getTime());
        } else {
            Log.d("AlarmHelper", "AlarmManager bulunamadı.");
        }
    }
}
