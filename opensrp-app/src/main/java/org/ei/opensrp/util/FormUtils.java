package org.ei.opensrp.util;

import android.content.Context;
import android.util.Xml;

import org.ei.opensrp.domain.SyncStatus;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by koros on 9/28/15.
 */
public class FormUtils {

    static FormUtils instance;
    Context mContext;
    org.ei.opensrp.Context theAppContext;

    public FormUtils(Context context){
        mContext = context;
        theAppContext = org.ei.opensrp.Context.getInstance();
    }

    public static FormUtils getInstance(Context ctx){
        if (instance == null){
            instance = new FormUtils(ctx);
        }
        return instance;
    }

    public FormSubmission generateFormSubmisionFromXMLString(String entity_id, String formData, String formName, Map<String, String> overrides) throws Exception{
        JSONObject formSubmission = XML.toJSONObject(formData);
        System.out.println(formSubmission);

        // use the form_definition.json to iterate through fields
        String formDefinitionJson = readFileFromAssetsFolder("www/form/" + formName + "/form_definition.json");
        JSONObject formDefinition = new JSONObject(formDefinitionJson);

        //String bindPath = formDefinition.getJSONObject("form").getString("bind_type");
        JSONObject fieldsDefinition = formDefinition.getJSONObject("form");
        JSONArray populatedFieldsArray = getPopulatedFieldsForArray(fieldsDefinition, entity_id, formSubmission, overrides);

        // replace all the fields in the form
        formDefinition.getJSONObject("form").put("fields", populatedFieldsArray);

        //get the subforms
        if (formDefinition.getJSONObject("form").has("sub_forms")){
            JSONObject subFormDefinition = formDefinition.getJSONObject("form").getJSONArray("sub_forms").getJSONObject(0);
            //get the bind path for the sub-form helps us to locate the node that holds the data in the corresponding data json
            String bindPath = subFormDefinition.getString("default_bind_path");
            String childTableName = subFormDefinition.getString("bind_type");

            //get the actual sub-form data
            JSONArray subForms = new JSONArray();
            Object subFormDataObject = getObjectAtPath(bindPath.split("/"), formSubmission);
            if(subFormDataObject instanceof JSONObject){
                JSONObject subFormData = (JSONObject)subFormDataObject;
                JSONArray subFormFields = getFieldsArrayForSubFormDefinition(subFormDefinition);
                List<String> ids = retrieveRelationalIdForSubForm(childTableName, entity_id);
                String relationalId = ids.isEmpty() ? null : ids.get(0);
                JSONObject subFormInstance = getFieldValuesForSubFormDefinition(subFormDefinition, relationalId, subFormData, overrides);
                JSONArray subFormInstances = new JSONArray();
                subFormInstances.put(0,subFormInstance);
                subFormDefinition.put("instances", subFormInstances);
                subFormDefinition.put("fields", subFormFields);
                subForms.put(0, subFormDefinition);
            }else if (subFormDataObject instanceof JSONArray){
                JSONArray subFormData = (JSONArray)subFormDataObject;
                JSONArray subFormFields = getFieldsArrayForSubFormDefinition(subFormDefinition);
                JSONArray subFormInstances = new JSONArray();
                //how do you tell which id originally belonged to a particular edited subform !!!!
                List<String> ids = retrieveRelationalIdForSubForm(childTableName, entity_id);
                for (int i = 0; i < subFormData.length(); i++){
                    String relationalId = ids.isEmpty() && ids.size() > i ? null : ids.get(i);
                    JSONObject subFormInstance = getFieldValuesForSubFormDefinition(subFormDefinition, relationalId, subFormData.getJSONObject(i), overrides);
                    subFormInstances.put(i,subFormInstance);
                }
                subFormDefinition.put("instances", subFormInstances);
                subFormDefinition.put("fields", subFormFields);
                subForms.put(0, subFormDefinition);
            }

            // replace the subforms field with real data
            formDefinition.getJSONObject("form").put("sub_forms", subForms);
        }

        String instanceId = generateRandomUUIDString();
        String entityId = retrieveIdForSubmission(formDefinition);
        String formDefinitionVersionString = formDefinition.getString("form_data_definition_version");

        String clientVersion = String.valueOf(new Date().getTime());
        String instance = formDefinition.toString();
        FormSubmission fs = new FormSubmission(instanceId, entityId, formName, instance, clientVersion, SyncStatus.PENDING, formDefinitionVersionString);
        return fs;
    }

