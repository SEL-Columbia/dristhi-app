package org.ei.drishti.repository;

import info.guardianproject.database.sqlcipher.SQLiteDatabase;

public interface DrishtiRepository {
    void onCreate(SQLiteDatabase database);

    void updateMasterRepository(Repository repository);
}
