package org.ei.opensrp.cursoradapter;

/**
 * Created by raihan on 3/17/16.
 */
public class SmartRegisterQueryBuilder {
    String Selectquery;

    public String getSelectquery() {
        return Selectquery;
    }

    public void setSelectquery(String selectquery) {
        Selectquery = selectquery;
    }

    public SmartRegisterQueryBuilder(String selectquery) {
        Selectquery = selectquery;
    }

    public SmartRegisterQueryBuilder() {
    }

    /*
            This method takes in @param tablename and columns other than ID. Any special conditions
            for sorting if required can also be added in condition string and if not you can pass null.
            Alertname is the name of the alert you would like to sort this by.
             */
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
    public String addlimitandOffset(String selectquery,int limit,int offset){
        return selectquery + " Limit " +offset+","+limit;
    }
    public String limitandOffset(int limit,int offset){
        return Selectquery + " Limit " +offset+","+limit;
    }
    public  String Endquery(String selectquery){
        return selectquery+";";
    }
    public String SelectInitiateMainTable(String tablename,String [] columns){
        Selectquery = "Select id as _id";
        for(int i = 0;i<columns.length;i++){
            Selectquery= Selectquery + " , " + columns[i];
        }
        Selectquery= Selectquery+ " From " + tablename;
        return Selectquery;
    }
    public String SelectInitiateMainTableCounts(String tablename){
        Selectquery = "Select Count(*)";
        Selectquery= Selectquery+ " From " + tablename;
        return Selectquery;
    }
    public String mainCondition(String condition){
        Selectquery= Selectquery+ " Where " + condition ;
        return Selectquery;
    }
    public String addCondition(String condition){
        Selectquery= Selectquery + condition ;
        return Selectquery;
    }
    public String orderbyCondition(String condition){
        Selectquery= Selectquery + " ORDER BY " + condition;
        return Selectquery;
    }
    public String joinwithALerts(String tablename,String alertname){
        Selectquery = Selectquery+ " LEFT JOIN alerts ";
        Selectquery = Selectquery+ " ON "+ tablename +".id = alerts.caseID and  alerts.scheduleName = '"+alertname+"'" ;
        return Selectquery;
    }
    public String joinwithALerts(String tablename){
        Selectquery = Selectquery+ " LEFT JOIN alerts ";
        Selectquery = Selectquery+ " ON "+ tablename +".id = alerts.caseID " ;
        return Selectquery;
    }
    @Override
    public String toString(){
        return Selectquery;
    }
}
