package com.example.sasha.stocktaking.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by sasha on 1/26/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "myappname.db";

    private static final int DATABASE_VERSION = 1;

    private PlaceDAO placeDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Place.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer){

        try{
            TableUtils.dropTable(connectionSource, Place.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public PlaceDAO getPlaceDAO() throws SQLException {
        if(placeDAO == null){
            placeDAO = new PlaceDAO(getConnectionSource(), Place.class);
        }
        return placeDAO;
    }


    @Override
    public void close(){
        super.close();
        placeDAO = null;
    }
}
