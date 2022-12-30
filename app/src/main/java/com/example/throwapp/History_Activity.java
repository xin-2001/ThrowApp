package com.example.throwapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.throwapp.Database.DATAbase;
import com.example.throwapp.Database.record_Database;
import com.example.throwapp.Database.user_Database;
import com.facebook.stetho.Stetho;

import java.util.List;

public class History_Activity extends AppCompatActivity {
    public Button buttonBack;

    MyAdapter myAdapter;
    record_Database nowSelectedData;//取得在畫面上顯示中的資料內容

    public String stuID;
    private ScannedData selectedDevice;
    public static final String INTENT_KEY = "GET_DEVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        selectedDevice = (ScannedData) getIntent().getSerializableExtra(INTENT_KEY);

        Stetho.initializeWithDefaults(this);//設置資料庫監視
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(recyclerView);//設置RecyclerView左滑刪除

        buttonBack=findViewById(R.id.button_back);

        Intent intent=getIntent();
        stuID=intent.getStringExtra("stuID");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History_Activity.this.finish();
            }
        });

        new Thread(() -> {
            List<record_Database> data = DATAbase.getInstance(this).getDataUao2().findDataByStuID(stuID);
            myAdapter = new MyAdapter(this, data);
            runOnUiThread(() -> {
                recyclerView.setAdapter(myAdapter);
                /**===============================================================================*/
                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {//原本的樣貌
                    @Override
                    public void onItemClick(record_Database myData) {
                    }
                });
                /**===============================================================================*/
                /**取得被選中的資料，並顯示於畫面*/
                myAdapter.setOnItemClickListener((myData) -> {//匿名函式(原貌在上方)
                    nowSelectedData = myData;
                    /*info.setText("");
                    info.append(String.valueOf(myData.getId()));
                    info.append('\n'+myData.getName());
                    info.append('\n'+myData.getStuID());
                    info.append('\n'+myData.getGender());
                    info.append('\n'+myData.getBirthday());
                    info.append('\n'+myData.getHeight());
                    info.append('\n'+myData.getWeight());
                    info.append('\n'+myData.getHand());*/
                    /*遷移後新增*/
                    nowSelectedData = null;

                    /*Intent i=new Intent(Login_Activity.this,User_Activity.class);
                    i.putExtra("stuID",myData.getStuID());
                    i.putExtra("GET_DEVICE",selectedDevice);
                    startActivity(i);*/

                });

            });
        }).start();

    }

    protected void onRestart() {
        super.onRestart();

        //重新獲取數據的邏輯，此處根據自己的要求回去
        myAdapter.refreshView();

    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<record_Database> myData;
        private Activity activity;
        private OnItemClickListener onItemClickListener;

        public MyAdapter(Activity activity, List<record_Database> myData) {
            this.activity = activity;
            this.myData = myData;
        }
        /**建立對外接口*/
        public void setOnItemClickListener(OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(android.R.id.text1);
                view = itemView;
            }
        }
        /**更新資料*/
        public void refreshView() {
            new Thread(()->{
                List<record_Database> data = DATAbase.getInstance(activity).getDataUao2().displayAll();
                this.myData = data;
                activity.runOnUiThread(() -> {
                    notifyDataSetChanged();
                });
            }).start();
        }
        /**刪除資料*/
        public void deleteData(int position){
            new Thread(()->{
                DATAbase.getInstance(activity).getDataUao2().deleteData(myData.get(position).getId());
                activity.runOnUiThread(()->{
                    notifyItemRemoved(position);
                    refreshView();
                });
            }).start();
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, null);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            //設定清單顯示項目
            holder.tvTitle.setText(myData.get(position).getTime()+"　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　");
            holder.view.setOnClickListener((v)->{
                onItemClickListener.onItemClick(myData.get(position));
            });

        }
        @Override
        public int getItemCount() {
            return myData.size();
        }
        /**建立對外接口*/
        public interface OnItemClickListener {
            void onItemClick(record_Database myData);
        }

    }
    private void setRecyclerFunction(RecyclerView recyclerView){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {//設置RecyclerView手勢功能
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        myAdapter.deleteData(position);
                        break;

                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

}