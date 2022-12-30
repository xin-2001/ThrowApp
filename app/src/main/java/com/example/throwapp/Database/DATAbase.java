package com.example.throwapp.Database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {user_Database.class,record_Database.class},version = 1,exportSchema = false)  //exportSchema = true,autoMigrations={ @AutoMigration (from =1,to=2)}
public abstract class DATAbase extends RoomDatabase {
    public static final String DB_NAME = "UserData.db";
    private static volatile DATAbase instance;


    public static synchronized DATAbase getInstance(Context context){
        if (instance == null){
            instance = create(context);  //創立新的資料庫
            /*instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database_user.class,DB_NAME).addMigrations(MARGIN_1to2,MARGIN_2to3).build();
*/
        }

        return instance;
    }


    private static DATAbase create(final Context context){
        return Room.databaseBuilder(context, DATAbase.class,DB_NAME).build();
    }

    public static Migration MARGIN_1to2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE \"MyTable\"  ADD COLUMN hand INTEGER NOT NULL DEFAULT 2");


        }
    };



    public abstract DataUao getDataUao();//設置對外接口
    public abstract DataUao2 getDataUao2();

}
