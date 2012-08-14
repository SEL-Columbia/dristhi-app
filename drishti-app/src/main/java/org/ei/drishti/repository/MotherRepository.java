package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;

public class MotherRepository extends DrishtiRepository {
    private static final String MOTHER_SQL = "CREATE TABLE mother(caseID VARCHAR, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, type VARCHAR, referenceDate VARCHAR, isHighRisk VARCHAR, deliveryPlace VARCHAR)";
    private static final String MOTHER_TABLE_NAME = "mother";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String TYPE_COLUMN = "type";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String IS_HIGH_RISK_COLUMN = "isHighRisk";
    private static final String DELIVERY_PLACE_COLUMN = "deliveryPlace";
    private static final String[] MOTHER_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, TYPE_COLUMN, REF_DATE_COLUMN, IS_HIGH_RISK_COLUMN, DELIVERY_PLACE_COLUMN};

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
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(MOTHER_SQL);
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
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ? AND date(" + REF_DATE_COLUMN + ", 'start of day', '+280 days') > date('now')", new String[]{TYPE_ANC}, null, null, null, null);
        return readAll(cursor);
    }

    public List<Mother> allPNCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ? OR date(" + REF_DATE_COLUMN + ", 'start of day', '+280 days') <= date('now')", new String[]{TYPE_PNC}, null, null, null, null);
        return readAll(cursor);
    }

    public long ancCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ? AND date(" + REF_DATE_COLUMN + ", 'start of day', '+280 days') > date('now')", new String[]{TYPE_ANC});
    }

    public long pncCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ? OR date(" + REF_DATE_COLUMN + ", 'start of day', '+280 days') <= date('now')", new String[]{TYPE_PNC});
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

    private List<Mother> findAllCasesForEC(String ecCaseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, EC_CASEID_COLUMN + " = ?", new String[]{ecCaseId}, null, null, null, null);
        return readAll(cursor);
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
        values.put(IS_HIGH_RISK_COLUMN, String.valueOf(mother.isHighRisk()));
        values.put(DELIVERY_PLACE_COLUMN, mother.deliveryPlace());
        return values;
    }

    private List<Mother> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Mother> mothers = new ArrayList<Mother>();
        while (!cursor.isAfterLast()) {
            mothers.add(new Mother(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4)).withExtraDetails(Boolean.parseBoolean(cursor.getString(5)), cursor.getString(6)));
            cursor.moveToNext();
        }
        cursor.close();
        return mothers;
    }
}
