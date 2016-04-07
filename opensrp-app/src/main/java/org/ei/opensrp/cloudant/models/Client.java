package org.ei.opensrp.cloudant.models;

import com.cloudant.sync.datastore.BasicDocumentRevision;

import org.ei.opensrp.clientandeventmodel.Address;
import org.ei.opensrp.clientandeventmodel.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by koros on 3/16/16.
 */
public class Client extends org.ei.opensrp.clientandeventmodel.Client{

    // this is the revision in the database representing this task
    private BasicDocumentRevision rev;
    public BasicDocumentRevision getDocumentRevision() {
        return rev;
    }

    static final String DOC_TYPE = "org.ei.opensrp.cloudant.models.Client";
    private String type = DOC_TYPE;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Client(){
    }

    public Client(org.ei.opensrp.clientandeventmodel.Client client){

        setAddresses(client.getAddresses());
        setAttributes(client.getAttributes());
        setBaseEntityId(client.getBaseEntityId());
        setBirthdate(client.getBirthdate());
        setBirthdateApprox(client.getBirthdateApprox());
        setCreator(client.getCreator());
        setDateCreated(client.getDateCreated());
        setDeathdateApprox(client.getDeathdateApprox());
        setDateVoided(client.getDateVoided());
        setDateEdited(client.getDateEdited());
        setDeathdate(client.getDeathdate());
        setFirstName(client.getFirstName());
        setIdentifiers(client.getIdentifiers());
        setGender(client.getGender());
        setLastName(client.getLastName());
        setMiddleName(client.getMiddleName());
        setVoider(client.getVoider());
        setVoidReason(client.getVoidReason());
        setVoided(client.getVoided());
        setEditor(client.getEditor());
        setRelationalBaseEntityId(client.getRelationalBaseEntityId());
        setRelationships(client.getRelationships());
        setType(type);
    }

    public static final String type_key = "type";
    public static final String adresses_key = "adresses";
    public static final String attributes_key = "attributes";
    public static final String base_entity_id_key = "base_entity_id";
    public static final String birth_date_key = "birth_date";
    public static final String birth_date_approx_key = "birth_date_approx";
    public static final String creator_key = "creator";
    public static final String date_created_key = "date_created";
    public static final String date_voided_key = "date_voided";
    public static final String date_edited_key = "date_edited";
    public static final String death_date_key = "death_date";
    public static final String firstname_key = "firstname";
    public static final String identifiers_key = "identifiers";
    public static final String gender_key = "gender";
    public static final String lastname_key = "lastname";
    public static final String middlename_key = "middlename";
    public static final String voider_key = "voider";
    public static final String void_reason_key = "void_reason";
    public static final String editor_key = "editor";
    public static final String death_date_approx_key = "death_date_approx";
    public static final String voided_key = "voided";
    public static final String relationships_key = "relationships";
    public static final String relational_base_entityId_key = "relational_base_entityId";

