package com.lpxz.workflow.listener;

import com.lpxz.workflow.listener.LoggingListeners.LoggingExecutionListener;
import com.lpxz.workflow.listener.LoggingListeners.LoggingStartEndEventExecutionListener;
import com.lpxz.workflow.listener.LoggingListeners.LoggingTransitionListener;
import com.lpxz.workflow.listener.LoggingListeners.LoggingUserTaskExecutionListener;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;

public abstract class LoggingBpmnParseListener extends AbstractBpmnParseListener {

    protected final LoggingExecutionListener loggingExecutionListener;
    protected final LoggingTransitionListener loggingTransitionListener;
    protected final LoggingUserTaskExecutionListener loggingUserTaskExecutionListener;
    protected final LoggingStartEndEventExecutionListener loggingStartEndEventExecutionListener;

    public LoggingBpmnParseListener() {
        LoggingListeners loggingListeners = new LoggingListeners();
        loggingExecutionListener = loggingListeners.executionListener;
        loggingTransitionListener = loggingListeners.transitionListener;
        loggingUserTaskExecutionListener = loggingListeners.userTaskExecutionListener;
        loggingStartEndEventExecutionListener = loggingListeners.startEndEventExecutionListener;
    }

}