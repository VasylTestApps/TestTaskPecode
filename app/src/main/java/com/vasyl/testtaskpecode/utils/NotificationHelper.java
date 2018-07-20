package com.vasyl.testtaskpecode.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.vasyl.testtaskpecode.R;
import com.vasyl.testtaskpecode.views.MainActivity;

import java.util.List;
import java.util.Random;

public class NotificationHelper {

    public static void createNotification(Context context, int id, int fragmentId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(context.getString(R.string.fragment_id), fragmentId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Math.abs(new Random().nextInt()), intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(context.getString(R.string.not_title))
                .setContentText("Notification " + fragmentId)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, mBuilder.build());
    }

    public static void deleteNotification(Context context, List<Integer> idList) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        for (int id : idList) {
            notificationManagerCompat.cancel(id);
        }
    }
}
