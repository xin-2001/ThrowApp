package com.example.throwapp.Database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyTable1")
public class user_Database{

    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String name;
    private String stuID;
    private String gender;
    private String birthday;
    private String height;
    private String weight;
    private String hand;
    private String other1;


    public user_Database(String name,String stuID,String gender,String birthday,String height,String weight,String hand,String other1){
        this.name=name;
        this.stuID=stuID;
        this.gender=gender;
        this.birthday=birthday;
        this.height=height;
        this.weight=weight;
        this.hand=hand;
        this.other1=other1;
    }

    @Ignore
    public user_Database(int id,String name,String stuID,String gender,String birthday,String height,String weight,String hand,String other1){
        this.id=id;
        this.name=name;
        this.stuID=stuID;
        this.gender=gender;
        this.birthday=birthday;
        this.height=height;
        this.weight=weight;
        this.hand=hand;
        this.other1=other1;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getStuID(){return stuID;}
    public void setStuID(String stuID){this.stuID=stuID;}

    public String getGender(){return gender;}
    public void setGender(String gender){this.gender=gender;}

    public String getBirthday(){return birthday;}
    public void setBirthday(String birthday){this.birthday=birthday;}

    public String getHeight(){return height;}
    public void setHeight(String height){this.height=height;}

    public String getWeight(){return weight;}
    public void setWeight(String weight){this.weight=weight;}

    public String getHand(){return hand;}
    public void setHand(String hand){this.hand=hand;}

    public String getOther1(){return other1;}
    public void setOther1(String other1){this.other1=other1;}
}
