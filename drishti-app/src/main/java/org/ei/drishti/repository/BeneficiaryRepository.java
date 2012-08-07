package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryRepository extends DrishtiRepository {
    private static final String BENEFICIARY_SQL = "CREATE TABLE beneficiary(caseID VARCHAR, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, type VARCHAR, referenceDate VARCHAR)";
    private static final String BENEFICIARY_TABLE_NAME = "beneficiary";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String TYPE_COLUMN = "type";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String[] BENEFICIARY_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, TYPE_COLUMN, REF_DATE_COLUMN};

    private static final String TYPE_PNC = "PNC";
    private static final String TYPE_ANC = "ANC";
    private static final String TYPE_CHILD = "CHILD";
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    public BeneficiaryRepository(TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(BENEFICIARY_SQL);
    }

    public void addMother(Beneficiary beneficiary) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(BENEFICIARY_TABLE_NAME, null, createValuesFor(beneficiary, TYPE_ANC));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy(beneficiary.ecCaseId(), beneficiary.referenceDate()));
    }

    public void addChild(String caseId, String referenceDate, String motherCaseId, String gender) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        Beneficiary motherCase = findByCaseId(motherCaseId);
        if (motherCase == null) {
            return;
        }

        ContentValues motherValuesToBeUpdated = new ContentValues();
        motherValuesToBeUpdated.put(TYPE_COLUMN, TYPE_PNC);

        database.insert(BENEFICIARY_TABLE_NAME, null, createValuesFor(new Beneficiary(caseId, motherCase.ecCaseId(), motherCase.thaayiCardNumber(), referenceDate), TYPE_CHILD));
        database.update(BENEFICIARY_TABLE_NAME, motherValuesToBeUpdated, CASE_ID_COLUMN + " = ?", new String[]{motherCaseId});
        timelineEventRepository.add(TimelineEvent.forChildBirth(motherCase.ecCaseId(), referenceDate, gender));
    }

    public List<Beneficiary> allBeneficiaries() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public long ancCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + BENEFICIARY_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_ANC});
    }

    public long pncCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + BENEFICIARY_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_PNC});
    }

    public long childCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + BENEFICIARY_TABLE_NAME + " WHERE " + TYPE_COLUMN + " = ?", new String[]{TYPE_CHILD});
    }

    public List<Beneficiary> findByECCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, EC_CASEID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public List<Beneficiary> allANCs() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{TYPE_ANC}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public void close(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        database.delete(BENEFICIARY_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void closeAllCasesForEC(String ecCaseId) {
        for (Beneficiary beneficiary : findByECCaseId(ecCaseId)) {
            close(beneficiary.caseId());
        }
    }

    public Beneficiary findByCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<Beneficiary> beneficiaries = readAllBeneficiaries(cursor);

        if (beneficiaries.isEmpty()) {
            return null;
        }
        return beneficiaries.get(0);
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
