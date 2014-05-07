package org.ei.drishti.domain;

import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.Context;
import org.ei.drishti.R;

import java.util.Locale;

import static org.ei.drishti.util.Log.logWarn;

public enum ChildServiceType {
    MEASLES {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_measles);
        }
    },
    MEASLESBOOSTER {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_measles_booster);
        }
    },
    OPV_BOOSTER {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_opv_booster);
        }
    },
    DPTBOOSTER_1 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_1);
        }
    },
    DPTBOOSTER_2 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_2);
        }
    },
    OPV_0 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_opv_0);
        }
    },
    OPV_1 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_opv_1);
        }
    },
    OPV_2 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_opv_2);
        }
    },
    OPV_3 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_opv_3);
        }
    },

    PENTAVALENT_1 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_1);
        }
    },
    PENTAVALENT_2 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_2);
        }
    },
    PENTAVALENT_3 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_3);
        }
    },
    BCG {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_bcg);
        }
    },
    PNC {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_pnc);
        }
    },
    JE {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_je);
        }
    },
    MMR {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_mmr);
        }
    },
    VITAMIN_A {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_vitamin_a);
        }
    },
    ILLNESS_VISIT {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_illness_visit);
        }
    },
    HEPB_0 {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_hepb_0);
        }
    },
    EMPTY {
        @Override
        public String displayName() {
            return Context.getInstance().applicationContext().getString(R.string.service_type_empty);
        }
    };

    public abstract String displayName();

    public static ChildServiceType tryParse(String type, ChildServiceType defaultType) {
        try {
            if (type.equalsIgnoreCase("Illness Visit")) {
                return ChildServiceType.valueOf("ILLNESS_VISIT");
            } else if (type.equalsIgnoreCase("Vitamin A")) {
                return ChildServiceType.valueOf("VITAMIN_A");
            } else {
                return StringUtils.isBlank(type) ? defaultType : ChildServiceType.valueOf(type.toUpperCase(Locale.getDefault()));
            }
        } catch (IllegalArgumentException e) {
            logWarn("Unknown current Service Type : " + type + " Exception : " + e);
            return defaultType;
        }
    }
}
