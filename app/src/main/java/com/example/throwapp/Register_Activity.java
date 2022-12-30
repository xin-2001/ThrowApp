package com.example.throwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.throwapp.Database.DATAbase;
import com.example.throwapp.Database.user_Database;

import java.io.IOException;

public class Register_Activity extends AppCompatActivity {
    public Button buttonBack;
    public Button buttonCheck;

    public EditText textName;
    public EditText textStuID;
    public RadioGroup radioGender;
    public RadioGroup radioHand;
    public RadioButton radioMan;
    public RadioButton radioWoman;
    public RadioButton radioRight;
    public RadioButton radioLeft;
    public EditText textBirthday;
    public EditText textHeight;
    public EditText textWeight;
    public TextView errorName;
    public TextView errorStuID;
    public TextView errorBirthday;
    public TextView errorHeight;
    public TextView errorWeight;

    public TextView total;

    user_Database data;

    int register_count=0;

    private ScannedData selectedDevice;
    public static final String INTENT_KEY = "GET_DEVICE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        selectedDevice = (ScannedData) getIntent().getSerializableExtra(INTENT_KEY);

        buttonBack=findViewById(R.id.button_back);
        buttonCheck=findViewById(R.id.buttonCheck);

        textName=findViewById(R.id.textName);
        textStuID=findViewById(R.id.textStuID);
        radioGender=findViewById(R.id.radioGender);
        radioHand=findViewById(R.id.radioHand);
        radioMan=findViewById(R.id.radioMan);
        radioWoman=findViewById(R.id.radioWoman);
        radioRight=findViewById(R.id.radioRight);
        radioLeft=findViewById(R.id.radioLeft);
        textBirthday=findViewById(R.id.editTextBirthday);
        textHeight=findViewById(R.id.editTextHeight);
        textWeight=findViewById(R.id.editTextWeight);
        errorName=findViewById(R.id.errorName);
        errorStuID=findViewById(R.id.errorStuID);
        errorBirthday=findViewById(R.id.errorBirthday);
        errorHeight=findViewById(R.id.errorHeight);
        errorWeight=findViewById(R.id.errorWeight);

        total=findViewById(R.id.total);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register_Activity.this.finish();
                /*new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("Exception when sendKeyDownUpSync",
                                    e.toString());
                        }
                    }
                }.start();*/
            }
        });
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //驗證帳號是否重複 V
                //確認每一欄都有填寫 V
                //性別用:man、woman V
                //慣用手用:left、right V
                //完成驗證push到資料庫
                register_count=0;
                String name;
                String stuID;
                String gender;
                String hand;
                String birthday;
                String height;
                String weight;



                //姓名驗證
                name=textName.getText().toString();
                if (name.matches("") ) {
                    errorName.setVisibility(View.VISIBLE);
                    register_count++;
                }else {
                    errorName.setVisibility(View.INVISIBLE);
                }
                //學號驗證
                stuID=textStuID.getText().toString();
                String strData="error";
                if(stuID.matches("")){
                    errorStuID.setVisibility(View.VISIBLE);
                    errorStuID.setText("請輸入學號");
                    register_count++;
                }else{
                    errorStuID.setVisibility(View.INVISIBLE);
                    total.setText("");
                    new Thread(() -> {
                        data = DATAbase.getInstance(getApplicationContext()).getDataUao().findDataByStuID(stuID);
                    }).start();
                    try{
                        total.setText(data.getStuID());
                    } catch (Exception e) {
                        e.printStackTrace();
                        total.setText("null");
                    }
                    //errorStuID.setVisibility(View.VISIBLE);
                    //errorStuID.append(data.toString());
                    total.setVisibility(View.VISIBLE);
                    //strData=total.getText().toString();
                    if(total.getText()!="null"){
                        //驗證會delay
                        errorStuID.setVisibility(View.VISIBLE);
                        errorStuID.setText("此學號已註冊");
                        register_count++;
                    }else{

                    }
                }
                //性別
                gender=radioLeft.toString();
                if(radioGender.getCheckedRadioButtonId()==R.id.radioMan){
                    gender="Man";
                }else if(radioGender.getCheckedRadioButtonId()==R.id.radioWoman){
                    gender="Woman";
                }
                //total.append(gender);
                //慣用手
                hand=radioRight.toString();
                if(radioHand.getCheckedRadioButtonId()==R.id.radioLeft){
                    hand="Left";
                }else if(radioHand.getCheckedRadioButtonId()==R.id.radioRight){
                    hand="Right";
                }
                //total.append(hand);
                //生日驗證
                birthday=textBirthday.getText().toString();
                if (birthday.matches("")) {
                    errorBirthday.setVisibility(View.VISIBLE);
                    register_count++;
                }else {
                    errorBirthday.setVisibility(View.INVISIBLE);
                }
                //身高驗證
                height=textHeight.getText().toString();
                if (height.matches("")) {
                    errorHeight.setVisibility(View.VISIBLE);
                    register_count++;
                }else {
                    errorHeight.setVisibility(View.INVISIBLE);
                }
                //體重驗證
                weight=textWeight.getText().toString();
                if (weight.matches("")) {
                    errorWeight.setVisibility(View.VISIBLE);
                    register_count++;
                }else {
                    errorWeight.setVisibility(View.INVISIBLE);
                }

                //若資料沒問題push到資料庫
                if(register_count!=0){
                    //total.setText(Integer.toString(register_count));

                }else{
                    //push
                    //total.setText(name+'\n'+stuID+'\n'+gender+'\n'+hand+'\n'+birthday+'\n'+height+'\n'+weight);
                    String finalHand = hand;
                    String finalGender = gender;
                    new Thread(() -> {
                        user_Database data2 = new user_Database(name, stuID, finalGender, birthday, height, weight, finalHand,"");/*遷移後新增*/
                        DATAbase.getInstance(getApplicationContext()).getDataUao().insertData(data2);

                    }).start();
                    Toast.makeText(getApplicationContext(), "已更新資訊！", Toast.LENGTH_LONG).show();
                    //進入使用者資料頁面
                    Intent i =new Intent(Register_Activity.this,User_Activity.class);
                    i.putExtra("stuID",stuID);
                    i.putExtra("GET_DEVICE",selectedDevice);
                    startActivity(i);
                    Register_Activity.this.finish();
                }


            }
        });


    }
}