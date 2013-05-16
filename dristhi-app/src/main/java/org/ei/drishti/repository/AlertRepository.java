package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertStatus;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.ei.drishti.domain.AlertStatus.closed;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.from;
import static org.ei.drishti.dto.AlertPriority.inProcess;

public class AlertRepository extends DrishtiRepository {
    private static final String ALERTS_SQL = "CREATE TABLE alerts(caseID VARCHAR, benificiaryName VARCHAR, husbandName VARCHAR, village VARCHAR, visitCode VARCHAR, thaayiCardNumber VARCHAR, priority VARCHAR, startDate VARCHAR, expiryDate VARCHAR, completionDate VARCHAR, status VARCHAR)";
    private static final String ALERTS_TABLE_NAME = "alerts";
    public static final String ALERTS_CASEID_COLUMN = "caseID";
    public static final String ALERTS_THAAYI_CARD_COLUMN = "thaayiCardNumber";
    public static final String ALERTS_VISIT_CODE_COLUMN = "visitCode";
    public static final String ALERTS_BENEFICIARY_NAME_COLUMN = "benificiaryName";
    public static final String ALERTS_HUSBAND_NAME_COLUMN = "husbandName";
    private static final String ALERTS_VILLAGE_COLUMN = "village";
    public static final String ALERTS_PRIORITY_COLUMN = "priority";
    public static final String ALERTS_STARTDATE_COLUMN = "startDate";
    public static final String ALERTS_EXPIRYDATE_COLUMN = "expiryDate";
    public static final String ALERTS_COMPLETIONDATE_COLUMN = "completionDate";
    private static final String ALERTS_STATUS_COLUMN = "status";
    private static final String[] ALERTS_TABLE_COLUMNS = new String[]{ALERTS_CASEID_COLUMN, ALERTS_BENEFICIARY_NAME_COLUMN, ALERTS_HUSBAND_NAME_COLUMN, ALERTS_VILLAGE_COLUMN, ALERTS_VISIT_CODE_COLUMN, ALERTS_THAAYI_CARD_COLUMN,
            ALERTS_PRIORITY_COLUMN, ALERTS_STARTDATE_COLUMN, ALERTS_EXPIRYDATE_COLUMN, ALERTS_COMPLETIONDATE_COLUMN, ALERTS_STATUS_COLUMN};
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
        valuesToBeUpdated.put(ALERTS_STATUS_COLUMN, closed.value());
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
            alerts.add(new Alert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), from(cursor.getString(6)),
                    cursor.getString(7), cursor.getString(8), AlertStatus.from(cursor.getString(10))).withCompletionDate(cursor.getString(9)));
            cursor.moveToNext();
        }
        cursor.close();
        return alerts;
    }

    private List<Alert> filterActiveAlerts(List<Alert> alerts) {
        List<Alert> activeAlerts = new ArrayList<Alert>();
        for (Alert alert : alerts) {
            LocalDate today = LocalDate.now();
            if (LocalDate.parse(alert.expiryDate()).isAfter(today) || (closed.equals(alert.status()) && LocalDate.parse(alert.completionDate()).isAfter(today.minusDays(3)))) {
                activeAlerts.add(alert);
            }
        }
        return activeAlerts;
    }

    private ContentValues createValuesFor(Alert alert) {
        ContentValues values = new ContentValues();
        values.put(ALERTS_CASEID_COLUMN, alert.caseId());
        values.put(ALERTS_THAAYI_CARD_COLUMN, alert.thaayiCardNo());
        values.put(ALERTS_VISIT_CODE_COLUMN, alert.visitCode());
        values.put(ALERTS_BENEFICIARY_NAME_COLUMN, alert.beneficiaryName());
        values.put(ALERTS_HUSBAND_NAME_COLUMN, alert.husbandName());
        values.put(ALERTS_VILLAGE_COLUMN, alert.village());
        values.put(ALERTS_PRIORITY_COLUMN, alert.priority().value());
        values.put(ALERTS_STARTDATE_COLUMN, alert.startDate());
        values.put(ALERTS_EXPIRYDATE_COLUMN, alert.expiryDate());
        values.put(ALERTS_COMPLETIONDATE_COLUMN, alert.completionDate());
        values.put(ALERTS_STATUS_COLUMN, alert.status().value());
        return values;
    }

    public List<Alert> findByECIdAndAlertNames(String entityId, List<String> names) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery(format("SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s IN (%s)", ALERTS_TABLE_NAME, ALERTS_CASEID_COLUMN, ALERTS_STATUS_COLUMN,ALERTS_VISIT_CODE_COLUMN,
                insertPlaceholdersForInClause(names.size())), getSelectionArgs(entityId, names));
        return readAllAlerts(cursor);
    }

    private String[] getSelectionArgs(String entityId, List<String> names) {
        List<String> selectionArgs = new ArrayList<String>();
        selectionArgs.add(entityId);
        selectionArgs.add(open.value());
        selectionArgs.addAll(names);
        return selectionArgs.toArray(new String[names.size() + 1]);
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }

    public void changeAlertPriorityToInProcess(String entityId, String alertName) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String[] caseAndVisitCodeColumnValues = {entityId, alertName};

        ContentValues valuesToBeUpdated = new ContentValues();
        valuesToBeUpdated.put(ALERTS_PRIORITY_COLUMN, inProcess.value());
        database.update(ALERTS_TABLE_NAME, valuesToBeUpdated, CASE_AND_VISIT_CODE_COLUMN_SELECTIONS, caseAndVisitCodeColumnValues);
    }
}
