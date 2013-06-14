package org.ei.drishti;

public class AllConstants {
    public static final String REPORT_CATEGORY = "reportCategory";
    public static final String INDICATOR_DETAIL = "indicatorDetail";
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";
    public static final String MONTH = "month";
    public static final String CASE_IDS = "caseIds";
    public static final String INDICATOR = "indicator";
    public static final String CASE_ID = "caseId";

    public static final String LANGUAGE_PREFERENCE_KEY = "locale";
    public static final String ENGLISH_LOCALE = "en";
    public static final String KANNADA_LOCALE = "kn";
    public static final String DEFAULT_LOCALE = ENGLISH_LOCALE;
    public static final String ENGLISH_LANGUAGE = "English";
    public static final String KANNADA_LANGUAGE = "Kannada";
    public static final String IS_SYNC_IN_PROGRESS_PREFERENCE_KEY = "isSyncInProgress";
    public static final String TYPE = "type";
    public static final String WOMAN_TYPE = "woman";
    public static final String REALM = "Dristhi";
    public static final String AUTHENTICATE_USER_URL_PATH = "/authenticate-user";

    public static final String DRISHTI_BASE_URL = "https://drishti.modilabs.org";
    public static final String HOST = "drishti.modilabs.org";
    public static final int PORT = 443;
    public static final boolean SHOULD_VERIFY_CERTIFICATE = true;

//    public static final String DRISHTI_BASE_URL = "https://li310-155.members.linode.com";
//    public static final String HOST = AuthScope.ANY_HOST;
//    public static final int PORT = AuthScope.ANY_PORT;
//    public static final boolean SHOULD_VERIFY_CERTIFICATE = false;

    public static final String DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH = "../../img/woman-placeholder.png";
    public static final String FORM_NAME_PARAM = "formName";
    public static final String INSTANCE_ID_PARAM = "instanceId";
    public static final String ENTITY_ID_PARAM = "entityId";
    public static final String VERSION_PARAM = "version";
    public static final String ENTITY_ID_FIELD_NAME = "id";
    public static final String ZIGGY_FILE_LOADER = "ziggyFileLoader";
    public static final String FORM_SUBMISSION_ROUTER = "formSubmissionRouter";

    public static final String REPOSITORY = "formDataRepositoryContext";
    public static final String EC_REGISTRATION_FORM_NAME = "ec_registration";
    public static final String FP_COMPLICATIONS_FORM_NAME = "fp_complications";
    public static final String FP_CHANGE_FORM_NAME = "fp_change";
    public static final String RENEW_FP_PRODUCT_FORM_NAME = "renew_fp_product";
    public static final String EC_CLOSE_FORM_NAME = "ec_close";
    public static final String ANC_REGISTRATION_FORM_NAME = "anc_registration";
    public static final String ANC_REGISTRATION_OA_FORM_NAME = "anc_registration_oa";
    public static final String ANC_VISIT_FORM_NAME = "anc_visit";
    public static final String ANC_CLOSE_FORM_NAME = "anc_close";
    public static final String TT_BOOSTER_FORM_NAME = "tt_booster";
    public static final String TT_1_FORM_NAME = "tt_1";
    public static final String TT_2_FORM_NAME = "tt_2";
    public static final String IFA_FORM_NAME = "ifa";
    public static final String HB_TEST_FORM_NAME = "hb_test";
    public static final String DELIVERY_OUTCOME_TEST_FORM_NAME = "delivery_outcome";
    public static final String PNC_REGISTRATION_OA_FORM_NAME = "pnc_registration_oa";
    public static final String PNC_CLOSE_FORM_NAME = "pnc_close";
    public static final String PNC_VISIT_FORM_NAME = "pnc_visit";
    public static final String CURRENT_FP_METHOD_FIELD_NAME = "currentMethod";
    public static final String NEW_FP_METHOD_FIELD_NAME = "newMethod";
    public static final String FAMILY_PLANNING_METHOD_CHANGE_DATE_FIELD_NAME = "familyPlanningMethodChangeDate";

    public static final String ENTITY_ID = "entityId";
    public static final int FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE = 112;
    public static final String ALERT_NAME_PARAM = "alertName";
    public static final String BOOLEAN_FALSE = "no";

    public class ANCCloseFields {
        public static final String DEATH_OF_WOMAN_FIELD_VALUE = "death_of_woman";
        public static final String CLOSE_REASON_FIELD_NAME = "closeReason";
        public static final String PERMANENT_RELOCATION_FIELD_VALUE = "relocation_permanent";
    }

    public class IFAFields {
        public static final String NUMBER_OF_IFA_TABLETS_GIVEN = "numberOfIFATabletsGiven";
        public static final String IFA_TABLETS_DATE = "ifaTabletsDate";
    }

    public class HbTestFields {
        public static final String HB_LEVEL = "hbLevel";
        public static final String HB_TEST_DATE = "hbTestDate";
    }

    public class TTFields {
        public static final String TT_DOSE = "ttDose";
        public static final String TT_DATE = "ttDate";
    }

    public class ANCVisitFields {
        public static final String REFERENCE_DATE = "referenceDate";
        public static final String THAYI_CARD_NUMBER = "thayiCardNumber";
        public static final String ANC_VISIT_NUMBER = "ancVisitNumber";
        public static final String ANC_VISIT_DATE = "ancVisitDate";
        public static final String BP_SYSTOLIC = "bpSystolic";
        public static final String BP_DIASTOLIC = "bpDiastolic";
        public static final String TEMPERATURE = "temperature";
        public static final String WEIGHT = "weight";
    }

    public class PNCVisitFields {
        public static final String PNC_VISIT_NUMBER = "pncVisitNumber";
        public static final String PNC_VISIT_DATE = "pncVisitDate";
        public static final String BP_SYSTOLIC = "bpSystolic";
        public static final String BP_DIASTOLIC = "bpDiastolic";
        public static final String TEMPERATURE = "temperature";
        public static final String HB_LEVEL = "hbLevel";
        public static final String NUMBER_OF_IFA_TABLETS_GIVEN = "numberOfIFATabletsGiven";
        public static final String IFA_TABLETS_DATE = "ifaTabletsDate";
    }

    public class DeliveryOutcomeFields {
        public static final String DID_WOMAN_SURVIVE = "didWomanSurvive";
        public static final String DID_MOTHER_SURVIVE = "didMotherSurvive";
    }
}
