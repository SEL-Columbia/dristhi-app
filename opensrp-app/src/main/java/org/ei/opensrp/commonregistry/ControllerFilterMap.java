package org.ei.opensrp.commonregistry;

/**
 * Created by raihan on 2/8/16.
 */
public class ControllerFilterMap{
    public String filterkey;
    public  String filtervalue;
    public boolean filterbool;

    public ControllerFilterMap(String filterkey, String filtervalue, boolean filterbool) {
        this.filterkey = filterkey;
        this.filtervalue = filtervalue;
        this.filterbool = filterbool;
    }
}