    public static Client fromRevision(BasicDocumentRevision rev) {
        Client client = new Client();
        client.rev = rev;
        // this could also be done by a fancy object mapper
        Map<String, Object> map = rev.asMap();
        if(map.containsKey(type_key) && map.get(type_key).equals(Client.DOC_TYPE)) {
            client.setType((String) map.get(type_key));
            if(map.get(adresses_key)!=null)
                client.setAddresses((List<Address>) map.get(adresses_key));
            if(map.get(attributes_key)!=null)
                client.setAttributes((Map<String, Object>) map.get(attributes_key));

            if(map.get(base_entity_id_key)!=null)
                client.setBaseEntityId((String) map.get(base_entity_id_key));
            //the date is being saved as long
            Long timestamp = (Long) map.get(birth_date_key);
            if (timestamp != null)
                client.setBirthdate(new Date(timestamp));
            if(map.get(birth_date_approx_key)!=null)
                client.setBirthdateApprox((Boolean) map.get(birth_date_approx_key));
            if(map.get(creator_key)!=null)
                client.setCreator((User) map.get(creator_key));
            if(map.get(date_created_key)!=null)
                client.setDateCreated((Date) map.get(date_created_key));
            if(map.get(date_voided_key)!=null)
                client.setDateVoided((Date) map.get(date_voided_key));
            if(map.get(date_edited_key)!=null)
                client.setDateEdited((Date) map.get(date_edited_key));
            if(map.get(death_date_key)!=null)
                client.setDeathdate(new Date((Long) map.get(death_date_key)));
            if(map.get(firstname_key)!=null)
                client.setFirstName((String) map.get(firstname_key));
            if(map.get(identifiers_key)!=null)
                client.setIdentifiers((Map<String, String>) map.get(identifiers_key));
            if(map.get(gender_key)!=null)
                client.setGender((String) map.get(gender_key));
            if( map.get(lastname_key)!=null)
                client.setLastName((String) map.get(lastname_key));
            if(map.get(middlename_key)!=null)
                client.setMiddleName((String) map.get(middlename_key));
            if(map.get(voider_key)!=null)
                client.setVoider((User) map.get(voider_key));
            if(map.get(void_reason_key)!=null)
                client.setVoidReason((String) map.get(void_reason_key));
            if(map.get(editor_key)!=null)
                client.setEditor((User) map.get(editor_key));
            if(map.get(death_date_approx_key)!=null)
                client.setDeathdateApprox((Boolean) map.get(death_date_approx_key));
            if( map.get(voided_key)!=null)
                client.setVoided((Boolean) map.get(voided_key));
            if (map.get(relationships_key) != null)
                client.setRelationships((Map<String, List<String>>) map.get(relationships_key));
            if (map.get(relational_base_entityId_key) != null)
                client.setRelationalBaseEntityId((String) map.get(relational_base_entityId_key));
            return client;
        }
        return null;
    }

    public Map<String, Object> asMap() {
        // this could also be done by a fancy object mapper
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(type_key, type);
        if (getAddresses() != null)
            map.put(adresses_key, getAddresses());
        if (getAttributes() != null)
            map.put(attributes_key, getAttributes());
        if (getBaseEntityId() != null)
            map.put(base_entity_id_key, getBaseEntityId());
        if (getBirthdate() != null)
            map.put(birth_date_key, getBirthdate());
        if (getBirthdateApprox() != null)
            map.put(birth_date_approx_key, getBirthdateApprox());
        if (getCreator() != null)
            map.put(creator_key, getCreator());
        if (getDateCreated() != null)
            map.put(date_created_key, getDateCreated());
        if (getDateVoided() != null)
            map.put(date_voided_key, getDateVoided());
        if (getDateEdited() != null)
            map.put(date_edited_key, getDateEdited());
        if (getDeathdate() != null)
            map.put(death_date_key, getDeathdate());
        if (getFirstName() != null)
            map.put(firstname_key, getFirstName());
        if (getIdentifiers() != null)
            map.put(identifiers_key, getIdentifiers());
        if (getGender() != null)
            map.put(gender_key, getGender());
        if (getLastName() != null)
            map.put(lastname_key, getLastName());
        if (getMiddleName() != null)
            map.put(middlename_key, getMiddleName());
        if (getVoider() != null)
            map.put(voider_key, getVoider());
        if (getVoidReason() != null)
            map.put(void_reason_key, getVoidReason());
        if (getEditor() != null)
            map.put(editor_key, getEditor());
        if (getDeathdateApprox() != null)
            map.put(death_date_approx_key, getDeathdateApprox());
        if (getVoided() != null)
            map.put(voided_key, getVoided());
        if (getRelationships() != null)
            map.put(relationships_key, getRelationships());
        if (getRelationalBaseEntityId() != null)
            map.put(relational_base_entityId_key, getRelationalBaseEntityId());
        return map;
    }

}
