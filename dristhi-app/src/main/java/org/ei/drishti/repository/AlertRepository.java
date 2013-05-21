package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Alert;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.ei.drishti.dto.AlertStatus.*;

public class AlertRepository extends DrishtiRepository {
    private static final String ALERTS_SQL = "CREATE TABLE alerts(caseID VARCHAR, visitCode VARCHAR, status VARCHAR, startDate VARCHAR, expiryDate VARCHAR, completionDate VARCHAR)";
    private static final String ALERTS_TABLE_NAME = "alerts";
    public static final String ALERTS_CASEID_COLUMN = "caseID";
    public static final String ALERTS_VISIT_CODE_COLUMN = "visitCode";
    public static final String ALERTS_STATUS_COLUMN = "status";
    public static final String ALERTS_STARTDATE_COLUMN = "startDate";
    public static final String ALERTS_EXPIRYDATE_COLUMN = "expiryDate";
    public static final String ALERTS_COMPLETIONDATE_COLUMN = "completionDate";
    private static final String[] ALERTS_TABLE_COLUMNS = new String[]{
            ALERTS_CASEID_COLUMN,
            ALERTS_VISIT_CODE_COLUMN,
            ALERTS_STATUS_COLUMN,
            ALERTS_STARTDATE_COLUMN,
            ALERTS_EXPIRYDATE_COLUMN,
            ALERTS_COMPLETIONDATE_COLUMN
    };
    public static final String CASE_AND_VISIT_CODE_COLUMN_SELECTIONS = ALERTS_CASEID_COLUMN + " = ? AND " + ALERTS_VISIT_CODE_COLUMN + " = ?";

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(ALERTS_SQL);
    }

    public List<Alert> allAlerts() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllAlerts(cursor);
    }

    public List<Alert> allActiveAlertsForCase(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS, ALERTS_CASEID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        return filterActiveAlerts(readAllAlerts(cursor));
    }

    public void createAlert(Alert alert) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {alert.caseId(), alert.visitCode()};

        List<Alert> existingAlerts = readAllAlerts(database.query(ALERTS_TABLE_NAME, ALERTS_TABLE_COLUMNS,
                CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues, null, null, null, null));

        ContentValues values = createValuesFor(alert);
        if (existingAlerts.isEmpty()) {
            database.insert(ALERTS_TABLE_NAME, null, values);
        } else {
            database.update(ALERTS_TABLE_NAME, values, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
        }
    }

    public void markAlertAsClosed(String caseId, String visitCode, String completionDate) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {caseId, visitCode};

        ContentValues valuesToBeUpdated = new ContentValues();
        valuesToBeUpdated.put(ALERTS_STATUS_COLUMN, complete.value());
        valuesToBeUpdated.put(ALERTS_COMPLETIONDATE_COLUMN, completionDate);
        database.update(ALERTS_TABLE_NAME, valuesToBeUpdated, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
    }

    public void deleteAllAlertsForCase(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, ALERTS_CASEID_COLUMN + "= ?", new String[]{caseId});
    }

    public void deleteAllAlerts() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(ALERTS_TABLE_NAME, null, null);
    }

    private List<Alert> readAllAlerts(Cursor cursor) {
        cursor.moveToFirst();
        List<Alert> alerts = new ArrayList<Alert>();
        while (!cursor.isAfterLast()) {
            alerts.add(
                    new Alert(cursor.getString(cursor.getColumnIndex(ALERTS_CASEID_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(ALERTS_VISIT_CODE_COLUMN)),
                            from(cursor.getString(cursor.getColumnIndex(ALERTS_STATUS_COLUMN))),
                            cursor.getString(cursor.getColumnIndex(ALERTS_STARTDATE_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(ALERTS_EXPIRYDATE_COLUMN))
                    )
                            .withCompletionDate(cursor.getString(cursor.getColumnIndex(ALERTS_COMPLETIONDATE_COLUMN))));
            cursor.moveToNext();
        }
        cursor.close();
        return alerts;
    }

    private List<Alert> filterActiveAlerts(List<Alert> alerts) {
        List<Alert> activeAlerts = new ArrayList<Alert>();
        for (Alert alert : alerts) {
            LocalDate today = LocalDate.now();
            if (LocalDate.parse(alert.expiryDate()).isAfter(today) || (complete.equals(alert.status()) && LocalDate.parse(alert.completionDate()).isAfter(today.minusDays(3)))) {
                activeAlerts.add(alert);
            }
        }
        return activeAlerts;
    }

    private ContentValues createValuesFor(Alert alert) {
        ContentValues values = new ContentValues();
        values.put(ALERTS_CASEID_COLUMN, alert.caseId());
        values.put(ALERTS_VISIT_CODE_COLUMN, alert.visitCode());
        values.put(ALERTS_STATUS_COLUMN, alert.status().value());
        values.put(ALERTS_STARTDATE_COLUMN, alert.startDate());
        values.put(ALERTS_EXPIRYDATE_COLUMN, alert.expiryDate());
        values.put(ALERTS_COMPLETIONDATE_COLUMN, alert.completionDate());
        return values;
    }

    public List<Alert> findByECIdAndAlertNames(String entityId, List<String> names) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery(format("SELECT * FROM %s WHERE %s = ? AND %s != ? AND %s IN (%s) ORDER BY DATE(%s)", ALERTS_TABLE_NAME, ALERTS_CASEID_COLUMN, ALERTS_STATUS_COLUMN, ALERTS_VISIT_CODE_COLUMN,
                insertPlaceholdersForInClause(names.size()), ALERTS_STARTDATE_COLUMN), getSelectionArgs(entityId, names));
        return readAllAlerts(cursor);
    }

    private String[] getSelectionArgs(String entityId, List<String> names) {
        List<String> selectionArgs = new ArrayList<String>();
        selectionArgs.add(entityId);
        selectionArgs.add(complete.value());
        selectionArgs.addAll(names);
        return selectionArgs.toArray(new String[names.size() + 1]);
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }

    public void changeAlertStatusToInProcess(String entityId, String alertName) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {entityId, alertName};

        ContentValues valuesToBeUpdated = new ContentValues();
        valuesToBeUpdated.put(ALERTS_STATUS_COLUMN, inProcess.value());
        database.update(ALERTS_TABLE_NAME, valuesToBeUpdated, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
    }
}
