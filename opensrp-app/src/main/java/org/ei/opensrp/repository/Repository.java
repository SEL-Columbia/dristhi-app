package org.ei.opensrp.repository;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.apache.commons.lang3.StringUtils;
import org.ei.opensrp.commonregistry.CommonFtsObject;
import org.ei.opensrp.util.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository extends SQLiteOpenHelper {
    private DrishtiRepository[] repositories;
    private File databasePath;
    private Context context;
    private String dbName;
    private Session session;
    private CommonFtsObject commonFtsObject;

    public Repository(Context context, Session session, DrishtiRepository... repositories) {
        super(context, session.repositoryName(), null, 1);
        this.repositories = repositories;
        this.context = context;
        this.session = session;
        this.dbName = session != null ? session.repositoryName() : "drishti.db";
        this.databasePath = context != null ? context.getDatabasePath(dbName) : new File("/data/data/org.ei.opensrp.indonesia/databases/drishti.db");

        SQLiteDatabase.loadLibs(context);
        for (DrishtiRepository repository : repositories) {
            repository.updateMasterRepository(this);
        }
    }

    public Repository(Context context, Session session, CommonFtsObject commonFtsObject, DrishtiRepository... repositories) {
        this(context, session, repositories);
        this.commonFtsObject = commonFtsObject;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (DrishtiRepository repository : repositories) {
            repository.onCreate(database);
        }

        if(this.commonFtsObject != null) {
            for (String ftsTable: commonFtsObject.getTables()) {
                String[] sortFields = this.commonFtsObject.getSortFields(ftsTable);
                List<String> searchColumns = new ArrayList<String>();
                for(String sortValue: sortFields){
                    searchColumns.add(CommonFtsObject.sortColumn(sortValue));
                }
                searchColumns.add(0, CommonFtsObject.idColumn);
                searchColumns.add(1, CommonFtsObject.relationalIdColumn);
                searchColumns.add(2, CommonFtsObject.phraseColumnName);

                String joinedSearchColumns = StringUtils.join(searchColumns, ",");

                String searchSql = "create virtual table " + CommonFtsObject.searchTableName(ftsTable) + " using fts4 (" + joinedSearchColumns + ");";
                database.execSQL(searchSql);

            }
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
