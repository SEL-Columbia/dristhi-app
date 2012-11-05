package org.ei.drishti.domain;

import java.util.Map;

import static org.ei.drishti.domain.TimelineEvent.*;
import static org.ei.drishti.util.Log.logWarn;

public enum FPMethod {
    CONDOM {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return forFPCondomRenew(caseId, details);
        }
    },
    IUD {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return forFPIUDRenew(caseId, details);
        }
    },
    OCP {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return forFPOCPRenew(caseId, details);
        }
    },
    DMPA {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return forFPDMPARenew(caseId, details);
        }
    },
    MALE_STERILIZATION {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    },
    FEMALE_STERILIZATION {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    },
    ECP {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    },
    TRADITIONAL_METHODS {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    },
    LAM {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    },
    NONE {
        public TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details) {
            return null;
        }
    };

    public abstract TimelineEvent getTimelineEventForRenew(String caseId, Map<String, String> details);

    public static FPMethod tryParse(String method, FPMethod defaultMethod) {
        try {
            return FPMethod.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            logWarn("Unknown current FP method : " + method + " Exception : " + e);
            return defaultMethod;
        }

    }
}
