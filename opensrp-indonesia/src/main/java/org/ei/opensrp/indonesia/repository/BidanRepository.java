package org.ei.opensrp.indonesia.repository;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.opensrp.repository.DrishtiRepository;

/**
 * Created by Dimas Ciputra on 8/28/15.
 */
public class BidanRepository extends DrishtiRepository{

    private static final String SQL_CREATE = "CREATE TABLE bidan(id VARCHAR PRIMARY KEY, bidanId VARCHAR, details VARCHAR)";
    public static final String BIDAN_TABLE_NAME = "bidan";
    public static final String[] BIDAN_TABLE_COLUMNS = {"id", "bidanId", "details"};

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}
