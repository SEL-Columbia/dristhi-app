package org.ei.drishti.domain;

import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.ANCClient;

import java.util.Locale;

import static org.ei.drishti.util.Log.logWarn;

public enum ANCServiceType {
    ANC_1 {
        @Override
        public String displayName() {
            return "ANC 1";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "ANC 1";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_ANC;
        }
    },
    ANC_2 {
        @Override
        public String displayName() {
            return "ANC 2";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "ANC 2";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_ANC;
        }
    },
    ANC_3 {
        @Override
        public String displayName() {
            return "ANC 3";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "ANC 3";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_ANC;
        }
    },
    ANC_4 {
        @Override
        public String displayName() {
            return "ANC 4";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "ANC 4";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_ANC;
        }
    },
    TT_1 {
        @Override
        public String displayName() {
            return "TT";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "TT 1";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_TT;
        }
    },
    TT_2 {
        @Override
        public String displayName() {
            return "TT";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "TT 2";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_TT;
        }
    },
    TT_BOOSTER {
        @Override
        public String displayName() {
            return "TT";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "TT Booster";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_TT;
        }
    },
    IFA {
        @Override
        public String displayName() {
            return "IFA";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "IFA";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_IFA;
        }
    },

    HB {
        @Override
        public String displayName() {
            return "HB";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "HB";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_HB;
        }
    },
    DELIVERY_PLAN {
        @Override
        public String displayName() {
            return "Delivery Plan";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "Delivery Plan";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_DELIVERY_PLAN;
        }
    },
    PNC {
        @Override
        public String displayName() {
            return "PNC";
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "PNC";
        }

        @Override
        public String category() {
            return ANCClient.CATEGORY_PNC;
        }
    },
    EMPTY {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_empty);
        }

        @Override
        public String onServiceCompleteDisplayName() {
            return "";
        }

        @Override
        public String category() {
            return "";
        }
    };

    public abstract String displayName();

    public abstract String onServiceCompleteDisplayName();

    public String shortName() {
        return displayName();
    }

    public abstract String category();

    public static ANCServiceType tryParse(String type, ANCServiceType defaultType) {
        try {
            if (type.equalsIgnoreCase("ANC 1")) {
                return ANCServiceType.valueOf("ANC_1");
            } else if (type.equalsIgnoreCase("ANC 2")) {
                return ANCServiceType.valueOf("ANC_2");
            } else if (type.equalsIgnoreCase("ANC 3")) {
                return ANCServiceType.valueOf("ANC_3");
            } else if (type.equalsIgnoreCase("ANC 4")) {
                return ANCServiceType.valueOf("ANC_4");
            } else if (type.equalsIgnoreCase("TT 1")) {
                return ANCServiceType.valueOf("TT_1");
            } else if (type.equalsIgnoreCase("TT 2")) {
                return ANCServiceType.valueOf("TT_2");
            } else if (type.equalsIgnoreCase("TT Booster")) {
                return ANCServiceType.valueOf("TT_BOOSTER");
            } else if (type.equalsIgnoreCase("IFA")) {
                return ANCServiceType.valueOf("IFA");
            } else if (type.equalsIgnoreCase("HB")) {
                return ANCServiceType.valueOf("HB");
            } else if (type.equalsIgnoreCase("Delivery Plan")) {
                return ANCServiceType.valueOf("DELIVERY_PLAN");
            } else if (type.equalsIgnoreCase("PNC")) {
                return ANCServiceType.valueOf("PNC");
            } else {
                return StringUtils.isBlank(type) ? defaultType : ANCServiceType.valueOf(type.toUpperCase(Locale.getDefault()));
            }
        } catch (IllegalArgumentException e) {
            logWarn("Unknown current Service Type : " + type + " Exception : " + e);
            return defaultType;
        }
    }
}
