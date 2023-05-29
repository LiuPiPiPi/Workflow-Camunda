package com.lpxz.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/4/18
 */
public class OfferLoanDelegate implements JavaDelegate {

    private final static Logger logger = Logger.getLogger(OfferLoanDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) {
        logger.info("offer " + execution.getVariable("lenderId") + " loans");
    }
}
