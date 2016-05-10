package com.vinkas.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 10-5-16.
 */
public class Scheduler {

    private static Scheduler scheduler;
    public static Scheduler getInstance() {
        if(scheduler == null)
            scheduler = new Scheduler();
        return scheduler;
    }

    public Scheduler() {
        alarmManager = (AlarmManager) getAndroidContext().getSystemService(Context.ALARM_SERVICE);
    }

    private AlarmManager alarmManager;

    public Notification.Builder getNotificationBuilder() {
        NotificationManager manager = (NotificationManager) getAndroidContext().getSystemService(Context.NOTIFICATION_SERVICE);
        return new Notification.Builder(getAndroidContext());
    }

    protected AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public Context getAndroidContext() {
        return Helper.getApplication();
    }

    public void schedule(int notificationId, Notification notification, long timestamp, int rtcType) {
        Intent publisherIntent = getPublisherIntent(notificationId, notification);
        PendingIntent noCreate = getPendingIntent(notificationId, publisherIntent, PendingIntent.FLAG_NO_CREATE);
        if (noCreate != null) {
            noCreate.cancel();
            getAlarmManager().cancel(noCreate);
        }
        PendingIntent updateCurrent = getPendingIntent(notificationId, publisherIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        getAlarmManager().set(rtcType, timestamp, updateCurrent);
    }

    protected Intent getPublisherIntent(int notificationId, Notification notification) {
        return Publisher.createInstance(getAndroidContext(), notificationId, notification);
    }

    protected PendingIntent getPendingIntent(int requestCode, Intent publisherIntent, int FLAG) {
        return PendingIntent.getBroadcast(getAndroidContext(), requestCode, publisherIntent, FLAG);
    }

}