    private List<String> retrieveRelationalIdForSubForm(String childTableName, String entityId) throws  Exception{
        List<String> ids = new ArrayList<String>();
        if (entityId != null){
            String sql = "select * from " + childTableName + " where relationalid='" + entityId + "'";
            String dbEntity = theAppContext.formDataRepository().queryList(sql);

            JSONArray entityJson = new JSONArray();
            if (dbEntity != null && !dbEntity.isEmpty()){
                entityJson = new JSONArray(dbEntity);
                for (int i = 0; i < entityJson.length(); i++){
                    if (entityJson.getJSONObject(i).has("id")){
                        ids.add(entityJson.getJSONObject(i).getString("id"));
                    }
                }
            }
        }
        return ids;
    }

    public String generateXMLInputForFormWithEntityId(String entityId, String formName, String overrides){
        try
        {
            //get the field overrides map
            JSONObject fieldOverrides = new JSONObject();
            if (overrides != null){
                fieldOverrides = new JSONObject(overrides);
                String overridesStr = fieldOverrides.getString("fieldOverrides");
                fieldOverrides = new JSONObject(overridesStr);
            }

            // use the form_definition.json to get the form mappings
            String formDefinitionJson = readFileFromAssetsFolder("www/form/" + formName + "/form_definition.json");
            JSONObject formDefinition = new JSONObject(formDefinitionJson);
            String bindPath = formDefinition.getJSONObject("form").getString("bind_type");

            String sql = "select * from " + bindPath + " where id='" + entityId + "'";
            String dbEntity = theAppContext.formDataRepository().queryUniqueResult(sql);

            JSONObject entityJson = new JSONObject();
            if (dbEntity != null && !dbEntity.isEmpty()){
                entityJson = new JSONObject(dbEntity);
            }

            //read the xml form model, the expected form model that is passed to the form mirrors it
            String formModelString = readFileFromAssetsFolder("www/form/" + formName + "/model.xml").replaceAll("\n"," ").replaceAll("\r", " ");
            InputStream is = new ByteArrayInputStream(formModelString.getBytes());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);

            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);

            //skip processing <model><instance>
            NodeList els = ((Element)document.getElementsByTagName("model").item(0)).getElementsByTagName("instance");
            Element el = (Element)els.item(0);
            NodeList entries = el.getChildNodes();
            int num = entries.getLength();
            for (int i = 0; i < num; i++) {
                Node n = entries.item(i);
                if (n instanceof Element){
                    Element node = (Element)n;
                    writeXML(node, serializer, fieldOverrides, formDefinition, entityJson);
                }
            }

            serializer.endDocument();

            String xml = writer.toString();
            //xml = xml.replaceAll("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>","");//56 !!!this ain't working
            xml = xml.substring(56);
            System.out.println(xml);

