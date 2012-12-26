package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.DatabaseUtils;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.repeat;

public class ChildRepository extends DrishtiRepository {
    private static final String CHILD_SQL = "CREATE TABLE child(caseID VARCHAR PRIMARY KEY, motherCaseId VARCHAR, thaayiCardNumber VARCHAR, dateOfBirth VARCHAR, gender VARCHAR, details VARCHAR)";
    private static final String CHILD_TABLE_NAME = "child";
    private static final String CASE_ID_COLUMN = "caseID";
    private static final String MOTHER_CASEID_COLUMN = "motherCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String DATE_OF_BIRTH_COLUMN = "dateOfBirth";
    private static final String GENDER_COLUMN = "gender";
    private static final String DETAILS_COLUMN = "details";
    private static final String[] CHILD_TABLE_COLUMNS = {CASE_ID_COLUMN, MOTHER_CASEID_COLUMN, THAAYI_CARD_COLUMN, DATE_OF_BIRTH_COLUMN, GENDER_COLUMN, DETAILS_COLUMN};

    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    public ChildRepository(TimelineEventRepository timelineEventRepository, AlertRepository alertRepository) {
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(CHILD_SQL);
    }

    public void addChild(Child child) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(CHILD_TABLE_NAME, null, createValuesFor(child));
        timelineEventRepository.add(TimelineEvent.forChildBirthInChildProfile(child.caseId(), child.dateOfBirth(), child.detailsAsMap()));
    }

    public List<Child> all() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAll(cursor);
    }

    public long count() {
        return DatabaseUtils.longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + CHILD_TABLE_NAME, new String[0]);
    }

    public void close(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        database.delete(CHILD_TABLE_NAME, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void closeAllCasesForMother(String motherCaseId) {
        for (Child child : findByMotherCaseId(motherCaseId)) {
            close(child.caseId());
        }
    }

    public Child find(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, CASE_ID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        List<Child> children = readAll(cursor);

        if (children.isEmpty()) {
            return null;
        }
        return children.get(0);
    }

    public List<Child> findChildrenByCaseIds(String... caseIds) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s IN (%s)", CHILD_TABLE_NAME, CASE_ID_COLUMN, insertPlaceholdersForInClause(caseIds.length)), caseIds);
        return readAll(cursor);
    }

    public void updateDetails(String caseId, Map<String, String> details) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DETAILS_COLUMN, new Gson().toJson(details));
        database.update(CHILD_TABLE_NAME, values, CASE_ID_COLUMN + " = ?", new String[]{caseId});
    }

    private List<Child> findByMotherCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, MOTHER_CASEID_COLUMN + " = ?", new String[]{caseId}, null, null, null, null);
        return readAll(cursor);
    }

    private ContentValues createValuesFor(Child child) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, child.caseId());
        values.put(MOTHER_CASEID_COLUMN, child.motherCaseId());
        values.put(THAAYI_CARD_COLUMN, child.thaayiCardNumber());
        values.put(DATE_OF_BIRTH_COLUMN, child.dateOfBirth());
        values.put(GENDER_COLUMN, child.gender());
        values.put(DETAILS_COLUMN, new Gson().toJson(child.details()));
        return values;
    }

    private List<Child> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Child> children = new ArrayList<Child>();
        while (!cursor.isAfterLast()) {
            children.add(new Child(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    new Gson().<Map<String, String>>fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {}.getType())));
            cursor.moveToNext();
        }
        cursor.close();
        return children;
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }
}
