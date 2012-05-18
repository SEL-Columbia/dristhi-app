package org.ei.drishti.repository;

import android.content.Context;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import info.guardianproject.database.sqlcipher.SQLiteOpenHelper;

import java.io.File;

public class Repository extends SQLiteOpenHelper {
    private DrishtiRepository[] repositories;
    private File databasePath;
    private Context context;
    private String dbName;

    public Repository(Context context, String dbName, DrishtiRepository... repositories) {
        super(context, dbName, null, 1);
        this.repositories = repositories;
        this.databasePath = context.getDatabasePath(dbName);
        this.context = context;
        this.dbName = dbName;

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
        if (password() == null) {
            throw new RuntimeException("Password has not been set!");
        }
        return super.getReadableDatabase(password());
    }

    public SQLiteDatabase getWritableDatabase() {
        if (password() == null) {
            throw new RuntimeException("Password has not been set!");
        }
        return super.getWritableDatabase(password());
    }

    public boolean canUseThisPassword(String password) {
        try {
            SQLiteDatabase database = SQLiteDatabase.openDatabase(databasePath.getPath(), password, null, SQLiteDatabase.OPEN_READONLY);
            database.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String password() {
        return org.ei.drishti.Context.getInstance().password();
    }

    public void deleteRepository() {
        close();
        boolean resultOfDelete = context.deleteDatabase(dbName);
        context.getDatabasePath(dbName).delete();
    }
}
