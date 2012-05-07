package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository implements DrishtiRepository {
    private static final String ALERTS_SQL = "CREATE TABLE alerts(caseID VARCHAR, thaayiCardNumber VARCHAR, visitCode VARCHAR, benificiaryName VARCHAR, priority INTEGER, dueDate VARCHAR)";
    private static final String ALERTS_TABLE_NAME = "alerts";
    public static final String ALERTS_CASEID_COLUMN = "caseID";
    public static final String ALERTS_THAAYI_CARD_COLUMN = "thaayiCardNumber";
    public static final String ALERTS_VISIT_CODE_COLUMN = "visitCode";
    public static final String ALERTS_BENIFICIARY_NAME_COLUMN = "benificiaryName";
    public static final String ALERTS_PRIORITY_COLUMN = "priority";
    public static final String ALERTS_DUEDATE_COLUMN = "dueDate";
    private static final String[] ALERTS_TABLE_COLUMNS = new String[] { ALERTS_CASEID_COLUMN, ALERTS_BENIFICIARY_NAME_COLUMN, ALERTS_VISIT_CODE_COLUMN, ALERTS_THAAYI_CARD_COLUMN, ALERTS_PRIORITY_COLUMN, ALERTS_DUEDATE_COLUMN};
    public static final String CASE_AND_VISIT_CODE_COLUMN_SELECTIONS = ALERTS_CASEID_COLUMN + " = ? AND " + ALERTS_VISIT_CODE_COLUMN + " = ?";
    private Repository masterRepository;

    public List<Alert> allAlerts() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllAlerts(cursor);
    }

    public void update(Action alertAction) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {alertAction.caseID(), alertAction.get("visitCode")};

        List<Alert> existingAlerts = readAllAlerts(database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS,
                CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues, null, null, null, null));

        ContentValues values = createValuesFor(alertAction, existingAlerts);
        if (existingAlerts.isEmpty()) {
            database.insert(ALERTS_TABLE_NAME, null, values);
        } else {
            database.update(ALERTS_TABLE_NAME, values, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
        }
    }

    public void delete(Action alertAction) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        database.delete(ALERTS_TABLE_NAME, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, new String[]{alertAction.caseID(), alertAction.get("visitCode")});
    }

    public void deleteAll(Action alertAction) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, ALERTS_CASEID_COLUMN + "= ?", new String[]{alertAction.caseID()});
    }

    private List<Alert> readAllAlerts(Cursor cursor) {
        cursor.moveToFirst();
        List<Alert> alerts = new ArrayList<Alert>();
        while (!cursor.isAfterLast()) {
            alerts.add(new Alert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
            cursor.moveToNext();
        }
        cursor.close();
        return alerts;
    }

    private ContentValues createValuesFor(Action alertAction, List<Alert> existingAlerts) {
        ContentValues values = new ContentValues();
        values.put(ALERTS_CASEID_COLUMN, alertAction.caseID());
        values.put(ALERTS_THAAYI_CARD_COLUMN, alertAction.get("thaayiCardNumber"));
        values.put(ALERTS_VISIT_CODE_COLUMN, alertAction.get("visitCode"));
        values.put(ALERTS_BENIFICIARY_NAME_COLUMN, alertAction.get("beneficiaryName"));
        values.put(ALERTS_PRIORITY_COLUMN, calculatePriority(existingAlerts, alertAction.get("latenessStatus")));
        values.put(ALERTS_DUEDATE_COLUMN, alertAction.get("dueDate"));
        return values;
    }

    private String calculatePriority(List<Alert> existingAlerts, String latenessStatus) {
        int thisPriority = latenessStatus.equals("late") ? 3 : 1;
        int existingPriority = existingAlerts.isEmpty() ? 0 : existingAlerts.get(0).priority();
        return String.valueOf(thisPriority + existingPriority);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(ALERTS_SQL);
    }

    public void updateMasterRepository(Repository repository) {
        this.masterRepository = repository;
    }

    public void deleteAllAlerts() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, null,null);
    }
}
