package com.example.li.datepickertimerdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTime = (Button) findViewById(R.id.btn_time);
        final TextView showTime = (TextView) findViewById(R.id.show_time);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(MainActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(showTime, 0, false);
            }
        });
    }
}
