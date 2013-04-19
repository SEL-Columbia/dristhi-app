package org.ei.drishti.service;

import org.ei.drishti.AllConstants;
import org.ei.drishti.repository.FormDataRepository;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import java.util.Map;

import static java.text.MessageFormat.format;
import static org.ei.drishti.util.Log.logError;
import static org.ei.drishti.util.Log.logInfo;
import static org.mozilla.javascript.Context.enter;
import static org.mozilla.javascript.Context.exit;
import static org.mozilla.javascript.Context.toObject;

public class ZiggyService {
    private static final String SAVE_METHOD_NAME = "save";
    private static final String JS_INIT_SCRIPT = "var formDataRepository = new enketo.FormDataRepository();\n" +
            "    var controller = new enketo.FormDataController(\n" +
            "        new enketo.EntityRelationshipLoader(),\n" +
            "        new enketo.FormDefinitionLoader(),\n" +
            "        new enketo.FormModelMapper(formDataRepository, new enketo.SQLQueryBuilder(formDataRepository), new enketo.IdFactory(new enketo.IdFactoryBridge())),\n" +
            "        formDataRepository);";

    private ZiggyFileLoader ziggyFileLoader;
    private FormDataRepository dataRepository;
    private Context context;
    private ScriptableObject scope;
    private Function saveFunction;

    public ZiggyService(ZiggyFileLoader ziggyFileLoader, FormDataRepository dataRepository) {
        this.ziggyFileLoader = ziggyFileLoader;
        this.dataRepository = dataRepository;
        initRhino();
    }

    public void saveForm(String params, String formInstance) throws Exception {
        context = enter();
        saveFunction.call(context, scope, scope, new Object[]{params, formInstance});
        logInfo(format("Saving form successful, with params: {0}, with instance {1}.", params, formInstance));
        exit();
    }

    private void initRhino() {
        try {
            context = enter();
            context.setOptimizationLevel(-1);
            scope = context.initStandardObjects();
            String jsFiles = ziggyFileLoader.getJSFiles();
            scope.put(AllConstants.REPOSITORY, scope, toObject(dataRepository, scope));
            scope.put(AllConstants.ZIGGY_FILE_LOADER, scope, toObject(ziggyFileLoader, scope));
            context.evaluateString(scope, jsFiles + JS_INIT_SCRIPT, "code", 1, null);
            saveFunction = ((Function) ((Map) scope.get("controller", scope)).get(SAVE_METHOD_NAME));
        } catch (Exception e) {
            logError("Rhino initialization failed. We are screwed. EOW!!!. Evil: " + e);
        } finally {
            exit();
        }
    }
}
