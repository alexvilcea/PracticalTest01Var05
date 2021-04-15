package ro.pub.cs.systems.eim.practicaltest01var05.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.pub.cs.systems.eim.practicaltest01var05.R;
import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {


    private TextView textViewSecond;
    private Button verifyButton, cancelButton;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.verify_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        textViewSecond = (TextView)findViewById(R.id.text_view_second);
        Intent intent = getIntent();


        if (intent != null && intent.getExtras().containsKey(Constants.NUMBER_OF_CLICKS)) {
            String textContent = intent.getStringExtra(Constants.NUMBER_OF_CLICKS);
            textViewSecond.setText(textContent);
        }

        verifyButton = (Button)findViewById(R.id.verify_button);
        verifyButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}