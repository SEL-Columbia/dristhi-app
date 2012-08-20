package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.EligibleCouple;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoupleRepository extends DrishtiRepository {
    private static final String EC_SQL = "CREATE TABLE eligible_couple(caseID VARCHAR PRIMARY KEY, wifeName VARCHAR, husbandName VARCHAR, ecNumber VARCHAR, currentMethod VARCHAR, village VARCHAR, subCenter VARCHAR, details VARCHAR)";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_NUMBER_COLUMN = "ecNumber";
    private static final String WIFE_NAME_COLUMN = "wifeName";
    private static final String HUSBAND_NAME_COLUMN = "husbandName";
    private static final String EC_TABLE_NAME = "eligible_couple";
    private static final String CURRENT_METHOD_COLUMN = "currentMethod";
    private static final String VILLAGE_NAME_COLUMN = "village";
    private static final String SUBCENTER_NAME_COLUMN = "subCenter";
    private static final String DETAILS_COLUMN = "details";
    private static final String[] EC_TABLE_COLUMNS = new String[] {CASE_ID_COLUMN, WIFE_NAME_COLUMN, HUSBAND_NAME_COLUMN, EC_NUMBER_COLUMN, CURRENT_METHOD_COLUMN, VILLAGE_NAME_COLUMN, SUBCENTER_NAME_COLUMN, DETAILS_COLUMN};
    private MotherRepository motherRepository;
    private final AlertRepository alertRepository;
    private final TimelineEventRepository timelineEventRepository;

    public EligibleCoupleRepository(MotherRepository motherRepository, TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.motherRepository = motherRepository;
        this.alertRepository = alertRepository;
        this.timelineEventRepository = timelineEventRepository;
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(EC_SQL);
    }

    public void add(EligibleCouple eligibleCouple) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(EC_TABLE_NAME, null, createValuesFor(eligibleCouple));
    }

    public EligibleCouple findByCaseID(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<EligibleCouple> couples = readAllEligibleCouples(cursor);
        if (couples.isEmpty()) {
            return null;
        }
        return couples.get(0);
    }

    public void close(String caseId) {
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        motherRepository.closeAllCasesForEC(caseId);
        masterRepository.getWritableDatabase().delete(EC_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void deleteAllEligibleCouples() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.delete(EC_TABLE_NAME, null,null);
    }

    public List<EligibleCouple> allEligibleCouples() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllEligibleCouples(cursor);
    }

    public long count() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + EC_TABLE_NAME, new String[0]);
    }

    private ContentValues createValuesFor(EligibleCouple eligibleCouple) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN,eligibleCouple.caseId());
        values.put(EC_NUMBER_COLUMN, eligibleCouple.ecNumber());
        values.put(WIFE_NAME_COLUMN, eligibleCouple.wifeName());
        values.put(HUSBAND_NAME_COLUMN, eligibleCouple.husbandName());
        values.put(CURRENT_METHOD_COLUMN, eligibleCouple.currentMethod());
        values.put(VILLAGE_NAME_COLUMN, eligibleCouple.village());
        values.put(SUBCENTER_NAME_COLUMN, eligibleCouple.subCenter());
        values.put(DETAILS_COLUMN, eligibleCouple.details());
        return values;
    }

    private List<EligibleCouple> readAllEligibleCouples(Cursor cursor) {
        cursor.moveToFirst();
        List<EligibleCouple> eligibleCouples = new ArrayList<EligibleCouple>();
        while (!cursor.isAfterLast()) {
            eligibleCouples.add(new EligibleCouple(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            cursor.moveToNext();
        }
        cursor.close();
        return eligibleCouples;
    }
}
