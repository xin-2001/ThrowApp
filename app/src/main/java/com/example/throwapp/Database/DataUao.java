package com.example.throwapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataUao {
    String tableName = "MyTable1";


    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertData(user_Database myData);

    /**複雜(?)新增所有資料的方法*/
    @Query("INSERT INTO "+tableName+"(name,stuID,gender,birthday,height,weight,hand,other1) VALUES(:name,:stuID,:gender,:birthday,:height,:weight,:hand,:other1)")
    void insertData(String name,String stuID,String gender,String birthday,String height,String weight,String hand,String other1);

    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<user_Database> displayAll();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName +" WHERE name = :name")
    List<user_Database> findDataByName(String name);

    @Query ("SELECT * FROM " + tableName +" WHERE stuID = :stuID")
    user_Database findDataByStuID(String stuID);
    //List<user_Database> findDataByStuID(String stuID);

    @Query ("SELECT * FROM " + tableName +" WHERE id = :id")
    user_Database findDataByID(String id);

    /**簡易更新資料的方法*/
    @Update
    void updateData(user_Database myData);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE "+tableName+" SET name = :name,stuID=:stuID,gender=:gender,birthday = :birthday,height= :height,weight=:weight,hand=:hand,other1=:other1 WHERE id = :id" )
    void updateData(int id,String name,String stuID,String gender,String birthday,String height,String weight,String hand,String other1);

    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(user_Database myData);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteData(int id);
/*
    @Transaction
    @Query("SELECT * FROM "+tableName)
    public List<UserAndRecord> getUsersAndRecord();*/

}
