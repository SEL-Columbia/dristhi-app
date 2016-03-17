package org.ei.opensrp.cursoradapter;

/**
 * Created by raihan on 3/17/16.
 */
public class SmartRegisterQueryBuilder {
    String Selectquery;
    public  String queryForRegisterSortBasedOnRegisterAndAlert(String tablename,String[]columns,String condition,String AlertName){
        Selectquery = "Select id as _id";
        for(int i = 0;i<columns.length;i++){
            Selectquery= Selectquery + " , " + columns[i];
        }
        Selectquery= Selectquery+ " From " + tablename;
        Selectquery = Selectquery+ " LEFT JOIN alerts ";
        Selectquery = Selectquery+ " ON "+ tablename +".id = alerts.caseID";
        if(condition != null){
            Selectquery= Selectquery+ " Where " + condition + " AND";
        }
        Selectquery= Selectquery+ " Where " + "alerts.scheduleName = '" + AlertName + "' ";
        Selectquery = Selectquery + "ORDER BY CASE WHEN alerts.status = 'urgent' THEN '1'\n" +
                "WHEN alerts.status = 'upcoming' THEN '2'\n" +
                "WHEN alerts.status = 'normal' THEN '3'\n" +
                "WHEN alerts.status = 'expired' THEN '4'\n" +
                "WHEN alerts.status is Null THEN '5'\n" +
                "Else alerts.status END ASC";
        return Selectquery;
    }
    public String queryForCountOnRegisters(String tablename,String condition){
        String Selectquery = "Select Count (*) ";
        Selectquery= Selectquery+ " From " + tablename;
        if(condition != null){
            Selectquery= Selectquery+ " Where " + condition ;
        }
        return Selectquery;
    }
    public String limitandOffset(int limit,int offset){
        return Selectquery + " Limit " +offset+","+limit;
    }
    public  String Endquery(String selectquery){
        return selectquery+";";
    }
}
