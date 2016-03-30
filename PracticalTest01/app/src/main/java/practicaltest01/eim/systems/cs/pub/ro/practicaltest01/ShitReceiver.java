package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.TextView;

public class ShitReceiver extends BroadcastReceiver {

    public ShitReceiver() {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = "shit";

        Log.d("shitLogger", intent.getStringExtra("cacat"));
    }

}
