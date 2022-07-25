package org.parmenter.correlator.core;

import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import org.parmenter.correlator.data.DataStreamEvent;

public class Engine {

    private final EPRuntime runtime;
    private final EPCompiler compiler;
    private final Configuration configuration;

    public Engine(){
        this.compiler = EPCompilerProvider.getCompiler();
        this.configuration = Util.getConfiguration();
        this.runtime = EPRuntimeProvider.getDefaultRuntime(this.configuration);
    }

    public boolean isInitialised(){
        return runtime != null;
    }

    public EPRuntime getRuntime(){
        return this.runtime;
    }

    public void newEvent(DataStreamEvent event) {
        runtime.getEventService().sendEventBean(event, event.getEventStreamName());
    }

}
