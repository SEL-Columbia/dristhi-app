package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static info.guardianproject.database.DatabaseUtils.longForQuery;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.repeat;

public class ChildRepository extends DrishtiRepository {
    private static final String CHILD_SQL = "CREATE TABLE child(id VARCHAR PRIMARY KEY, motherCaseId VARCHAR, thaayiCardNumber VARCHAR, dateOfBirth VARCHAR, gender VARCHAR, details VARCHAR, isClosed VARCHAR)";
    public static final String CHILD_TABLE_NAME = "child";
    private static final String CASE_ID_COLUMN = "id";
    private static final String MOTHER_CASEID_COLUMN = "motherCaseId";
    private static final String THAAYI_CARD_COLUMN = "thaayiCardNumber";
    private static final String DATE_OF_BIRTH_COLUMN = "dateOfBirth";
    private static final String GENDER_COLUMN = "gender";
    private static final String DETAILS_COLUMN = "details";
    private static final String IS_CLOSED_COLUMN = "isClosed";
    public static final String[] CHILD_TABLE_COLUMNS = {CASE_ID_COLUMN, MOTHER_CASEID_COLUMN, THAAYI_CARD_COLUMN, DATE_OF_BIRTH_COLUMN, GENDER_COLUMN, DETAILS_COLUMN, IS_CLOSED_COLUMN};
    public static final String NOT_CLOSED = "false";

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

    public void add(Child child) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(CHILD_TABLE_NAME, null, createValuesFor(child));
        timelineEventRepository.add(TimelineEvent.forChildBirthInChildProfile(child.caseId(), child.dateOfBirth(), child.detailsAsMap()));
    }

    public List<Child> all() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(CHILD_TABLE_NAME, CHILD_TABLE_COLUMNS, IS_CLOSED_COLUMN + " = ?", new String[]{NOT_CLOSED}, null, null, null, null);
        return readAll(cursor);
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

    public long count() {
        return longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + CHILD_TABLE_NAME + " WHERE " + IS_CLOSED_COLUMN + " = '" + NOT_CLOSED + "'", new String[0]);
    }

    public void close(String caseId) {
        alertRepository.deleteAllAlertsForCase(caseId);
        timelineEventRepository.deleteAllTimelineEventsForCase(caseId);
        markAsClosed(caseId);
    }

    private void markAsClosed(String caseId) {
        ContentValues values = new ContentValues();
        values.put(IS_CLOSED_COLUMN, TRUE.toString());
        masterRepository.getWritableDatabase().update(CHILD_TABLE_NAME, values, CASE_ID_COLUMN + " = ?", new String[]{caseId});
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
        values.put(IS_CLOSED_COLUMN, Boolean.toString(child.isClosed()));
        return values;
    }

    private List<Child> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Child> children = new ArrayList<Child>();
        while (!cursor.isAfterLast()) {
            children.add(new Child(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    new Gson().<Map<String, String>>fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {
                    }.getType())).setIsClosed(Boolean.valueOf(cursor.getString(6))));
            cursor.moveToNext();
        }
        cursor.close();
        return children;
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }
}
