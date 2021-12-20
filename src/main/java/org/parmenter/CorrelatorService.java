package org.parmenter;

import org.parmenter.correlator.core.Engine;
import org.parmenter.correlator.data.DataStreamEvent;

/**
 * Hello world!
 *
 */
public class CorrelatorService
{
//    public static void main( String[] args )
//    {
//        CorrelatorService service = new CorrelatorService();
//        service.startEngine();
//    }

    Engine engine;

    public CorrelatorService(){

    }

    public void startEngine(){
        engine = new Engine();
    }

    public void newEvent(DataStreamEvent event) {
        if(!engine.isReady()){
            throw new RuntimeException("Engine not ready")
        }

        engine.newEvent(event)
    }
}
