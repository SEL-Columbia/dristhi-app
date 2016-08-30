package org.ei.opensrp.cursoradapter;

import org.apache.commons.lang3.StringUtils;
import org.ei.opensrp.commonregistry.CommonFtsObject;

import java.util.List;

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
        Selectquery = "SELECT id AS _id";
        for(int i = 0;i<columns.length;i++){
            Selectquery= Selectquery + " , " + columns[i];
        }
        Selectquery= Selectquery+ " FROM " + tablename;
        Selectquery = Selectquery+ " LEFT JOIN alerts ";
        Selectquery = Selectquery+ " ON "+ tablename +".id = alerts.caseID";
        if(condition != null){
            Selectquery= Selectquery+ " WHERE " + condition + " AND";
        }
        Selectquery= Selectquery+ " WHERE " + "alerts.scheduleName = '" + AlertName + "' ";
        Selectquery = Selectquery + "ORDER BY CASE WHEN alerts.status = 'urgent' THEN '1'\n" +
                "WHEN alerts.status = 'upcoming' THEN '2'\n" +
                "WHEN alerts.status = 'normal' THEN '3'\n" +
                "WHEN alerts.status = 'expired' THEN '4'\n" +
                "WHEN alerts.status is Null THEN '5'\n" +
                "Else alerts.status END ASC";
        return Selectquery;
    }
    public String queryForCountOnRegisters(String tablename,String condition){
        String Selectquery = "SELECT COUNT (*) ";
        Selectquery= Selectquery+ " FROM " + tablename;
        if(condition != null){
            Selectquery= Selectquery+ " WHERE " + condition ;
        }
        return Selectquery;
    }
    public String addlimitandOffset(String selectquery,int limit,int offset){
        return selectquery + " LIMIT " +offset+","+limit;
    }
    public String limitandOffset(int limit,int offset){
        return Selectquery + " LIMIT " +offset+","+limit;
    }
    public  String Endquery(String selectquery){
        return selectquery+";";
    }
    public String SelectInitiateMainTable(String tablename,String [] columns){
        Selectquery = "SELECT id AS _id";
        for(int i = 0;i<columns.length;i++){
            Selectquery= Selectquery + " , " + columns[i];
        }
        Selectquery= Selectquery+ " FROM " + tablename;
        return Selectquery;
    }
    public String SelectInitiateMainTableCounts(String tablename){
        Selectquery = "SELECT COUNT(*)";
        Selectquery= Selectquery+ " FROM " + tablename;
        return Selectquery;
    }
    public String mainCondition(String condition){
        Selectquery= Selectquery+ " WHERE " + condition ;
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
        Selectquery = Selectquery+ " ON "+ tablename +".id = alerts.caseID AND  alerts.scheduleName = '"+alertname+"'" ;
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

    public String toStringFts(List<String> ids, String idColumn, String sort){
        String res = Selectquery;

        String whereOrAnd = " WHERE";
        if(Selectquery.contains("WHERE")){
            whereOrAnd = " AND";
        }

        if(ids.isEmpty()){
            res += String.format(whereOrAnd + " %s IN () ", idColumn);;
        }else {
            String joinedIds = "'" + StringUtils.join(ids, "','") + "'";
            res += String.format(whereOrAnd + " %s IN (%s) ", idColumn, joinedIds);

            if (StringUtils.isNotBlank(sort)) {
                res += " ORDER BY CASE " + idColumn;
                for (int i = 0; i < ids.size(); i++) {
                    res += " WHEN '" + ids.get(i) + "' THEN " + i;
                }
                res += " END ";

            }
        }

        return res;
    }

    public String searchQueryFts(String tablename, String searchJoinTable, String searchFilter, String sort, int limit, int offset){
        String query = "SELECT " + CommonFtsObject.idColumn + " FROM " + CommonFtsObject.searchTableName(tablename)  + phraseClause(searchJoinTable, searchFilter) + orderByClause(sort) + limitClause(limit, offset);
        return query;
    }


    public String countQueryFts(String tablename, String searchJoinTable, String searchFilter){
        String countQuery = "SELECT COUNT(*) FROM " + CommonFtsObject.searchTableName(tablename)  + phraseClause(searchJoinTable, searchFilter);
        return countQuery;
    }

    private String phraseClause(String joinTable, String phrase){
        String phraseClause = "";
        if(StringUtils.isNotBlank(phrase)){
            phraseClause = " WHERE " + CommonFtsObject.phraseColumnName + " MATCH '" + phrase + "*'";
            if(StringUtils.isNotBlank(joinTable)){
                phraseClause += " OR " + CommonFtsObject.idColumn + " IN ( SELECT " + CommonFtsObject.relationalIdColumn + " FROM " + CommonFtsObject.searchTableName(joinTable) + " WHERE " + CommonFtsObject.phraseColumnName + " MATCH '" + phrase + "*' )";
            }
        }
        return phraseClause;
    }

    private String orderByClause(String sort){
        if(StringUtils.isNotBlank(sort)){
            if(!sort.contains("alerts")) {
                return " ORDER BY " + sort;
            }
        }
        return "";
    }

    private String limitClause(int limit, int offset){
        return " LIMIT " +  offset + "," + limit;
    }
}
