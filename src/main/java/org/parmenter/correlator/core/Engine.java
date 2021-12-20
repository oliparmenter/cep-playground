package org.parmenter.correlator.core;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import org.parmenter.correlator.data.DataStreamEvent;
import org.parmenter.correlator.data.models.ModelManager;

import java.util.List;

public class Engine {

    private EPRuntime _runtime = null;

    public Engine(){

    }

    public boolean isReady(){
        return _runtime != null;
    }


    private EPRuntime get_runtime(){
        if (_runtime == null){
            Configuration configuration = new Configuration();

            //Setup models
            List<Class> modelList = ModelManager.modelList();
            for(Class eventType : modelList){
                configuration.getCommon().addEventType(
                        eventType
                );
            }

            EPRuntime _runtime = EPRuntimeProvider.getDefaultRuntime(configuration);

            EPCompiled compiled = null;
            EPDeployment deployment;
            try {
                deployment = _runtime.getDeploymentService().deploy(compiled);
            } catch (EPDeployException ex) {
                throw new RuntimeException("Dpeloyment failed: " + ex.getMessage(), ex);
            }
        }

        return _runtime;
    }


    public void newEvent(DataStreamEvent event) {
        _runtime.getEventService().sendEventBean(event, event.getEventStreamName());
    }
}
