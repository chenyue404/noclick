package com.chenyue404.noclick;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockService extends Service {
    private Intent lockIntent = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        lockIntent = new Intent(LockService.this, MainActivity.class);
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /*注册广播*/
        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        LockService.this.registerReceiver(ScreenBroadcastReceiver, mScreenOffFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LockService.this.unregisterReceiver(ScreenBroadcastReceiver);
        //在此重新启动
        startService(new Intent(LockService.this, LockService.class));
    }

    private BroadcastReceiver ScreenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("chenyue", intent.getAction());
            String action = intent.getAction();
            context.startActivity(lockIntent);
        }
    };


}
