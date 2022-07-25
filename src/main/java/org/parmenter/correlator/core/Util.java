package org.parmenter.correlator.core;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import org.parmenter.correlator.data.models.ModelManager;

import java.util.List;

public class Util {

    public static Configuration getConfiguration() {
        Configuration config = new Configuration();
        List<Class> modelList = ModelManager.modelList();
        for (Class model : modelList) {
            config.getCommon().addEventType(model);
        }
        return config;
    }

    public static EPCompiled compileEPL(String eplStatementString, Configuration config) {
        CompilerArguments compilerArgs = new CompilerArguments(config);
        EPCompiled compiled;
        try {
            compiled = EPCompilerProvider.getCompiler().compile(eplStatementString, compilerArgs);
        } catch (EPCompileException e) {
            e.printStackTrace();
            compiled = null;
        }
        return compiled;
    }

}
