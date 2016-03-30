package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class MyService extends Service {

    private class  ProcessingThread extends Thread {

        final public static String ACTION_STRING = "ro.pub.cs.systems.eim.lab05.startedservice.string";
        final public static String ACTION_INTEGER = "ro.pub.cs.systems.eim.lab05.startedservice.integer";
        final public static String ACTION_ARRAY_LIST = "ro.pub.cs.systems.eim.lab05.startedservice.arraylist";

        private Context context;
        private int left, right;

        public ProcessingThread(Context context, int left, int right) {
            this.context = context;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {

            while(true){
                sendMessage(1);
                sleep();

            }
        }

        private void sleep() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage(int messageType) {

            Calendar cal = Calendar.getInstance();

            Intent intent = new Intent();

            int val = cal.get(Calendar.SECOND) % 3;
            switch(val) {
                case 1:
                    intent.setAction("shit1");
                    break;
                case 2:
                    intent.setAction("shit2");
                    break;
                case 0:
                    intent.setAction("shit3");
                    break;
            }
            intent.putExtra("cacat", cal.get(Calendar.DATE) + " " + cal.get(Calendar.HOUR) + " " + ((left + right) / 2) + " " + Math.sqrt(left * right));
            context.sendBroadcast(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int left = intent.getIntExtra("left", 0);
        int right = intent.getIntExtra("right", 0);

        ProcessingThread pt = new ProcessingThread(this, left, right);
        pt.start();


        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}