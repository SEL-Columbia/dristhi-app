package org.ei.drishti.repository;

import android.content.Context;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import info.guardianproject.database.sqlcipher.SQLiteOpenHelper;

public class Repository extends SQLiteOpenHelper {
    private DrishtiRepository[] repositories;
    private String password = "secret :) just for now";

    public Repository(Context context, String dbName, DrishtiRepository... repositories) {
        super(context, dbName, null, 1);
        this.repositories = repositories;

        SQLiteDatabase.loadLibs(context);
        for (DrishtiRepository repository : repositories) {
            repository.updateMasterRepository(this);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (DrishtiRepository repository : repositories) {
            repository.onCreate(database);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase(password);
    }

    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase(password);
    }
}
