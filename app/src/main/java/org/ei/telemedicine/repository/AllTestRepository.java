package org.ei.telemedicine.repository;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.TestDomain;

public class AllTestRepository {
    private TestRepository test_repository;
    private final TimelineEventRepository timelineEventRepository;
    private final AlertRepository alertRepository;

    public AllTestRepository(TestRepository test_repository,
                             AlertRepository alertRepository,
                             TimelineEventRepository timelineEventRepository) {
        this.test_repository = test_repository;
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;

    }

//    public List<Mother> readAllMothers() {
//        Cursor cursor = test_repository.allmotherData();
//        cursor.moveToFirst();
//        List<Mother> mothers = new ArrayList<Mother>();
//        while (!cursor.isAfterLast()) {
//            Map<String, String> details = new Gson().fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {
//            }.getType());
//
//            mothers.add(new Mother(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4))
//                    .withDetails(details)
//                    .setIsClosed(Boolean.valueOf(cursor.getString(6)))
//                    .withType(cursor.getString(cursor.getColumnIndex("type"))));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return mothers;
//    }

    public int getChildCount() {
        return 0;
    }

}
