package org.ei.drishti.repository;

import android.database.sqlite.SQLiteDatabase;

public interface DrishtiRepository {
    void onCreate(SQLiteDatabase database);

    void updateMasterRepository(Repository repository);
}
