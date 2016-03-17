package org.ei.opensrp.cursoradapter;

/**
 * Created by raihan on 3/17/16.
 */
public class SmartRegisterQueryBuilder {
    public static String queryForRegisterSortBasedOnRegister(String tablename,String [] columns){
        String Selectquery = "Select id as _id";
        for(int i = 0;i<columns.length;i++){
            Selectquery= Selectquery + " , " + columns[i];
        }

        return "";
    }
}
