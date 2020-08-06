package detai.android.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import detai.android.CommonUtils.Constants;

public abstract class ThiTracNghiemDbContext extends SQLiteOpenHelper {

    public ThiTracNghiemDbContext(Context context)
    {
        super(context, Constants.DATABASE_NAME, null, Constants.SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + Constants.TBL_CAUHOI + "(" +
                Constants.COL_CAUHOI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.COL_CAUHOI_NOIDUNG + " TEXT," +
                Constants.COL_CAUHOI_DAPANA + " TEXT," +
                Constants.COL_CAUHOI_DAPANB + " TEXT," +
                Constants.COL_CAUHOI_DAPANC + " TEXT," +
                Constants.COL_CAUHOI_DAPAND + " TEXT," +
                Constants.COL_CAUHOI_DAPANDUNG + " INTEGER);");

        sqLiteDatabase.execSQL("CREATE TABLE " + Constants.TBL_THISINH + "(" +
                Constants.COL_THISINH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.COL_THISINH_TENTHISINH + " TEXT," +
                Constants.COL_THISINH_SOCAUDUNG + " TEXT," +
                Constants.COL_THISINH_SODIEM + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public abstract boolean Insert(Object ob);

    public abstract boolean Update(Object ob);

    public abstract boolean Delete(Integer id,boolean isDeleteAll);

    public abstract ArrayList<Object> GetAll();


}
