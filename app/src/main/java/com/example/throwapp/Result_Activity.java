package com.example.throwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Result_Activity extends AppCompatActivity {
    public Button buttonLogout;
    public Button buttonBackHome;

    public String stuID,max,valueAll,min,avg,time,count;
    public TextView Text_count,Text_max,Text_avg,Text_min,Text_time;
    private ScannedData selectedDevice;
    public static final String INTENT_KEY = "GET_DEVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        selectedDevice = (ScannedData) getIntent().getSerializableExtra(INTENT_KEY);

        buttonLogout=findViewById(R.id.button_logout);
        buttonBackHome=findViewById(R.id.button_backHome);

        Intent intent=getIntent();
        stuID=intent.getStringExtra("stuID");
        max=intent.getStringExtra("max");
        min=intent.getStringExtra("min");
        avg=intent.getStringExtra("avg");
        count=intent.getStringExtra("count");
        valueAll=intent.getStringExtra("valueAll");  //製圖
        time=intent.getStringExtra("time");

        Text_count=findViewById(R.id.count_text);
        Text_max=findViewById(R.id.max_text);
        Text_avg=findViewById(R.id.avg_text);
        Text_min=findViewById(R.id.min_text);
        Text_time=findViewById(R.id.time_text);

        Text_count.setText("次數："+count+"次");
        Text_max.setText(max);
        Text_min.setText(min);
        Text_avg.setText(avg);
        Text_time.setText(time);



        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Result_Activity.this,Login_Activity.class);
                startActivity(i);
                Result_Activity.this.finish();
            }
        });
        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Result_Activity.this,User_Activity.class);
                i.putExtra("stuID",stuID);
                i.putExtra("GET_DEVICE",selectedDevice);
                startActivity(i);
                Result_Activity.this.finish();
            }
        });

    }
}