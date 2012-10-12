package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EligibleCoupleRepository extends DrishtiRepository {
    private static final String EC_SQL = "CREATE TABLE eligible_couple(caseID VARCHAR PRIMARY KEY, wifeName VARCHAR, husbandName VARCHAR, ecNumber VARCHAR, village VARCHAR, subCenter VARCHAR, isOutOfArea VARCHAR, details VARCHAR)";
    public static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_NUMBER_COLUMN = "ecNumber";
    private static final String WIFE_NAME_COLUMN = "wifeName";
    private static final String HUSBAND_NAME_COLUMN = "husbandName";
    public static final String EC_TABLE_NAME = "eligible_couple";
    private static final String VILLAGE_NAME_COLUMN = "village";
    private static final String SUBCENTER_NAME_COLUMN = "subCenter";
    private static final String IS_OUT_OF_AREA_COLUMN = "isOutOfArea";
    private static final String DETAILS_COLUMN = "details";
    public static final String[] EC_TABLE_COLUMNS = new String[]{CASE_ID_COLUMN, WIFE_NAME_COLUMN, HUSBAND_NAME_COLUMN, EC_NUMBER_COLUMN, VILLAGE_NAME_COLUMN, SUBCENTER_NAME_COLUMN, IS_OUT_OF_AREA_COLUMN, DETAILS_COLUMN};
    private MotherRepository motherRepository;
    private final AlertRepository alertRepository;
    private final TimelineEventRepository timelineEventRepository;

    public EligibleCoupleRepository(MotherRepository motherRepository, TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.motherRepository = motherRepository;
        this.alertRepository = alertRepository;
        this.timelineEventRepository = timelineEventRepository;
    }

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(EC_SQL);
    }

    public void add(EligibleCouple eligibleCouple) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(EC_TABLE_NAME, null, createValuesFor(eligibleCouple));
        if (StringUtils.isNotBlank(eligibleCouple.details().get("submissionDate"))) {
            timelineEventRepository.add(TimelineEvent.forECRegistered(eligibleCouple.caseId(), eligibleCouple.details().get("submissionDate")));
        }
    }

    public void updateDetails(String caseId, Map<String, String> details) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        addTimelineEventsForChanges(caseId, details);

        ContentValues valuesToUpdate = new ContentValues();
        valuesToUpdate.put(DETAILS_COLUMN, new Gson().toJson(details));
        database.update(EC_TABLE_NAME, valuesToUpdate, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public List<EligibleCouple> allEligibleCouples() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllEligibleCouples(cursor);
    }

    public List<EligibleCouple> allInAreaEligibleCouples() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, IS_OUT_OF_AREA_COLUMN + " = ?", new String[]{"false"}, null, null, null, null);
        return readAllEligibleCouples(cursor);
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

    public List<String> villages() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(true, EC_TABLE_NAME, new String[]{VILLAGE_NAME_COLUMN}, IS_OUT_OF_AREA_COLUMN + " = ?", new String[]{"false"}, null, null, null, null);
        cursor.moveToFirst();
        List<String> villages = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            villages.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return villages;
    }

    public void close(String caseId) {
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        motherRepository.closeAllCasesForEC(caseId);
        masterRepository.getWritableDatabase().delete(EC_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public long count() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + EC_TABLE_NAME + " WHERE " + IS_OUT_OF_AREA_COLUMN + " = 'false'", new String[0]);
    }

    private ContentValues createValuesFor(EligibleCouple eligibleCouple) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, eligibleCouple.caseId());
        values.put(WIFE_NAME_COLUMN, eligibleCouple.wifeName());
        values.put(HUSBAND_NAME_COLUMN, eligibleCouple.husbandName());
        values.put(EC_NUMBER_COLUMN, eligibleCouple.ecNumber());
        values.put(VILLAGE_NAME_COLUMN, eligibleCouple.village());
        values.put(SUBCENTER_NAME_COLUMN, eligibleCouple.subCenter());
        values.put(IS_OUT_OF_AREA_COLUMN, Boolean.toString(eligibleCouple.isOutOfArea()));
        values.put(DETAILS_COLUMN, new Gson().toJson(eligibleCouple.details()));
        return values;
    }

    private List<EligibleCouple> readAllEligibleCouples(Cursor cursor) {
        cursor.moveToFirst();
        List<EligibleCouple> eligibleCouples = new ArrayList<EligibleCouple>();
        while (!cursor.isAfterLast()) {
            EligibleCouple eligibleCouple = new EligibleCouple(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    new Gson().<Map<String, String>>fromJson(cursor.getString(7), new TypeToken<Map<String, String>>() {
                    }.getType()));
            if (Boolean.valueOf(cursor.getString(6)))
                eligibleCouple.asOutOfArea();
            eligibleCouples.add(eligibleCouple);
            cursor.moveToNext();
        }
        cursor.close();
        return eligibleCouples;
    }

    private void addTimelineEventsForChanges(String caseId, Map<String, String> details) {
        EligibleCouple couple = findByCaseID(caseId);
        if (couple == null) {
            return;
        }

        if (details.containsKey("currentMethod")) {
            timelineEventRepository.add(TimelineEvent.forChangeOfFPMethod(caseId, couple.details().get("currentMethod"), details.get("currentMethod"), details.get("familyPlanningMethodChangeDate")));
        }
    }
}
