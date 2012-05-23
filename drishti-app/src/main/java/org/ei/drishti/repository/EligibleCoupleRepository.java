package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.EligibleCouple;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoupleRepository implements DrishtiRepository {
    private static final String EC_SQL = "CREATE TABLE eligible_couple(caseID VARCHAR PRIMARY KEY, wifeName VARCHAR, husbandName VARCHAR, ecNumber VARCHAR, village VARCHAR, subCenter VARCHAR)";
    public static final String CASE_ID_COLUMN = "caseID";
    public static final String EC_NUMBER_COLUMN = "ecNumber";
    public static final String WIFE_NAME_COLUMN = "wifeName";
    public static final String HUSBAND_NAME_COLUMN = "husbandName";
    private static final String EC_TABLE_NAME = "eligible_couple";
    private static final String VILLAGE_NAME_COLUMN = "village";
    private static final String SUBCENTER_NAME_COLUMN = "subCenter";
    private static final String[] EC_TABLE_COLUMNS = new String[] {CASE_ID_COLUMN, WIFE_NAME_COLUMN, HUSBAND_NAME_COLUMN, EC_NUMBER_COLUMN, VILLAGE_NAME_COLUMN, SUBCENTER_NAME_COLUMN};
    private Repository masterRepository;

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(EC_SQL);
    }

    public void updateMasterRepository(Repository repository) {
        this.masterRepository = repository;
    }

    public void add(Action createAction) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(EC_TABLE_NAME, null, createValuesFor(createAction));
    }

    public void delete(Action deleteAction) {
        masterRepository.getWritableDatabase().delete(EC_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{deleteAction.caseID()});
    }

    public void deleteAllEligibleCouples() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(EC_TABLE_NAME, null,null);
    }

    public List<EligibleCouple> allEligibleCouples() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllAlerts(cursor);
    }

    private ContentValues createValuesFor(Action action) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, action.caseID());
        values.put(EC_NUMBER_COLUMN, action.get("ecNumber"));
        values.put(WIFE_NAME_COLUMN, action.get("wife"));
        values.put(HUSBAND_NAME_COLUMN, action.get("husband"));
        values.put(VILLAGE_NAME_COLUMN, action.get("village"));
        values.put(SUBCENTER_NAME_COLUMN, action.get("subcenter"));
        return values;
    }

    private List<EligibleCouple> readAllAlerts(Cursor cursor) {
        cursor.moveToFirst();
        List<EligibleCouple> eligibleCouples = new ArrayList<EligibleCouple>();
        while (!cursor.isAfterLast()) {
            eligibleCouples.add(new EligibleCouple(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return eligibleCouples;
    }
}
