package com.example.admin.appquanlyquanhecanhan.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThongTinChiTietActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 6/24/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            int notificationID;

            if (MainActivity.ten != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.gift)
                        .setContentTitle("Thông báo sinh nhật")
                        .setContentText("Hôm nay sinh nhật của : " +MainActivity.ten);
                Log.e("name",MainActivity.ten);
                Intent intentResult = new Intent(context, ThongTinChiTietActivity.class);
                intentResult.putExtra("IDN",MainActivity.IDN);
                PendingIntent intentt = PendingIntent.getActivity(context, 0, intentResult, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(intentt);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(uri);
                notificationID = 111;
                NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                manager.notify(notificationID, builder.build());
            }

        }catch (Exception e){

        }

        }

}
