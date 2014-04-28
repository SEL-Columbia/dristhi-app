package org.ei.drishti.setup;

import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;

public class DrishtiTestSetup extends Setup {
    @Override
    public boolean shouldInstrument(ClassInfo classInfo) {
        return super.shouldInstrument(classInfo)
                || classInfo.getName().equals("org.ei.drishti.Context")
                || classInfo.getName().equals("org.ei.drishti.view.controller.ECSmartRegisterController")
                || classInfo.getName().equals("org.ei.drishti.view.controller.VillageController");
    }
}
