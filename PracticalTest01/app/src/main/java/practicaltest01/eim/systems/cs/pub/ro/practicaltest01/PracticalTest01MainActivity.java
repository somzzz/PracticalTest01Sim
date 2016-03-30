package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class PracticalTest01MainActivity extends Activity {

    private ShitReceiver myShit= null;
    private IntentFilter startedServiceIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        EditText leftEditText = (EditText) findViewById(R.id.left_edit_text);
        EditText rightEditText = (EditText) findViewById(R.id.right_edit_text);

        Log.d("WTFWTFTWW", "OnCreate");
        if (savedInstanceState != null) {
            Log.d("WTFWTFTWW", "Saved instance state != null");

            if (savedInstanceState.containsKey("leftCount")) {
                leftEditText.setText(savedInstanceState.getString("leftCount"));
            } else {
                leftEditText.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightCount")) {
                rightEditText.setText(savedInstanceState.getString("rightCount"));
            } else {
                rightEditText.setText(String.valueOf(0));
            }
        } else {
            leftEditText.setText(String.valueOf(0));
            rightEditText.setText(String.valueOf(0));
        }

        Button leftButton = (Button) findViewById(R.id.left_button);
        final Button rightButton = (Button) findViewById(R.id.right_button);
        Button nextApp = (Button) findViewById(R.id.navigate_to_secondary_activity_button);

        nextApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText leftEditText = (EditText) findViewById(R.id.left_edit_text);
                EditText rightEditText = (EditText) findViewById(R.id.right_edit_text);

                Intent intent =  new Intent("practicaltest01.eim.systems.cs.pub.ro.practicaltest01.PracticalTest01SecondaryACtivity");
                intent.putExtra("left", leftEditText.getText().toString());
                intent.putExtra("right", rightEditText.getText().toString());
                startActivityForResult(intent, 42);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText leftEditText = (EditText) findViewById(R.id.left_edit_text);
                int leftNumberOfClicks = Integer.parseInt(leftEditText.getText().toString());

                EditText rightEditText = (EditText) findViewById(R.id.right_edit_text);
                int rightNumberOfClicks = Integer.parseInt(rightEditText.getText().toString());

                leftNumberOfClicks++;
                leftEditText.setText(String.valueOf(leftNumberOfClicks));

                if (leftNumberOfClicks + rightNumberOfClicks > 10) {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    intent.putExtra("left", leftNumberOfClicks);
                    intent.putExtra("right", rightNumberOfClicks);
                    startService(intent);
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText leftEditText = (EditText) findViewById(R.id.left_edit_text);
                int leftNumberOfClicks = Integer.parseInt(leftEditText.getText().toString());


                EditText rightEditText = (EditText) findViewById(R.id.right_edit_text);
                int rightNumberOfClicks = Integer.parseInt(rightEditText.getText().toString());
                rightNumberOfClicks++;
                rightEditText.setText(String.valueOf(rightNumberOfClicks));

                if (leftNumberOfClicks + rightNumberOfClicks > 10) {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    intent.putExtra("left", leftNumberOfClicks);
                    intent.putExtra("right", rightNumberOfClicks);
                  startService(intent);
                }
            }
        });

        myShit = new ShitReceiver();

        // TODO: exercise 7b - create an instance of the IntentFilter
        // with the corresponding actions of the broadcast intents
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction("shit1");
        startedServiceIntentFilter.addAction("shit2");
        startedServiceIntentFilter.addAction("shit3");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 42:
                Toast.makeText(getApplication(), "Contacts Manager Activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("WTFWTFTWW", "OnSave");

        EditText leftEditText = (EditText)findViewById(R.id.left_edit_text);
        EditText rightEditText = (EditText)findViewById(R.id.right_edit_text);

        savedInstanceState.putString("LeftEdit", String.valueOf(leftEditText.getText()));
        savedInstanceState.putString("RightEdit", String.valueOf(rightEditText.getText()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("WTFWTFTWW", "OnRestore");

        EditText leftEditText = (EditText)findViewById(R.id.left_edit_text);
        EditText rightEditText = (EditText)findViewById(R.id.right_edit_text);

        leftEditText.setText(savedInstanceState.getString("LeftEdit"));
        rightEditText.setText(savedInstanceState.getString("RightEdit"));
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("WTFWTFTWW", "OnStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WTFWTFTWW", "OnDestroy");

        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(myShit, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(myShit);

        super.onPause();
    }

}
