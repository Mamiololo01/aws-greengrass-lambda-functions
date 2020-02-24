package com.amazonaws.greengrass.cddembeddedvaadinskeleton.handlers;

import com.amazonaws.greengrass.cddembeddedvaadinskeleton.events.TimerFiredEvent;
import com.amazonaws.greengrass.cddembeddedvaadinskeleton.vaadin.EmbeddedVaadin;
import com.awslabs.aws.iot.greengrass.cdd.communication.Dispatcher;
import com.awslabs.aws.iot.greengrass.cdd.events.ImmutableGreengrassStartEvent;
import com.awslabs.aws.iot.greengrass.cdd.handlers.interfaces.GreengrassStartEventHandler;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class StartupHandler implements GreengrassStartEventHandler {
    private final int DELAY_MS = 5000;
    private final int PERIOD_MS = 5000;
    @Inject
    EmbeddedVaadin embeddedVaadin;
    @Inject
    Dispatcher dispatcher;

    @Inject
    public StartupHandler() {
    }

    @Override
    public void execute(ImmutableGreengrassStartEvent immutableGreengrassStartEvent) {
        embeddedVaadin.start();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dispatcher.dispatch(new TimerFiredEvent());
            }
        }, DELAY_MS, PERIOD_MS);
    }
}
