package com.example.ilachatirlatmasi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.PendingIntent;

public class AlarmReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String ilacIsmi = intent.getStringExtra("ilacIsmi");

        // Bildirim oluşturma
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "ILAC_HATIRLATICI";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "İlaç Hatırlatıcı Kanalı",
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // "Alarmı Kapat" butonunu eklemek için bir Intent oluştur
        Intent stopAlarmIntent = new Intent(context, StopAlarmReceiver.class);
        stopAlarmIntent.putExtra("notificationId", 1);
        PendingIntent stopAlarmPendingIntent = PendingIntent.getBroadcast(
                context, 0, stopAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Bildirime kapatma butonu ekle
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("İlaç Hatırlatma")
                .setContentText("Şimdi " + ilacIsmi + " almanız gerekiyor!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_close, "Alarmı Kapat", stopAlarmPendingIntent); // Kapatma butonu

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }

        // Ses çalma
        try {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmSound == null) {
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            mediaPlayer = MediaPlayer.create(context, alarmSound);
            if (mediaPlayer != null) {
                mediaPlayer.start();

                // Alarmı otomatik olarak durdurmak için 30 saniyelik bir süre ekleyelim
                mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.release());
                new Handler().postDelayed(() -> {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null; // Kaynağı serbest bırak
                    }
                }, 30000); // 30 saniye
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopAlarm() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null; // Kaynağı serbest bırak
        }
    }
}
