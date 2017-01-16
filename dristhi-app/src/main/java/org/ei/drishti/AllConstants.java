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
    public static final String CHILD_TYPE = "child";
    public static final String REALM = "OpenSRP";
    public static final String AUTHENTICATE_USER_URL_PATH = "/anm-villages?anm-id=";
    public static final String OPENSRP_AUTH_USER_URL_PATH = "/security/authenticate";
    public static final String OPENSRP_LOCATION_URL_PATH = "/location/location-tree";

    public static final String FORM_NAME_PARAM = "formName";
    public static final String INSTANCE_ID_PARAM = "instanceId";
    public static final String ENTITY_ID_PARAM = "entityId";
    public static final String FIELD_OVERRIDES_PARAM = "fieldOverrides";
    public static final String VERSION_PARAM = "version";
    public static final String SYNC_STATUS = "sync_status";
    public static final String ENTITY_ID_FIELD_NAME = "id";
    public static final String ZIGGY_FILE_LOADER = "ziggyFileLoader";
    public static final String FORM_SUBMISSION_ROUTER = "formSubmissionRouter";
    public static final String ANM_LOCATION_CONTROLLER = "anmLocationContext";

    public static final String REPOSITORY = "formDataRepositoryContext";

    public static final String NEW_FP_METHOD_FIELD_NAME = "newMethod";

    public static final String ENTITY_ID = "entityId";
    public static final int FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE = 112;
    public static final String ALERT_NAME_PARAM = "alertName";
    public static final String BOOLEAN_TRUE = "yes";
    public static final String BOOLEAN_FALSE = "no";
    public static final String SPACE = " ";
    public static final String COMMA_WITH_SPACE = ", ";
    public static final String DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH = "../../img/woman-placeholder.png";
    public static final String DEFAULT_GIRL_INFANT_IMAGE_PLACEHOLDER_PATH = "../../img/icons/child-girlinfant@3x.png";
    public static final String DEFAULT_BOY_INFANT_IMAGE_PLACEHOLDER_PATH = "../../img/icons/child-infant@3x.png";
    public static final String FEMALE_GENDER = "female";
    public static final String FORM_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss ZZZ";
    public static final String SHORT_DATE_FORMAT = "dd/MM";
    public static final String FILE_PATH_STARTING_STRING = "file:///";
    public static final String SLASH_STRING = "/";
    public static final String FP_DIALOG_TAB_SELECTION = "FP_DIALOG_TAB_SELECTION";

    public static final String OUT_OF_AREA = "out_of_area";
    public static final String IN_AREA = "in_area";

    public class FormNames {
        public static final String EC_REGISTRATION = "ec_registration";
        public static final String FP_COMPLICATIONS = "fp_complications";
        public static final String FP_CHANGE = "fp_change";
        public static final String RENEW_FP_PRODUCT = "renew_fp_product";
        public static final String EC_CLOSE = "ec_close";
        public static final String ANC_REGISTRATION = "anc_registration";
        public static final String ANC_REGISTRATION_OA = "anc_registration_oa";
        public static final String ANC_VISIT = "anc_visit";
        public static final String ANC_CLOSE = "anc_close";
        public static final String TT = "tt";
        public static final String TT_BOOSTER = "tt_booster";
        public static final String TT_1 = "tt_1";
        public static final String TT_2 = "tt_2";
        public static final String IFA = "ifa";
        public static final String HB_TEST = "hb_test";
        public static final String DELIVERY_OUTCOME = "delivery_outcome";
        public static final String PNC_REGISTRATION_OA = "pnc_registration_oa";
        public static final String PNC_CLOSE = "pnc_close";
        public static final String PNC_VISIT = "pnc_visit";
        public static final String PNC_POSTPARTUM_FAMILY_PLANNING = "postpartum_family_planning";
        public static final String CHILD_IMMUNIZATIONS = "child_immunizations";
        public static final String CHILD_REGISTRATION_EC = "child_registration_ec";
        public static final String CHILD_REGISTRATION_OA = "child_registration_oa";
        public static final String CHILD_CLOSE = "child_close";
        public static final String CHILD_ILLNESS = "child_illness";
        public static final String VITAMIN_A = "vitamin_a";
        public static final String DELIVERY_PLAN = "delivery_plan";
        public static final String EC_EDIT = "ec_edit";
        public static final String ANC_INVESTIGATIONS = "anc_investigations";
        public static final String RECORD_ECPS = "record_ecps";
        public static final String FP_REFERRAL_FOLLOWUP = "fp_referral_followup";
        public static final String FP_FOLLOWUP = "fp_followup";
    }

    public class ECRegistrationFields {
        public static final String CURRENT_FP_METHOD = "currentMethod";
        public static final String WOMAN_DOB = "womanDOB";
        public static final String FAMILY_PLANNING_METHOD_CHANGE_DATE = "familyPlanningMethodChangeDate";
        public static final String IUD_PLACE = "iudPlace";
        public static final String IUD_PERSON = "iudPerson";
        public static final String NUMBER_OF_CONDOMS_SUPPLIED = "numberOfCondomsSupplied";
        public static final String NUMBER_OF_CENTCHROMAN_PILLS_DELIVERED = "numberOfCentchromanPillsDelivered";
        public static final String NUMBER_OF_OCP_DELIVERED = "numberOfOCPDelivered";
        public static final String CASTE = "caste";
        public static final String ECONOMIC_STATUS = "economicStatus";
        public static final String NUMBER_OF_PREGNANCIES = "numberOfPregnancies";
        public static final String PARITY = "parity";
        public static final String NUMBER_OF_LIVING_CHILDREN = "numberOfLivingChildren";
        public static final String NUMBER_OF_STILL_BIRTHS = "numberOfStillBirths";
        public static final String NUMBER_OF_ABORTIONS = "numberOfAbortions";
        public static final String HIGH_PRIORITY_REASON = "highPriorityReason";
        public static final String IS_HIGH_PRIORITY = "isHighPriority";
        public static final String REGISTRATION_DATE = "registrationDate";
        public static final String BPL_VALUE = "BPL";
        public static final String SC_VALUE = "SC";
        public static final String ST_VALUE = "ST";
    }

    public class ANCCloseFields {
        public static final String DEATH_OF_WOMAN_FIELD_VALUE = "death_of_woman";
        public static final String CLOSE_REASON_FIELD_NAME = "closeReason";
        public static final String PERMANENT_RELOCATION_FIELD_VALUE = "relocation_permanent";
    }

    public class PNCCloseFields {
        public static final String DEATH_OF_MOTHER_FIELD_VALUE = "death_of_mother";
        public static final String PERMANENT_RELOCATION_FIELD_VALUE = "permanent_relocation";
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

    public class ANCRegistrationFields {
        public static final String EDD = "edd";
        public static final String HIGH_RISK_REASON = "highRiskReason";
        public static final String IS_HIGH_RISK = "isHighRisk";
        public static final String ASHA_PHONE_NUMBER = "ashaPhoneNumber";
        public static final String ANC_NUMBER = "ancNumber";
        public static final String REGISTRATION_DATE = "registrationDate";
        public static final String RISK_OBSERVED_DURING_ANC = "riskObservedDuringANC";
    }

    public class PNCRegistrationFields {
        public static final String DELIVERY_PLACE = "deliveryPlace";
        public static final String DELIVERY_TYPE = "deliveryType";
        public static final String DELIVERY_COMPLICATIONS = "deliveryComplications";
        public static final String OTHER_DELIVERY_COMPLICATIONS = "otherDeliveryComplications";
        public static final String IMMEDIATE_REFERRAL_REASON = "immediateReferralReason";
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
        public static final String PNC_VISIT_DAY = "pncVisitDay";
        public static final String PNC_VISIT_DATE = "pncVisitDate";
        public static final String BP_SYSTOLIC = "bpSystolic";
        public static final String BP_DIASTOLIC = "bpDiastolic";
        public static final String TEMPERATURE = "temperature";
        public static final String WEIGHT = "weight";
        public static final String HB_LEVEL = "hbLevel";
        public static final String NUMBER_OF_IFA_TABLETS_GIVEN = "numberOfIFATabletsGiven";
        public static final String CHILD_PNC_VISIT_SUB_FORM_NAME = "child_pnc_visit";
    }

    public class DeliveryOutcomeFields {
        public static final String DID_WOMAN_SURVIVE = "didWomanSurvive";
        public static final String DID_MOTHER_SURVIVE = "didMotherSurvive";
        public static final String REFERENCE_DATE = "referenceDate";
        public static final String DELIVERY_PLACE = "deliveryPlace";
        public static final String DELIVERY_OUTCOME = "deliveryOutcome";
        public static final String CHILD_REGISTRATION_SUB_FORM_NAME = "child_registration";
        public static final String STILL_BIRTH_VALUE = "still_birth";
    }

    public class ChildRegistrationECFields {
        public static final String BCG_DATE = "bcgDate";
        public static final String MEASLES_DATE = "measlesDate";
        public static final String MEASLESBOOSTER_DATE = "measlesboosterDate";
        public static final String OPV_0_DATE = "opv0Date";
        public static final String OPV_1_DATE = "opv1Date";
        public static final String OPV_2_DATE = "opv2Date";
        public static final String OPV_3_DATE = "opv3Date";
        public static final String OPVBOOSTER_DATE = "opvboosterDate";
        public static final String DPTBOOSTER_1_DATE = "dptbooster1Date";
        public static final String DPTBOOSTER_2_DATE = "dptbooster2Date";
        public static final String PENTAVALENT_1_DATE = "pentavalent1Date";
        public static final String PENTAVALENT_2_DATE = "pentavalent2Date";
        public static final String PENTAVALENT_3_DATE = "pentavalent3Date";
        public static final String HEPB_BIRTH_DOSE_DATE = "hepb0Date";
        public static final String SHOULD_CLOSE_MOTHER = "shouldCloseMother";
        public static final String MMR_DATE = "mmrDate";
        public static final String JE_DATE = "jeDate";
    }

    public class ChildRegistrationFields {
        public static final String MOTHER_ID = "motherId";
        public static final String CHILD_ID = "childId";
        public static final String DATE_OF_BIRTH = "dateOfBirth";
        public static final String WEIGHT = "weight";
        public static final String IMMUNIZATIONS_GIVEN = "immunizationsGiven";
        public static final String GENDER = "gender";
        public static final String NAME = "name";
        public static final String HIGH_RISK_REASON = "childHighRiskReason";
        public static final String IS_CHILD_HIGH_RISK = "isChildHighRisk";
    }

    public class ChildRegistrationOAFields {
        public static final String CHILD_ID = "id";
        public static final String DATE_OF_BIRTH = "dateOfBirth";
        public static final String WEIGHT = "weight";
        public static final String IMMUNIZATIONS_GIVEN = "immunizationsGiven";
        public static final String NAME = "name";
        public static final String THAYI_CARD_NUMBER = "thayiCardNumber";
    }

    public class PNCRegistrationOAFields {
        public static final String WEIGHT = "weight";
        public static final String IMMUNIZATIONS_GIVEN = "immunizationsGiven";
        public static final String CHILD_REGISTRATION_OA_SUB_FORM_NAME = "child_registration_oa";
    }

    public class ChildImmunizationsFields {
        public static final String PREVIOUS_IMMUNIZATIONS_GIVEN = "previousImmunizations";
        public static final String IMMUNIZATIONS_GIVEN = "immunizationsGiven";
        public static final String IMMUNIZATION_DATE = "immunizationDate";
    }

    public static class Immunizations {
        public static final String BCG = "bcg";

        public static final String MEASLES = "measles";
        public static final String MEASLES_BOOSTER = "measlesbooster";

        public static final String OPV_0 = "opv_0";
        public static final String OPV_1 = "opv_1";
        public static final String OPV_2 = "opv_2";
        public static final String OPV_3 = "opv_3";
        public static final String OPV_BOOSTER = "opvbooster";

        public static final String DPT_BOOSTER_1 = "dptbooster_1";
        public static final String DPT_BOOSTER_2 = "dptbooster_2";

        public static final String PENTAVALENT_1 = "pentavalent_1";
        public static final String PENTAVALENT_2 = "pentavalent_2";
        public static final String PENTAVALENT_3 = "pentavalent_3";

        public static final String HEPATITIS_BIRTH_DOSE = "hepb_0";
        public static final String MMR = "mmr";
        public static final String JE = "je";

        public static final String[] ALL = new String[]{
                BCG,
                MEASLES, MEASLES_BOOSTER,
                OPV_0, OPV_1, OPV_2, OPV_3, OPV_BOOSTER,
                DPT_BOOSTER_1, DPT_BOOSTER_2,
                PENTAVALENT_1, PENTAVALENT_2, PENTAVALENT_3,
                HEPATITIS_BIRTH_DOSE,
                JE,
                MMR};
    }

    public class ChildIllnessFields {
        public static final String CHILD_SIGNS = "childSigns";
        public static final String CHILD_SIGNS_OTHER = "childSignsOther";
        public static final String SICK_VISIT_DATE = "sickVisitDate";
        public static final String REPORT_CHILD_DISEASE = "reportChildDisease";
        public static final String REPORT_CHILD_DISEASE_OTHER = "reportChildDiseaseOther";
        public static final String REPORT_CHILD_DISEASE_DATE = "reportChildDiseaseDate";
        public static final String REPORT_CHILD_DISEASE_PLACE = "reportChildDiseasePlace";
        public static final String CHILD_REFERRAL = "childReferral";
    }

    public class VitaminAFields {
        public static final String VITAMIN_A_DOSE = "vitaminADose";
        public static final String VITAMIN_A_DATE = "vitaminADate";
        public static final String VITAMIN_A_PLACE = "vitaminAPlace";
    }

    public class CommonFormFields {
        public static final String SUBMISSION_DATE = "submissionDate";
    }

    public class DeliveryPlanFields {
        public static final String DELIVERY_FACILITY_NAME = "deliveryFacilityName";
        public static final String TRANSPORTATION_PLAN = "transportationPlan";
        public static final String BIRTH_COMPANION = "birthCompanion";
        public static final String ASHA_PHONE_NUMBER = "ashaPhoneNumber";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String REVIEWED_HRP_STATUS = "reviewedHRPStatus";
        public static final String DELIVERY_FACILITY_HOME_VALUE = "home";
        public static final String DELIVERY_FACILITY_SDH_VALUE = "sdh";
        public static final String DELIVERY_FACILITY_DH_VALUE = "dh";
    }
}
