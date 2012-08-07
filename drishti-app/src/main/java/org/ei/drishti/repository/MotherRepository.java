package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;

public class MotherRepository extends DrishtiRepository {
    private static final String MOTHER_SQL = "CREATE TABLE mother(caseID VARCHAR, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, type VARCHAR, referenceDate VARCHAR)";
    private static final String MOTHER_TABLE_NAME = "mother";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String TYPE_COLUMN = "type";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String[] MOTHER_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, TYPE_COLUMN, REF_DATE_COLUMN};

    private static final String TYPE_ANC = "ANC";
    private static final String TYPE_PNC = "PNC";
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    public MotherRepository(TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(MOTHER_SQL);
    }

    public void add(Beneficiary beneficiary) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(MOTHER_TABLE_NAME, null, createValuesFor(beneficiary, TYPE_ANC));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy(beneficiary.caseId(), beneficiary.referenceDate()));
    }

    public void switchToPNC(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        ContentValues motherValuesToBeUpdated = new ContentValues();
        motherValuesToBeUpdated.put(TYPE_COLUMN, TYPE_PNC);

        database.update(MOTHER_TABLE_NAME, motherValuesToBeUpdated, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public List<Beneficiary> allANCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{TYPE_ANC}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public List<Beneficiary> allPNCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{TYPE_PNC}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public long ancCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_ANC});
    }

    public long pncCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + MOTHER_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_PNC});
    }

    public Beneficiary find(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(MOTHER_TABLE_NAME, MOTHER_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<Beneficiary> beneficiaries = readAllBeneficiaries(cursor);

        if (beneficiaries.isEmpty()) {
            return null;
        }
        return beneficiaries.get(0);
    }

    public void close(String caseId) {
        masterRepository.getWritableDatabase().delete(MOTHER_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    private ContentValues createValuesFor(Beneficiary beneficiary, String type) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, beneficiary.caseId());
        values.put(EC_CASEID_COLUMN, beneficiary.ecCaseId());
        values.put(THAAYI_CARD_COLUMN, beneficiary.thaayiCardNumber());
        values.put(TYPE_COLUMN, type);
        values.put(REF_DATE_COLUMN, beneficiary.referenceDate());
        return values;
    }

    private List<Beneficiary> readAllBeneficiaries(Cursor cursor) {
        cursor.moveToFirst();
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        while (!cursor.isAfterLast()) {
            beneficiaries.add(new Beneficiary(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        return beneficiaries;
    }
}
