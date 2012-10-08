package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.join;
import static org.ei.drishti.repository.EligibleCoupleRepository.EC_TABLE_COLUMNS;
import static org.ei.drishti.repository.EligibleCoupleRepository.EC_TABLE_NAME;

public class MotherRepository extends DrishtiRepository {
    private static final String MOTHER_SQL = "CREATE TABLE mother(caseID VARCHAR PRIMARY KEY, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, type VARCHAR, referenceDate VARCHAR, details VARCHAR)";
    private static final String MOTHER_TYPE_INDEX_SQL = "CREATE INDEX mother_type_index ON mother(type);";
    private static final String MOTHER_REFERENCE_DATE_INDEX_SQL = "CREATE INDEX mother_referenceDate_index ON mother(referenceDate);";
    private static final String MOTHER_TABLE_NAME = "mother";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String TYPE_COLUMN = "type";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String DETAILS_COLUMN = "details";
    private static final String[] MOTHER_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, TYPE_COLUMN, REF_DATE_COLUMN, DETAILS_COLUMN};

    private static final String TYPE_ANC = "ANC";
    private static final String TYPE_PNC = "PNC";

    private ChildRepository childRepository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    public MotherRepository(ChildRepository childRepository, TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.childRepository = childRepository;
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(MOTHER_SQL);
        database.execSQL(MOTHER_TYPE_INDEX_SQL);
        database.execSQL(MOTHER_REFERENCE_DATE_INDEX_SQL);
    }

    public void add(Mother mother) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(MOTHER_TABLE_NAME, null, createValuesFor(mother, TYPE_ANC));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy(mother.caseId(), mother.referenceDate()));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancyForEC(mother.ecCaseId(), mother.thaayiCardNumber(), mother.referenceDate()));
    }

    public void switchToPNC(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        ContentValues motherValuesToBeUpdated = new ContentValues();
        motherValuesToBeUpdated.put(TYPE_COLUMN, TYPE_PNC);

        database.update(MOTHER_TABLE_NAME, motherValuesToBeUpdated, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public List<Mother> allANCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{TYPE_ANC}, null, null, null, null);
        return readAll(cursor);
    }

    public List<Mother> allPNCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{TYPE_PNC}, null, null, null, null);
        return readAll(cursor);
    }

    public long ancCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_ANC});
    }

    public long pncCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_PNC});
    }

    public void updateDetails(String caseId, Map<String, String> details) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("details", new Gson().toJson(details));
        database.update(MOTHER_TABLE_NAME, values, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public Mother find(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<Mother> mothers = readAll(cursor);

        if (mothers.isEmpty()) {
            return null;
        }
        return mothers.get(0);
    }

    public void closeAllCasesForEC(String ecCaseId) {
        List<Mother> mothers = findAllCasesForEC(ecCaseId);
        for (Mother mother : mothers) {
            close(mother.caseId());
        }
    }

    public List<Mother> findAllCasesForEC(String ecCaseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, EC_CASEID_COLUMN + " = ?", new String[]{ecCaseId}, null, null, null, null);
        return readAll(cursor);
    }

    public List<Pair<Mother, EligibleCouple>> allANCsWithEC() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + tableColumnsForQuery(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS) + ", " + tableColumnsForQuery(EC_TABLE_NAME, EC_TABLE_COLUMNS) +
                " FROM " + MOTHER_TABLE_NAME + ", " + EC_TABLE_NAME +
                " WHERE " + TYPE_COLUMN + "='" + TYPE_ANC +
                "' AND " + MOTHER_TABLE_NAME + "." + EC_CASEID_COLUMN + " = " + EC_TABLE_NAME + "." + EligibleCoupleRepository.CASE_ID_COLUMN, null);
        return readAllANCsWithEC(cursor);
    }

    public void close(String caseId) {
        childRepository.closeAllCasesForMother(caseId);
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        masterRepository.getWritableDatabase().delete(MOTHER_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    private ContentValues createValuesFor(Mother mother, String type) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, mother.caseId());
        values.put(EC_CASEID_COLUMN, mother.ecCaseId());
        values.put(THAAYI_CARD_COLUMN, mother.thaayiCardNumber());
        values.put(TYPE_COLUMN, type);
        values.put(REF_DATE_COLUMN, mother.referenceDate());
        values.put(DETAILS_COLUMN, new Gson().toJson(mother.details()));
        return values;
    }

    private List<Mother> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Mother> mothers = new ArrayList<Mother>();
        while (!cursor.isAfterLast()) {
            Map<String, String> details = new Gson().fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {
            }.getType());

            mothers.add(new Mother(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4)).withDetails(details));
            cursor.moveToNext();
        }
        cursor.close();
        return mothers;
    }

    private List<Pair<Mother, EligibleCouple>> readAllANCsWithEC(Cursor cursor) {
        cursor.moveToFirst();
        List<Pair<Mother, EligibleCouple>> ancsWithEC = new ArrayList<Pair<Mother, EligibleCouple>>();
        while (!cursor.isAfterLast()) {
            Mother mother = new Mother(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4))
                    .withDetails(new Gson().<Map<String, String>>fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {}.getType()));
            EligibleCouple eligibleCouple = new EligibleCouple(cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                    new Gson().<Map<String, String>>fromJson(cursor.getString(13), new TypeToken<Map<String, String>>() {}.getType()));
            if (Boolean.valueOf(cursor.getString(12)))
                eligibleCouple.asOutOfArea();

            ancsWithEC.add(Pair.of(mother, eligibleCouple));
            cursor.moveToNext();
        }
        cursor.close();
        return ancsWithEC;
    }

    private String tableColumnsForQuery(String tableName, String[] tableColumns) {
        return join(prepend(tableColumns, tableName + "."), ", ");
    }

    private String[] prepend(String[] input, String textToPrepend) {
        String[] output = new String[input.length];
        for (int index = 0; index < input.length; index++) {
            output[index] = textToPrepend + input[index];
        }
        return output;
    }
}
