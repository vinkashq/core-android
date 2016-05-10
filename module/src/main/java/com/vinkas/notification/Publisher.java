package com.vinkas.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Publisher extends BroadcastReceiver {

    protected static Uri getUri() {
        return Uri.parse(URL);
    }

    protected static String getAction() {
        return ACTION;
    }

    public static Intent createInstance(Context context, int notificationId, Notification notification) {
        Intent intent = new Intent(context, Publisher.class);
        intent.setData(getUri());
        intent.setAction(getAction());
        intent.putExtra(KEY_ID, notificationId);
        intent.putExtra(KEY_NOTIFICATION, notification);
        return intent;
    }

    private static final String URL = "publisher://notification.vinkas.com";
    private static final String ACTION = "vinkas.intent.action.NOTIFICATION";
    private static final String KEY_NOTIFICATION = "notification";
    private static final String KEY_ID = "id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(KEY_NOTIFICATION);
        int id = intent.getIntExtra(KEY_ID, 0);
        Notify(context, id, notification);
    }

    public void Notify(Context context, int id, Notification notification) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);
    }

}
