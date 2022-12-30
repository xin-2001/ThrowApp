package com.example.throwapp;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewTrain_Activity extends AppCompatActivity {
    public Button buttonEnd;
    public String stuID;
    private BluetoothLeService mBluetoothLeService;
    private ExpandableListAdapter expandableListAdapter;
    public static final String TAG = "My";
    private TextView Text_now,Text_max,Text_avg,Text_min;
    private boolean isLedOn = false;
    String count,valueAll="",max,min,avg;
    String AVGG,Maxx,Minn,time;
    float MAX,MIN,COUNT=0,SUM;



    private ScannedData selectedDevice;
    public static final String INTENT_KEY = "GET_DEVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_train);
        selectedDevice = (ScannedData) getIntent().getSerializableExtra(INTENT_KEY);
        initBLE();
        initUI();

        buttonEnd=findViewById(R.id.button_end);
        Intent intent=getIntent();
        stuID=intent.getStringExtra("stuID");

        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time=getTime();

                Intent i=new Intent(NewTrain_Activity.this,Result_Activity.class);
                i.putExtra("stuID",stuID);
                i.putExtra("GET_DEVICE",selectedDevice);
                /*
                i.putExtra("max",max);
                i.putExtra("min",min);
                i.putExtra("avg",avg);
                i.putExtra("count",count);
                i.putExtra("valueAll",valueAll);
                i.putExtra("time",time);*/

                startActivity(i);
                NewTrain_Activity.this.finish();
            }
        });



        //10次或按結束後資料儲存
    }

    public String getTime(){
        //先行定義時間格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //取得現在時間
        Date dt=new Date();
        //透過SimpleDateFormat的format方法將Date轉為字串
        String dts=sdf.format(dt);
        return dts;
    }

    /**
     * 初始化藍芽
     */
    private void initBLE() {
        /**綁定Service
         * @see BluetoothLeService*/
        Intent bleService = new Intent(this, BluetoothLeService.class);
        bindService(bleService, mServiceConnection, BIND_AUTO_CREATE);
        /**設置廣播*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);//連接一個GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);//從GATT服務中斷開連接
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);//查找GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);//從服務中接受(收)數據

        registerReceiver(mGattUpdateReceiver, intentFilter);
        if (mBluetoothLeService != null) mBluetoothLeService.connect(selectedDevice.getAddress());  //---BT connect
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        expandableListAdapter = new ExpandableListAdapter();
        expandableListAdapter.onChildClick = this::onChildClick;
        ExpandableListView expandableListView = findViewById(R.id.gatt_services_list);
        expandableListView.setAdapter(expandableListAdapter);
        //tvAddress = findViewById(R.id.device_address);
        //tvStatus = findViewById(R.id.connection_state);
        //tvRespond = findViewById(R.id.data_value);
        Text_now=findViewById(R.id.now_text);
        Text_max=findViewById(R.id.max_text);
        Text_avg=findViewById(R.id.avg_text);
        Text_min=findViewById(R.id.min_text);
        //tvAddress.setText(selectedDevice.getAddress());
        //tvStatus.setText("未連線");
        //tvAddress.setBackgroundColor(rgb(255,0,0));
        //tvRespond.setText("---");

        //tvRespond.setMovementMethod(new ScrollingMovementMethod());
    }




    /**
     * 藍芽已連接/已斷線資訊回傳
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            mBluetoothLeService.connect(selectedDevice.getAddress());  //-------------------BT connect
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService.disconnect();
        }
    };
    /**
     * 新增資料(製圖)
     *//*
    private void addData(float inputData) {
        LineData data = chart.getData();//取得原數據
        ILineDataSet set = data.getDataSetByIndex(0);//取得曲線(因為只有一條，故為0，若有多條則需指定)
        if (set == null) {
            set = createSet();
            data.addDataSet(set);//如果是第一次跑則需要載入數據
        }
        data.addEntry(new Entry(set.getEntryCount(), inputData), 0);//新增數據點
        //---entry->barentry
        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.setVisibleXRange(0, 20);//設置可見範圍
        chart.moveViewToX(data.getEntryCount());//將可視焦點放在最新一個數據，使圖表可移動
    }*/


    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            /**如果有連接*/
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(TAG, "藍芽已連線");
                //tvStatus.setText("已連線");
                //tvAddress.setBackgroundColor(rgb(0,255,0));  //藍芽顏色

            }
            /**如果沒有連接*/
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.d(TAG, "藍芽已斷開");

            }
            /**找到GATT服務*/
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Log.d(TAG, "已搜尋到GATT服務");
                List<BluetoothGattService> gattList = mBluetoothLeService.getSupportedGattServices();
                displayGattAtLogCat(gattList);
                expandableListAdapter.setServiceInfo(gattList);
            }
            /**接收來自藍芽傳回的資料*/
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.d(TAG, "接收到藍芽資訊");
                byte[] getByteData = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                StringBuilder stringBuilder = new StringBuilder(getByteData.length);
                for (byte byteChar : getByteData)
                    stringBuilder.append(String.format("%02X ", byteChar));
                String stringData = new String(getByteData);
                Log.d(TAG, "String: " + stringData + "\n"
                        + "byte[]: " + BluetoothLeService.byteArrayToHexStr(getByteData));
                //isLedOn = getByteData.equals("486173206F6E");
                //isLedOn = BluetoothLeService.byteArrayToHexStr(getByteData).equals("486173206F6E");

                float avgg=0;
                int to_int;
                float test = 0;
                try{
                    test = Float.parseFloat(stringData);
                    Text_now.setText(stringData);
                }catch(NumberFormatException e){
                    System.out.println("浮點數轉換錯誤");
                }
                if(MAX<test) {
                    MAX=test;
                    Text_max.setText(stringData);
                    Maxx=stringData;
                }
                if(test<MIN){
                    MIN=test;
                    Minn=stringData;
                    Text_min.setText(stringData);
                }
                SUM=SUM+test;
                avgg=(SUM/COUNT)*1000;
                to_int= (int) (avgg);
                avgg=to_int;
                avgg=avgg/1000;
                AVGG= String.valueOf(avgg);
                Text_avg.setText(AVGG);

                count=String.valueOf(COUNT);
                valueAll=valueAll+"#"+test;

                if(count=="10"){
                    /*Intent i=new Intent(NewTrain_Activity.this,Result_Activity.class);
                    i.putExtra("stuID",stuID);
                    i.putExtra("GET_DEVICE",selectedDevice);

                    i.putExtra("max",max);
                    i.putExtra("min",min);
                    i.putExtra("avg",avg);
                    i.putExtra("count",count);
                    i.putExtra("valueAll",valueAll);
                    i.putExtra("time",time);
                    startActivity(i);*/
                }
                COUNT++;
                //addData(test);  //製圖

            }
        }
    };
    /*
      將藍芽所有資訊顯示在Logcat
     */
    private void displayGattAtLogCat(List<BluetoothGattService> gattList) {
        for (BluetoothGattService service : gattList) {
            Log.d(TAG, "Service: " + service.getUuid().toString());
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                Log.d(TAG, "\tCharacteristic: " + characteristic.getUuid().toString() + " ,Properties: " +
                        mBluetoothLeService.getPropertiesTagArray(characteristic.getProperties()));
                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    Log.d(TAG, "\t\tDescriptor: " + descriptor.getUuid().toString());
                }
            }
        }
    }

    /*
      關閉藍芽
     */
    private void closeBluetooth() {
        if (mBluetoothLeService == null) return;
        mBluetoothLeService.disconnect();
        unbindService(mServiceConnection);
        unregisterReceiver(mGattUpdateReceiver);

    }

    @Override
    protected void onStop() {
        super.onStop();
        closeBluetooth();
    }

    /*
      點擊物件，即寫資訊給藍芽(或直接讀藍芽裝置資訊)
     */
    //@Override
    public void onChildClick(ServiceInfo.CharacteristicInfo info) {
        String led = "off";
        if (!isLedOn) led = "on";
        mBluetoothLeService.sendValue(led, info.getCharacteristic());
    }


}