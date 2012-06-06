package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.ChildStatus;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryRepository extends DrishtiRepository {
    private static final String BENEFICIARY_SQL = "CREATE TABLE beneficiary(caseID VARCHAR PRIMARY KEY, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, status VARCHAR)";
    private static final String BENEFICIARY_TABLE_NAME = "beneficiary";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String STATUS_COLUMN = "status";
    private static final String[] BENEFICIARY_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, STATUS_COLUMN};

    public void add(Action action) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(BENEFICIARY_TABLE_NAME, null, createValuesFor(action));
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(BENEFICIARY_SQL);
    }

    public List<Beneficiary> allBeneficiaries() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    private ContentValues createValuesFor(Action action) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, action.caseID());
        values.put(EC_CASEID_COLUMN, action.get("ecCaseId"));
        values.put(THAAYI_CARD_COLUMN, action.get("thaayiCardNumber"));
        values.put(STATUS_COLUMN, action.get("status"));
        return values;
    }

    private List<Beneficiary> readAllBeneficiaries(Cursor cursor) {
        cursor.moveToFirst();
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        while (!cursor.isAfterLast()) {
            beneficiaries.add(new Beneficiary(cursor.getString(0), cursor.getString(1), cursor.getString(2), ChildStatus.from(cursor.getString(3))));
            cursor.moveToNext();
        }
        cursor.close();
        return beneficiaries;

    }
}
