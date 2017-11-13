package com.nvn.myplace.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nvn.myplace.data.model.DBUtils;

/**
 * Created by n on 05/11/2017.
 */

public class PlaceSqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PLACE";
    private static final int DB_VERSION = 1;

    private static final String CREATE_PLACE_TBL_SQL =
            "CREATE TABLE " + DBUtils.PLACE_TBL_NAME + "("
                        +DBUtils.COLUMN_PLACE_ID + " "              + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.PRIMARY_KEY +","
                        +DBUtils.COLUMN_PLACE_CATEGORY_ID + " "     + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_NAME+ " "             + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_ADDRESS + " "         + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_DESCRIPTION + " "     + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_IMAGE + " "           + DBUtils.BLOB_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_LAT + " "             + DBUtils.REAL_DATA_TYPE + " " + DBUtils.NOT_NULL +","
                        +DBUtils.COLUMN_PLACE_LNG + " "             + DBUtils.REAL_DATA_TYPE + " " + DBUtils.NOT_NULL +""
                    +")";

    private static final String CREATE_CATEGORY_TBL_SQL =
            "CREATE TABLE " + DBUtils.CATEGORY_TBL_NAME + "("
                    +DBUtils.COLUMN_CATEGORY_ID + " "               + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.PRIMARY_KEY +","
                    +DBUtils.COLUMN_CATEGORY_NAME + " "             + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL +""
                    +")";

    private static final String INSERT_CATEGORY_SQL =
            "INSERT INTO " +DBUtils.CATEGORY_TBL_NAME + "(" +DBUtils.COLUMN_CATEGORY_ID + ", " + DBUtils.COLUMN_CATEGORY_NAME + ")"
            + " VALUES "
            + "('1', 'Restaurant'), " + "('2', 'Cinema'), " + "('3', 'Fashion'), " + "('4', 'ATM')";


    public PlaceSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PLACE_TBL_SQL);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TBL_SQL);
        sqLiteDatabase.execSQL(INSERT_CATEGORY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
