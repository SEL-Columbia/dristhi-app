package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository extends DrishtiRepository {
    private static final String ALERTS_SQL = "CREATE TABLE alerts(caseID VARCHAR, thaayiCardNumber VARCHAR, visitCode VARCHAR, benificiaryName VARCHAR, village VARCHAR, priority INTEGER, dueDate VARCHAR)";
    private static final String ALERTS_TABLE_NAME = "alerts";
    public static final String ALERTS_CASEID_COLUMN = "caseID";
    public static final String ALERTS_THAAYI_CARD_COLUMN = "thaayiCardNumber";
    public static final String ALERTS_VISIT_CODE_COLUMN = "visitCode";
    public static final String ALERTS_BENEFICIARY_NAME_COLUMN = "benificiaryName";
    private static final String ALERTS_VILLAGE_COLUMN = "village";
    public static final String ALERTS_PRIORITY_COLUMN = "priority";
    public static final String ALERTS_DUEDATE_COLUMN = "dueDate";
    private static final String[] ALERTS_TABLE_COLUMNS = new String[] { ALERTS_CASEID_COLUMN, ALERTS_BENEFICIARY_NAME_COLUMN, ALERTS_VILLAGE_COLUMN, ALERTS_VISIT_CODE_COLUMN, ALERTS_THAAYI_CARD_COLUMN, ALERTS_PRIORITY_COLUMN, ALERTS_DUEDATE_COLUMN};
    public static final String CASE_AND_VISIT_CODE_COLUMN_SELECTIONS = ALERTS_CASEID_COLUMN + " = ? AND " + ALERTS_VISIT_CODE_COLUMN + " = ?";

    public List<Alert> allAlerts() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllAlerts(cursor);
    }

    public List<String> uniqueLocations() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(true, ALERTS_TABLE_NAME, new String[] { ALERTS_VILLAGE_COLUMN }, null, null, null, null, null, null);
        return readAllVillages(cursor);
    }

    public void createAlert(Alert alert) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {alert.caseId(), alert.visitCode()};

        List<Alert> existingAlerts = readAllAlerts(database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS,
                CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues, null, null, null, null));

        ContentValues values = createValuesFor(alert, existingAlerts);
        if (existingAlerts.isEmpty()) {
            database.insert(ALERTS_TABLE_NAME, null, values);
        } else {
            database.update(ALERTS_TABLE_NAME, values, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
        }
    }

    public void deleteAlertsForVisitCodeOfCase(String caseId, String visitCode) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        database.delete(ALERTS_TABLE_NAME, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, new String[]{caseId, visitCode});
    }

    public void deleteAllAlertsForCase(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, ALERTS_CASEID_COLUMN + "= ?", new String[]{caseId});
    }

    public void deleteAllAlerts() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, null,null);
    }

    private List<Alert> readAllAlerts(Cursor cursor) {
        cursor.moveToFirst();
        List<Alert> alerts = new ArrayList<Alert>();
        while (!cursor.isAfterLast()) {
            alerts.add(new Alert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6)));
            cursor.moveToNext();
        }
        cursor.close();
        return alerts;
    }

    private List<String> readAllVillages(Cursor cursor) {
        cursor.moveToFirst();
        List<String> villages = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String village = cursor.getString(0);
            if (village != null) {
                villages.add(village);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return villages;
    }

    private ContentValues createValuesFor(Alert alert, List<Alert> existingAlerts) {
        ContentValues values = new ContentValues();
        values.put(ALERTS_CASEID_COLUMN, alert.caseId());
        values.put(ALERTS_THAAYI_CARD_COLUMN, alert.thaayiCardNo());
        values.put(ALERTS_VISIT_CODE_COLUMN, alert.visitCode());
        values.put(ALERTS_BENEFICIARY_NAME_COLUMN, alert.beneficiaryName());
        values.put(ALERTS_VILLAGE_COLUMN, alert.village());
        values.put(ALERTS_PRIORITY_COLUMN, calculatePriority(existingAlerts, alert.priority()));
        values.put(ALERTS_DUEDATE_COLUMN, alert.dueDate());
        return values;
    }

    private String calculatePriority(List<Alert> existingAlerts, int priority) {
        int existingPriority = existingAlerts.isEmpty() ? 0 : existingAlerts.get(0).priority();
        return String.valueOf(priority + existingPriority);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(ALERTS_SQL);
    }
}
