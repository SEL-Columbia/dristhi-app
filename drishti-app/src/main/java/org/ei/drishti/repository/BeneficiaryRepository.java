package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.BeneficiaryStatus;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.domain.BeneficiaryStatus.BORN;

public class BeneficiaryRepository extends DrishtiRepository {
    private static final String BENEFICIARY_SQL = "CREATE TABLE beneficiary(caseID VARCHAR, thaayiCardNumber VARCHAR, ecCaseId VARCHAR, status VARCHAR, referenceDate VARCHAR)";
    private static final String BENEFICIARY_TABLE_NAME = "beneficiary";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String EC_CASEID_COLUMN = "ecCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String STATUS_COLUMN = "status";
    private static final String REF_DATE_COLUMN = "referenceDate";
    private static final String[] BENEFICIARY_TABLE_COLUMNS = {CASE_ID_COLUMN, EC_CASEID_COLUMN, THAAYI_CARD_COLUMN, STATUS_COLUMN, REF_DATE_COLUMN};

    public void addMother(Beneficiary beneficiary) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(BENEFICIARY_TABLE_NAME, null, createValuesFor(beneficiary));
    }

    public void updateDeliveryStatus(String caseId, String status) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        ContentValues valuesToBeUpdated = new ContentValues();
        valuesToBeUpdated.put(STATUS_COLUMN, status);

        database.update(BENEFICIARY_TABLE_NAME, valuesToBeUpdated, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void addChild(String caseId, String referenceDate, String motherCaseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        ContentValues valuesToBeUpdated = new ContentValues();
        valuesToBeUpdated.put(CASE_ID_COLUMN, caseId);
        valuesToBeUpdated.put(STATUS_COLUMN, BORN.value());
        valuesToBeUpdated.put(REF_DATE_COLUMN, referenceDate);

        database.update(BENEFICIARY_TABLE_NAME, valuesToBeUpdated, CASE_ID_COLUMN + " = ?", new String[]{motherCaseId});
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(BENEFICIARY_SQL);
    }

    public List<Beneficiary> allBeneficiaries() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    public List<Beneficiary> findByECCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(BENEFICIARY_TABLE_NAME, BENEFICIARY_TABLE_COLUMNS, EC_CASEID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        return readAllBeneficiaries(cursor);
    }

    private ContentValues createValuesFor(Beneficiary beneficiary) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, beneficiary.caseId());
        values.put(EC_CASEID_COLUMN, beneficiary.ecCaseId());
        values.put(THAAYI_CARD_COLUMN, beneficiary.thaayiCardNumber());
        values.put(STATUS_COLUMN, beneficiary.status().value());
        values.put(REF_DATE_COLUMN, beneficiary.referenceDate());
        return values;
    }

    private List<Beneficiary> readAllBeneficiaries(Cursor cursor) {
        cursor.moveToFirst();
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        while (!cursor.isAfterLast()) {
            beneficiaries.add(new Beneficiary(cursor.getString(0), cursor.getString(1), cursor.getString(2), BeneficiaryStatus.from(cursor.getString(3)), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        return beneficiaries;

    }
}
