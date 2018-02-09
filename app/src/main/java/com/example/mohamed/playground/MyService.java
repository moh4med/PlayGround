package com.example.mohamed.playground;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service implements MediaPlayer.OnPreparedListener ,MediaPlayer.OnCompletionListener{
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private static final String TAG = "MYSERVICE";
    MediaPlayer mMediaPlayer = null;
    private MediaPlayer mp;
    private static final String ACTION_PAUSE = "com.example.action.Pause";
    private static final String ACTION_STOP = "com.example.action.Stop";
    public MyService(){
        Log.e(TAG, "Constructor");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // android.os.Debug.waitForDebugger();
        mp = MediaPlayer.create(this, R.raw.kaiji);
        Log.e(TAG, "onCreate");
        Intent notificationIntent = new Intent(this, com.example.mohamed.playground.MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification =
                new Notification.Builder(this)
                        .setContentTitle(getText(R.string.notification_title))
                        .setContentText(getText(R.string.notification_message))
                        .setSmallIcon(R.drawable.emoji)
                        .setContentIntent(pendingIntent)
                        .setTicker(getText(R.string.ticker_text))
                        .build();

        startForeground(1337, notification);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroySERVICE");
        super.onDestroy();
        mp.stop();
        mp.release();
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e(TAG,"STOPING SERVICE");
        stopSelf();
    }
    public int onStartCommand(Intent intent, int flags,final int startId) {
        Log.e(TAG, "onStartCommand");
        if(intent==null){
            Log.e(TAG, "Playing again");
            mp.start();
        }else{
            if (intent.getAction().equals(ACTION_PLAY)) {
                Log.e(TAG, "Playing");
                mp.start();
            }else if (intent.getAction().equals(ACTION_PAUSE)){
                Log.e(TAG, "Pause");
                mp.pause();
            }else if (intent.getAction().equals(ACTION_STOP)){
                Log.e(TAG, "stop");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopSelf(startId);
                    }
                },3000);
            }
        }
        return Service.START_STICKY;
    }
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"BINDING");
        return mBinder;
    }

    /** method for clients */
    public int getcurrenttime() {
        return mp.getCurrentPosition();
    }

    /**
     * Called when MediaPlayer is ready
     */
    public void onPrepared(MediaPlayer player) {
    }


}
