package org.ei.drishti.util;

import org.ei.drishti.Context;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.service.LoginService;

public class FakeContext {
    public static Context setupService(final DrishtiService drishtiService) {
        return Context.setInstance(new Context() {
            @Override
            protected DrishtiService drishtiService() {
                return drishtiService;
            }
        });
    }

    public static Context setupService(final DrishtiService drishtiService, final LoginService loginService) {
        return Context.setInstance(new Context() {
            @Override
            protected DrishtiService drishtiService() {
                return drishtiService;
            }

            @Override
            public LoginService loginService() {
                return loginService;
            }
        });
    }
}
