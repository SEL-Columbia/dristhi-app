package org.ei.drishti.repository;

import info.guardianproject.database.sqlcipher.SQLiteDatabase;

public abstract class DrishtiRepository {
    protected Repository masterRepository;

    public void updateMasterRepository(Repository repository) {
        this.masterRepository = repository;
    }

    abstract public void onCreate(SQLiteDatabase database);
}
