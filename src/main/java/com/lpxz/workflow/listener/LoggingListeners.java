package com.lpxz.workflow.listener;

import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;

public class LoggingListeners {

    public final LoggingExecutionListener executionListener;
    public final LoggingTransitionListener transitionListener;
    public final LoggingUserTaskExecutionListener userTaskExecutionListener;
    public final LoggingStartEndEventExecutionListener startEndEventExecutionListener;

    public LoggingListeners() {
        executionListener = new LoggingExecutionListener();
        transitionListener = new LoggingTransitionListener();
        userTaskExecutionListener = new LoggingUserTaskExecutionListener();
        startEndEventExecutionListener = new LoggingStartEndEventExecutionListener();
    }

    public static class LoggingExecutionListener extends AbstractLoggingListener {
        @Override
        protected void notifyExecution(ExecutionEntity execution) {
            logExecution(execution);
        }
    }

    public static class LoggingStartEndEventExecutionListener extends AbstractLoggingListener {
        @Override
        protected void notifyExecution(ExecutionEntity execution) {
            String processDefinitionId = execution.getProcessDefinitionId();
            logExecution(execution, processDefinitionId);
        }
    }

    public static class LoggingTransitionListener extends AbstractLoggingListener {
        @Override
        protected void notifyExecution(ExecutionEntity execution) {
            TransitionImpl transitionTaken = execution.getTransition();
            if (transitionTaken != null) {
                logger.info(">>>>>[{}] ({} -> {} -> {})", execution.getId(),
                        transitionTaken.getSource().getId(), transitionTaken.getId(),
                        transitionTaken.getDestination().getId());
            }
        }
    }

    public static class LoggingUserTaskExecutionListener extends AbstractLoggingListener {
        @Override
        protected void notifyExecution(ExecutionEntity execution) {
            logExecution(execution, "(user task)");
        }
    }
}
