package com.example.throwapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataUao2 {

    String table2Name = "MyTable2";

    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertData(record_Database myData);

    /**複雜(?)新增所有資料的方法*/
    @Query("INSERT INTO "+table2Name+"(name,stuID,time,valueAll,max,min,avg,other1) VALUES(:name,:stuID,:time,:valueAll,:max,:min,:avg,:other1)")
    void insertData(String name,String stuID, String time, String valueAll, String max, String min, String avg,String other1);

    /**撈取全部資料*/
    @Query("SELECT * FROM " + table2Name)
    List<record_Database> displayAll();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + table2Name +" WHERE stuID = :stuID")
    List<record_Database> findDataByStuID(String stuID);

    /**簡易更新資料的方法*/
    @Update
    void updateData(record_Database myData);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE "+table2Name+" SET name=:name,stuID=:stuID,time=:time,valueAll = :valueAll,max= :max,min=:min,avg=:avg,other1=:other1 WHERE id = :id" )
    void updateData(int id,String name,String stuID, String time,String valueAll, String max, String min, String avg,String other1);

    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(record_Database myData);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + table2Name + " WHERE id = :id")
    void deleteData(int id);

}

