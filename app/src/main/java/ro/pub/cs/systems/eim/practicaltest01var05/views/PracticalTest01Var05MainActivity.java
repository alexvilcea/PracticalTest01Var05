package ro.pub.cs.systems.eim.practicaltest01var05.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01var05.R;
import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;
import ro.pub.cs.systems.eim.practicaltest01var05.service.PracticalTest01Var05Service;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter = new IntentFilter();
    private TextView textView;
    private Button topLeftBtn, topRightBtn, centerBtn, bottomLeftBtn, bottomRightBtn;
    private Button navigateToSecondaryActivityButton;
    private int pressedNo;
    private int serviceStatus;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String text = textView.getText().toString();

            switch(view.getId()) {
                case R.id.top_left:
                    textView.setText(text+"TOP LEFT, ");
                    pressedNo++;
                    break;
                case R.id.top_right:
                    textView.setText(text+"TOP RIGHT, ");
                    pressedNo++;

                    break;
                case R.id.center_btn:
                    textView.setText(text+"CENTER, ");
                    pressedNo++;

                    break;
                case R.id.bottom_left:
                    textView.setText(text+"BOTTOM LEFT, ");
                    pressedNo++;

                    break;
                case R.id.bottom_right:
                    textView.setText(text+"BOTTOM RIGHT, ");
                    pressedNo++;

                    break;

                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);

                    intent.putExtra(Constants.NUMBER_OF_CLICKS, text);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
            Log.d("MyApplication", String.valueOf(pressedNo));


            if (pressedNo > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, text);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);


        topLeftBtn= (Button)findViewById(R.id.top_left);
        topRightBtn= (Button)findViewById(R.id.top_right);
        centerBtn= (Button)findViewById(R.id.center_btn);
        bottomLeftBtn= (Button)findViewById(R.id.bottom_left);
        bottomRightBtn= (Button)findViewById(R.id.bottom_right);

        textView = (TextView)findViewById(R.id.text_view);

        topLeftBtn.setOnClickListener(buttonClickListener);
        topRightBtn.setOnClickListener(buttonClickListener);
        centerBtn.setOnClickListener(buttonClickListener);
        bottomRightBtn.setOnClickListener(buttonClickListener);
        bottomLeftBtn.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

            intentFilter.addAction(Constants.actionTypes);

    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("MyApplication", "onSaveInstanceState");
        savedInstanceState.putString(Constants.PRESSED_NO, String.valueOf(pressedNo));
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Log.d("MyApplication", "onRestoreInstanceState");

        if (savedInstanceState.containsKey(Constants.PRESSED_NO)) {
            pressedNo = Integer.parseInt(savedInstanceState.getString(Constants.PRESSED_NO));
        } else {
            pressedNo = 0;
        }

        Log.d("MyApplication", String.valueOf(pressedNo));
        Toast.makeText(this, String.valueOf(pressedNo), Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}