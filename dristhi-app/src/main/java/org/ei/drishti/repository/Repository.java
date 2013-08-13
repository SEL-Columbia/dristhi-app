package org.ei.drishti.repository;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import org.ei.drishti.util.Session;

import java.io.File;

public class Repository extends SQLiteOpenHelper {
    private DrishtiRepository[] repositories;
    private File databasePath;
    private Context context;
    private String dbName;
    private Session session;

    public Repository(Context context, Session session, DrishtiRepository... repositories) {
        super(context, session.repositoryName(), null, 1);
        this.repositories = repositories;
        this.databasePath = context.getDatabasePath(session.repositoryName());
        this.context = context;
        this.dbName = session.repositoryName();
        this.session = session;

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
        return session.password();
    }

    public void deleteRepository() {
        close();
        context.deleteDatabase(dbName);
        context.getDatabasePath(dbName).delete();
    }
}
