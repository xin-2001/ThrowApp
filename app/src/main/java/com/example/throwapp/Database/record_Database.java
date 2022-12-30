package com.example.throwapp.Database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyTable2")
public class record_Database{
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String name;
    private String stuID;
    private String time;  //次數->時間
    private String valueAll;
    private String max;
    private String min;
    private String avg;
    private String other1;


    public record_Database(String name,String stuID,String time,String valueAll,String max,String min,String avg,String other1){
        this.name=name;
        this.stuID=stuID;
        this.time=time;
        this.valueAll=valueAll;
        this.max=max;
        this.min=min;
        this.avg=avg;
        this.other1=other1;
    }

    @Ignore
    public record_Database(int id,String name,String stuID,String time,String valueAll,String max,String min,String avg,String other1){
        this.id=id;
        this.name=name;
        this.stuID=stuID;
        this.time=time;
        this.valueAll=valueAll;
        this.max=max;
        this.min=min;
        this.avg=avg;
        this.other1=other1;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getStuID(){return stuID;}
    public void setStuID(String stuID){this.stuID=stuID;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=time;}

    public String getValueAll(){return valueAll;}
    public void setValueAll(String valueAll){this.valueAll=valueAll;}

    public String getMax(){return max;}
    public void setMax(String max){this.max=max;}

    public String getMin(){return min;}
    public void setMin(String min){this.min=min;}

    public String getAvg(){return avg;}
    public void setAvg(String avg){this.avg=avg;}

    public String getOther1(){return other1;}
    public void setOther1(String other1){this.other1=other1;}

}