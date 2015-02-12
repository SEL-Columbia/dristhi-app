package org.ei.drishti.person;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.DrishtiRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.text.MessageFormat.format;
import static net.sqlcipher.DatabaseUtils.longForQuery;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.repeat;

public class PersonRepository extends DrishtiRepository {
    private static final String person_SQL = "CREATE TABLE eligible_couple(id VARCHAR PRIMARY KEY,details VARCHAR)";
    public static final String ID_COLUMN = "id";
    public static final String DETAILS_COLUMN = "details";
    public static final String person_TABLE_NAME = "person";
    public static final String[] person_TABLE_COLUMNS = new String[]{ID_COLUMN, DETAILS_COLUMN};


    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(person_SQL);
    }

    public void add(Person person) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(person_TABLE_NAME, null, createValuesFor(person));
    }

    public void updateDetails(String caseId, Map<String, String> details) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        Person person = findByCaseID(caseId);
        if (person == null) {
            return;
        }

        ContentValues valuesToUpdate = new ContentValues();
        valuesToUpdate.put(DETAILS_COLUMN, new Gson().toJson(details));
        database.update(person_TABLE_NAME, valuesToUpdate, ID_COLUMN + " = ?", new String[]{caseId});
    }

    public void mergeDetails(String caseId, Map<String, String> details) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        Person person = findByCaseID(caseId);
        if (person == null) {
            return;
        }

        Map<String, String> mergedDetails = new HashMap<String, String>(person.getDetails());
        mergedDetails.putAll(details);
        ContentValues valuesToUpdate = new ContentValues();
        valuesToUpdate.put(DETAILS_COLUMN, new Gson().toJson(mergedDetails));
        database.update(person_TABLE_NAME, valuesToUpdate, ID_COLUMN + " = ?", new String[]{caseId});
    }

    public List<Person> allPerson() {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(person_TABLE_NAME, person_TABLE_COLUMNS, null, null, null, null, null, null);
        return readAllPerson(cursor);
    }

    public List<Person> findByCaseIDs(String... caseIds) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE %s IN (%s)", person_TABLE_NAME, ID_COLUMN,
                insertPlaceholdersForInClause(caseIds.length)), caseIds);
        return readAllPerson(cursor);
    }

    public Person findByCaseID(String caseId) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(person_TABLE_NAME, person_TABLE_COLUMNS, ID_COLUMN + " = ?", new String[]{caseId},
                null, null, null, null);
        List<Person> persons = readAllPerson(cursor);
        if (persons.isEmpty()) {
            return null;
        }
        return persons.get(0);
    }

    public long count() {
        return longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + person_TABLE_NAME
                , new String[0]);
    }



    public void close(String caseId) {
//        ContentValues values = new ContentValues();
//        masterRepository.getWritableDatabase().update(EC_TABLE_NAME, values, ID_COLUMN + " = ?", new String[]{caseId});
    }

    private ContentValues createValuesFor(Person person) {
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, person.getCaseId());
        values.put(DETAILS_COLUMN, new Gson().toJson(person.getDetails()));
        return values;
    }

    private List<Person> readAllPerson(Cursor cursor) {
        cursor.moveToFirst();
        List<Person> persons = new ArrayList<Person>();
        while (!cursor.isAfterLast()) {
            Person person = new Person(cursor.getString(0),new Gson().<Map<String, String>>fromJson(cursor.getString(7), new TypeToken<Map<String, String>>() {
                    }.getType()));

            persons.add(person);
            cursor.moveToNext();
        }
        cursor.close();
        return persons;
    }

    private String insertPlaceholdersForInClause(int length) {
        return repeat("?", ",", length);
    }





    private List<Map<String, String>> readDetailsList(Cursor cursor) {
        cursor.moveToFirst();
        List<Map<String, String>> detailsList = new ArrayList<Map<String, String>>();
        while (!cursor.isAfterLast()) {
            String detailsJSON = cursor.getString(0);
            detailsList.add(new Gson().<Map<String, String>>fromJson(detailsJSON, new TypeToken<HashMap<String, String>>() {
            }.getType()));
            cursor.moveToNext();
        }
        cursor.close();
        return detailsList;
    }
}
