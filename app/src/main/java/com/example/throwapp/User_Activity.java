package com.example.throwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.throwapp.Database.DATAbase;
import com.example.throwapp.Database.user_Database;

public class User_Activity extends AppCompatActivity {
    public TextView textName;
    public TextView textStuID;
    public TextView textGender;
    public TextView textBirthday;
    public TextView textHeight;
    public TextView textWeight;
    public TextView textHand;

    public Button logout;
    public Button history;
    public Button newTrain;

    String stuID;
    user_Database data;
    private ScannedData selectedDevice;
    public static final String INTENT_KEY = "GET_DEVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent=getIntent();
        stuID=intent.getStringExtra("stuID");
        selectedDevice = (ScannedData) getIntent().getSerializableExtra(INTENT_KEY);

        textName=findViewById(R.id.textName);
        textStuID=findViewById(R.id.textStuID);
        textGender=findViewById(R.id.textGender);
        textBirthday=findViewById(R.id.textBirthday);
        textHeight=findViewById(R.id.textHeight);
        textWeight=findViewById(R.id.textWeight);
        textHand=findViewById(R.id.textHand);
        logout=findViewById(R.id.button_logout);
        history=findViewById(R.id.button_history);
        newTrain=findViewById(R.id.button_newTrain);
        String gender;

        new Thread(() -> {
            data = DATAbase.getInstance(getApplicationContext()).getDataUao().findDataByStuID(stuID);
            textName.setText(data.getName());
            textStuID.setText("學　號："+data.getStuID());
            //gender=data.getGender();
            //判斷有問題
            if(data.getGender()=="Man"){
                textGender.setText("性　別：男");
            }else if(data.getGender()=="Woman"){
                textGender.setText("性　別：女");
            }
            textBirthday.setText("生　日："+data.getBirthday());
            textHeight.setText("身　高："+data.getHeight());
            textWeight.setText("體　重："+data.getWeight());
            if(data.getHand()=="Left"){
                textHand.setText("慣用手：左手");
            }else if(data.getHand()=="Right"){
                textHand.setText("慣用手：右手");
            }

        }).start();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Activity.this.finish();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(User_Activity.this,History_Activity.class);
                i.putExtra("stuID",stuID);
                startActivity(i);
            }
        });
        newTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(User_Activity.this,NewTrain_Activity.class);
                i.putExtra("stuID",stuID);
                i.putExtra("GET_DEVICE",selectedDevice);
                startActivity(i);
            }
        });

    }
}