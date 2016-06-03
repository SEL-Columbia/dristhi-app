package org.ei.opensrp.vaccinator.repository;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.opensrp.repository.DrishtiRepository;

/**
 * Created by muhammad.ahmed@ihsinformatics.com on 16-Nov-15.
 */
public class ClientRepository extends DrishtiRepository {

    public static final String ID_COLUMN = "id";
    public static final String Relational_ID_COLUMN  = "relationalid";
    public static final String DETAILS_COLUMN = "details";
    public static final String TYPE_COLUMN = "type";
    private String CREATE_CLIENT_SQL = "CREATE TABLE common("+ID_COLUMN+" VARCHAR PRIMARY KEY," +Relational_ID_COLUMN+
            "VARCHAR , "+TYPE_COLUMN+" VARCHAR , "+DETAILS_COLUMN+" VARCHAR)";






    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CLIENT_SQL);

    }
}
