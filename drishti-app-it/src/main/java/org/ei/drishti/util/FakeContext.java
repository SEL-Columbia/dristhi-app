package org.ei.drishti.util;

import org.ei.drishti.Context;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.service.LoginService;

import java.util.Date;

public class FakeContext {
    public static Context setupService(final DrishtiService drishtiService) {
        return Context.setInstance(new Context() {
            @Override
            protected DrishtiService drishtiService() {
                return drishtiService;
            }

            @Override
            protected String repositoryName() {
                return "drishti.db." + (new Date().getTime() - 1);
            }

            @Override
            public String password() {
                return "password";
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

            @Override
            protected String repositoryName() {
                return "drishti.db." + (new Date().getTime() - 1);
            }

            @Override
            public String password() {
                return "password";
            }
        });
    }
}
