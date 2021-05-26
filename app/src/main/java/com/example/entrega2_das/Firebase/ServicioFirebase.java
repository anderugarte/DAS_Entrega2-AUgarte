package com.example.entrega2_das.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.entrega2_das.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {

    public ServicioFirebase() { }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "Canal1");
            elBuilder.setSmallIcon(R.drawable.logo1).setContentTitle("Where Is That?")
                    .setVibrate(new long[]{0, 100})
                    .setAutoCancel(true);
            elBuilder.setContentText("¡Hay nuevas publicaciones por descubrir!");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("Canal1", "CanalMain",
                        NotificationManager.IMPORTANCE_DEFAULT);
                elCanal.setDescription("Canal Principal");
                elCanal.setVibrationPattern(new long[]{0, 100});
                elCanal.enableVibration(true);
                elManager.createNotificationChannel(elCanal);
            }

            elManager.notify(2, elBuilder.build());
        }
    }
}