            return xml;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private void writeXML(Element node, XmlSerializer serializer, JSONObject fieldOverrides, JSONObject formDefinition, JSONObject entityJson){
        try {
            String nodeName = node.getNodeName();
            String entityId = entityJson.has("id") ? entityJson.getString("id") : null;

            serializer.startTag("", nodeName);

            //write the node value
            if (entityJson.has(nodeName)){
                serializer.text(entityJson.getString(nodeName));
            }

            //overwrite the node value with contents from overrides map
            if (fieldOverrides.has(nodeName)){
                serializer.text(fieldOverrides.getString(nodeName));
            }

            // write the xml attributes
            writeXMLAttributes(node, serializer);

            List<String> subFormNames = getSubFormNames(formDefinition);

            // get all child nodes
            NodeList entries = node.getChildNodes();
            int num = entries.getLength();
            for (int i = 0; i < num; i++) {
                if (entries.item(i) instanceof Element) {
                    Element child = (Element) entries.item(i);
                    String fieldName = child.getNodeName();

                    if(!subFormNames.isEmpty() && subFormNames.contains(fieldName))
                    {
                        /* its a subform element process it */
                        // get the subform definition
                        JSONArray subForms = formDefinition.getJSONObject("form").getJSONArray("sub_forms");
                        JSONObject subFormDefinition = retriveSubformDefinitionForBindPath(subForms, fieldName);
                        if (subFormDefinition != null){
                            String childTableName = subFormDefinition.getString("bind_type");
                            String sql = "select * from '" + childTableName + "' where relationalid = '" + entityId + "'";
                            String childTablesStr = theAppContext.formDataRepository().queryList(sql);
                            JSONArray childTables = new JSONArray(childTablesStr);
                            for (int k = 0; k < childTables.length(); k++){
                                JSONObject childEntityJson = childTables.getJSONObject(k);
                                writeXML(child, serializer, fieldOverrides, subFormDefinition, childEntityJson);
                            }
                        }
                    }
                    else
                    {
                        //its not a sub-form element write its value
                        serializer.startTag("", fieldName);
                        // write the xml attributes
                        writeXMLAttributes(child, serializer);
                        //write the node value
                        if (entityJson.has(fieldName)){
                            serializer.text(entityJson.getString(fieldName));
                        }

                        //overwrite the node value with contents from overrides map
                        if (fieldOverrides.has(fieldName)){
                            serializer.text(fieldOverrides.getString(fieldName));
                        }

                        serializer.endTag("", fieldName);
                    }
                }
            }

            serializer.endTag("", node.getNodeName());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Currently not used but, the method should retrieve the path of a given node,
     * useful when confirming if the current node has been properly mapped to its bind_path
     **/
    private String getXPath(Node node)
    {
        Node parent = node.getParentNode();
        if (parent == null) {
            return "/" + node.getNodeName();
        }
        return getXPath(parent) + "/";
    }

    private List<String> getSubFormNames(JSONObject formDefinition) throws Exception{
        List<String> subFormNames = new ArrayList<String>();
        if (formDefinition.has("form") && formDefinition.getJSONObject("form").has("sub_forms")){
            JSONArray subForms = formDefinition.getJSONObject("form").getJSONArray("sub_forms");
            for (int i = 0; i< subForms.length() ; i++){
                JSONObject subForm = subForms.getJSONObject(i);
                String subFormNameStr = subForm.getString("default_bind_path");
                String[] path = subFormNameStr.split("/");
                String subFormName = path[path.length - 1]; // the last token
                subFormNames.add(subFormName);
            }
        }
        return subFormNames;
    }

    private JSONObject retriveSubformDefinitionForBindPath(JSONArray subForms, String fieldName) throws Exception{
        for (int i = 0; i< subForms.length() ; i++){
            JSONObject subForm = subForms.getJSONObject(i);
            String subFormNameStr = subForm.getString("default_bind_path");
            String[] path = subFormNameStr.split("/");
            String subFormName = path[path.length - 1]; // the last token
            if (fieldName.equalsIgnoreCase(subFormName)){
                return subForm;
            }
        }
        return null;
    }

    private void writeXMLAttributes(Element node, XmlSerializer serializer){
        try {
            // get a map containing the attributes of this node
            NamedNodeMap attributes = node.getAttributes();

            // get the number of nodes in this map
            int numAttrs = attributes.getLength();

            for (int i = 0; i < numAttrs; i++) {
                Attr attr = (Attr) attributes.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();
                serializer.attribute("", attrName, attrValue);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRandomUUIDString(){
        return UUID.randomUUID().toString();
    }

    private String retrieveIdForSubmission(JSONObject jsonObject) throws Exception{
        JSONArray fields = jsonObject.getJSONObject("form").getJSONArray("fields");
        for (int i = 0; i < fields.length(); i++){
            JSONObject field = fields.getJSONObject(i);
            if (field.has("name") && field.getString("name").equalsIgnoreCase("id")){
                return field.getString("value");
            }
        }
        return null;
    }

    public Object getObjectAtPath(String[] path, JSONObject jsonObject) throws Exception{
        JSONObject object = jsonObject;
        int i = 0;
        while (i < path.length - 1) {
            if (object.has(path[i])) {
                Object o = object.get(path[i]);
                if (o instanceof JSONObject){
                    object = object.getJSONObject(path[i]);
                }
                else if (o instanceof JSONArray){
                    object = object.getJSONArray(path[i]).getJSONObject(0);
                }
            }
            i++;
        }
        return object.has(path[i]) ? object.get(path[i]) : null;
    }

    public JSONArray getPopulatedFieldsForArray(JSONObject fieldsDefinition, String entityId, JSONObject jsonObject, Map<String, String> overrides) throws  Exception{
        String bindPath = fieldsDefinition.getString("bind_type");
        String sql = "select * from " + bindPath + " where id='" + entityId + "'";
        String dbEntity = theAppContext.formDataRepository().queryUniqueResult(sql);

        JSONObject entityJson = new JSONObject();
        if (dbEntity != null && !dbEntity.isEmpty()){
            entityJson = new JSONObject(dbEntity);
        }

        JSONArray fieldsArray = fieldsDefinition.getJSONArray("fields");

        for (int i = 0; i < fieldsArray.length(); i++){
            JSONObject item = fieldsArray.getJSONObject(i);
            if (!item.has("name"))
                continue; // skip elements without name
            if (item.has("bind")){
                String pathSting = item.getString("bind");
                pathSting = pathSting.startsWith("/") ? pathSting.substring(1) : pathSting;
                String[] path = pathSting.split("/");
                String value = getValueForPath(path, jsonObject);
                item.put("value", value);
                item.remove("bind");
            }

            item.put("source", bindPath + "." +  item.getString("name"));

            if (item.has("name") && item.getString("name").equalsIgnoreCase("id")){
                String id = entityJson.has("id") ? entityJson.getString("id") : generateRandomUUIDString();
                item.put("value", id);
            }

//            // temp hack for existing location field
//            if (item.has("name") && item.getString("name").equalsIgnoreCase("existing_location")){
//                if (overrides.containsKey("existing_location")){
//                    item.put("value", overrides.get("existing_location"));
//                }else
//                    item.put("value", "4ccd5a33-c462-4b53-b8c1-a1ad1c3ba0cf");
//            }
        }
        return fieldsArray;
    }

    public JSONArray getFieldsArrayForSubFormDefinition(JSONObject fieldsDefinition) throws  Exception{
        JSONArray fieldsArray = fieldsDefinition.getJSONArray("fields");
        String bindPath = fieldsDefinition.getString("bind_type");

        JSONArray subFormFieldsArray = new JSONArray();

        for (int i = 0; i < fieldsArray.length(); i++){
            JSONObject field = new JSONObject();
            JSONObject item = fieldsArray.getJSONObject(i);
            if (!item.has("name"))
                continue; // skip elements without name
            field.put("name", item.getString("name"));
            field.put("source", bindPath + "." +  item.getString("name"));
            subFormFieldsArray.put(i, field);
        }

        return subFormFieldsArray;
    }

    public JSONObject getFieldValuesForSubFormDefinition(JSONObject fieldsDefinition, String entityId, JSONObject jsonObject, Map<String, String> overrides) throws  Exception{

        JSONArray fieldsArray = fieldsDefinition.getJSONArray("fields");

        JSONObject fieldsValues = new JSONObject();

        for (int i = 0; i < fieldsArray.length(); i++){
            JSONObject item = fieldsArray.getJSONObject(i);
            if (!item.has("name"))
                continue; // skip elements without name
            if (item.has("bind")){
                String pathSting = item.getString("bind");
                pathSting = pathSting.startsWith("/") ? pathSting.substring(1) : pathSting;
                String[] path = pathSting.split("/");

                //check if we need to override this val
                if (overrides.containsKey(item.getString("name"))){
                    fieldsValues.put(item.getString("name"), overrides.get(item.getString("name")));
                }
                else{
                    String value = getValueForPath(path, jsonObject);
                    fieldsValues.put(item.getString("name"), value);
                }
            }

            //TODO: generate the id for the record
            if (item.has("name") && item.getString("name").equalsIgnoreCase("id")){
                String id = entityId != null ? entityId : generateRandomUUIDString();
                fieldsValues.put(item.getString("name"), id);
            }
        }
        return fieldsValues;
    }

    public String getValueForPath(String[] path, JSONObject jsonObject) throws Exception{
        JSONObject object = jsonObject;
        String value = null;
        int i = 0;
        while (i < path.length - 1) {
            if (object.has(path[i])) {
                Object o = object.get(path[i]);
                if (o instanceof JSONObject){
                    object = object.getJSONObject(path[i]);
                }
                else if (o instanceof JSONArray){
                    object = object.getJSONArray(path[i]).getJSONObject(0);
                }
            }
            i++;
        }
        if(object.has(path[i]) && object.get(path[i]) instanceof JSONObject && ((JSONObject) object.get(path[i])).has("content")){
            value = ((JSONObject) object.get(path[i])).getString("content");
        }
        else if(object.has(path[i]) && !(object.get(path[i]) instanceof JSONObject)){
            value = object.has(path[i]) ? object.get(path[i]).toString() : null;
        }
        return value;
    }

    private String readFileFromAssetsFolder(String fileName){
        String fileContents = null;
        try {
            InputStream is = mContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileContents = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        //Log.d("File", fileContents);
        return fileContents;
    }

    public static int getIndexForFormName(String formName, String[] formNames){
        for (int i = 0; i < formNames.length; i++){
            if (formName.equalsIgnoreCase(formNames[i])){
                return i;
            }
        }
        return -1;
    }

}
