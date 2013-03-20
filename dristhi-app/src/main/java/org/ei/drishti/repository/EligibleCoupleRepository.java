package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.ei.drishti.domain.TimelineEvent.forChangeOfFPMethod;

public class EligibleCoupleRepository extends DrishtiRepository {
    private static final String EC_SQL = "CREATE TABLE eligible_couple(id VARCHAR PRIMARY KEY, wifeName VARCHAR, husbandName VARCHAR, ecNumber VARCHAR, village VARCHAR, subCenter VARCHAR, isOutOfArea VARCHAR, details VARCHAR, isClosed INTEGER)";
    public static final String CASE_ID_COLUMN = "id";
    private static final String EC_NUMBER_COLUMN = "ecNumber";
    private static final String WIFE_NAME_COLUMN = "wifeName";
    private static final String HUSBAND_NAME_COLUMN = "husbandName";
    public static final String EC_TABLE_NAME = "eligible_couple";
    private static final String VILLAGE_NAME_COLUMN = "village";
    private static final String SUBCENTER_NAME_COLUMN = "subCenter";
    private static final String IS_OUT_OF_AREA_COLUMN = "isOutOfArea";
    private static final String DETAILS_COLUMN = "details";
    private static final String IS_CLOSED_COLUMN = "isClosed";
    public static final String[] EC_TABLE_COLUMNS = new String[]{CASE_ID_COLUMN, WIFE_NAME_COLUMN, HUSBAND_NAME_COLUMN, EC_NUMBER_COLUMN, VILLAGE_NAME_COLUMN, SUBCENTER_NAME_COLUMN, IS_OUT_OF_AREA_COLUMN, DETAILS_COLUMN, IS_CLOSED_COLUMN};

    public static final String CURRENT_FP_METHOD_FIELD_NAME = "currentMethod";
    public static final String FP_UPDATE_FIELD_NAME = "fpUpdate";
    public static final String CHANGE_FP_METHOD_FIELD_NAME = "change_fp_method";
    public static final String RENEW_FP_PRODUCT_FIELD_NAME = "renew_fp_product";
    public static final String FAMILY_PLANNING_METHOD_CHANGE_DATE_FIELD_NAME = "familyPlanningMethodChangeDate";
    public static final String NOT_CLOSED = "0";

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

        EligibleCouple couple = findByCaseID(caseId);
        if (couple == null) {
            return;
        }

        addTimelineEventsForFPRelatedChanges(couple, details);

        ContentValues valuesToUpdate = new ContentValues();
        valuesToUpdate.put(DETAILS_COLUMN, new Gson().toJson(details));
        database.update(EC_TABLE_NAME, valuesToUpdate, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public List<EligibleCouple> allEligibleCouples() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(EC_TABLE_NAME, EC_TABLE_COLUMNS, IS_OUT_OF_AREA_COLUMN + " = ? AND " + IS_CLOSED_COLUMN + " = ?", new String[]{"false", NOT_CLOSED}, null, null, null, null);
        return readAllEligibleCouples(cursor);
    }

    public List<EligibleCouple> findByCaseIDs(String... caseIds) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s IN (%s)", EC_TABLE_NAME, CASE_ID_COLUMN, insertPlaceholdersForInClause(caseIds.length)), caseIds);
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

    public long count() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + EC_TABLE_NAME + " WHERE " + IS_OUT_OF_AREA_COLUMN + " = 'false' and " +
                IS_CLOSED_COLUMN + " = " + NOT_CLOSED, new String[0]);
    }

    public List<String> villages() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(true, EC_TABLE_NAME, new String[]{VILLAGE_NAME_COLUMN}, IS_OUT_OF_AREA_COLUMN + " = ? AND " + IS_CLOSED_COLUMN + " = ?", new String[]{"false", NOT_CLOSED}, null, null, null, null);
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
        markAsClosed(caseId);
    }

    private void markAsClosed(String caseId) {
        ContentValues values = new ContentValues();
        values.put(IS_CLOSED_COLUMN, true);
        masterRepository.getWritableDatabase().update(EC_TABLE_NAME, values, CASE_ID_COLUMN + " = ?", new String[]{caseId});
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
        values.put(IS_CLOSED_COLUMN, eligibleCouple.isClosed());
        return values;
    }

    private List<EligibleCouple> readAllEligibleCouples(Cursor cursor) {
        cursor.moveToFirst();
        List<EligibleCouple> eligibleCouples = new ArrayList<EligibleCouple>();
        while (!cursor.isAfterLast()) {
            EligibleCouple eligibleCouple = new EligibleCouple(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    new Gson().<Map<String, String>>fromJson(cursor.getString(7), new TypeToken<Map<String, String>>() {
                    }.getType())).setIsClosed(cursor.getInt(8) != 0);
            if (Boolean.valueOf(cursor.getString(6)))
                eligibleCouple.asOutOfArea();
            eligibleCouples.add(eligibleCouple);
            cursor.moveToNext();
        }
        cursor.close();
        return eligibleCouples;
    }

    private void addTimelineEventsForFPRelatedChanges(EligibleCouple couple, Map<String, String> details) {
        if (wasFPMethodChanged(details)) {
            timelineEventRepository.add(forChangeOfFPMethod(couple.caseId(), couple.details().get(CURRENT_FP_METHOD_FIELD_NAME), details.get(CURRENT_FP_METHOD_FIELD_NAME), details.get(FAMILY_PLANNING_METHOD_CHANGE_DATE_FIELD_NAME)));
        } else if (wasFPproductRenewed(details)) {
            TimelineEvent timelineEventForRenew = FPMethod.tryParse(details.get(CURRENT_FP_METHOD_FIELD_NAME), FPMethod.NONE).getTimelineEventForRenew(couple.caseId(), details);
            if (timelineEventForRenew != null)
                timelineEventRepository.add(timelineEventForRenew);
        }
    }

    private boolean wasFPproductRenewed(Map<String, String> details) {
        return details.containsKey(FP_UPDATE_FIELD_NAME) && details.get(FP_UPDATE_FIELD_NAME).equals(RENEW_FP_PRODUCT_FIELD_NAME);
    }

    private boolean wasFPMethodChanged(Map<String, String> details) {
        return details.containsKey(FP_UPDATE_FIELD_NAME) && details.get(FP_UPDATE_FIELD_NAME).equals(CHANGE_FP_METHOD_FIELD_NAME);
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }
}
