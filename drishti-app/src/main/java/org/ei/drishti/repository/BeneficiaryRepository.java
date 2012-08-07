package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryRepository extends DrishtiRepository {
    private static final String CHILD_SQL = "CREATE TABLE child(caseID VARCHAR, thaayiCardNumber VARCHAR, motherCaseId VARCHAR, referenceDate VARCHAR)";
    private static final String CHILD_TABLE_NAME = "child";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String MOTHER_CASEID_COLUMN = "motherCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String[] CHILD_TABLE_COLUMNS = {CASE_ID_COLUMN, MOTHER_CASEID_COLUMN, THAAYI_CARD_COLUMN, REF_DATE_COLUMN};

    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    public BeneficiaryRepository(TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CHILD_SQL);
    }

    public void addChildForMother(Mother mother, String caseId, String referenceDate, String gender) {
        if (mother == null) {
            return;
        }

        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(CHILD_TABLE_NAME, null, createValuesFor(new Beneficiary(caseId, mother.caseId(), mother.thaayiCardNumber(), referenceDate)));
        timelineEventRepository.add(TimelineEvent.forChildBirth(caseId, referenceDate, gender));
    }

    public List<Beneficiary> all() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public long childCount() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + CHILD_TABLE_NAME, new String[0]);
    }

    public void close(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        database.delete(CHILD_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void closeAllCasesForMother(String motherCaseId) {
        for (Beneficiary beneficiary : findByMotherCaseId(motherCaseId)) {
            close(beneficiary.caseId());
        }
    }

    public Beneficiary findByCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<Beneficiary> beneficiaries = readAllBeneficiaries(cursor);

        if (beneficiaries.isEmpty()) {
            return null;
        }
        return beneficiaries.get(0);
    }

    private List<Beneficiary> findByMotherCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, MOTHER_CASEID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    private ContentValues createValuesFor(Beneficiary beneficiary) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, beneficiary.caseId());
        values.put(MOTHER_CASEID_COLUMN, beneficiary.ecCaseId());
        values.put(THAAYI_CARD_COLUMN, beneficiary.thaayiCardNumber());
        values.put(REF_DATE_COLUMN, beneficiary.referenceDate());
        return values;
    }

    private List<Beneficiary> readAllBeneficiaries(Cursor cursor) {
        cursor.moveToFirst();
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        while (!cursor.isAfterLast()) {
            beneficiaries.add(new Beneficiary(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return beneficiaries;
    }
}
