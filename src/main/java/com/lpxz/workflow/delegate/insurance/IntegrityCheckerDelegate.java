package com.lpxz.workflow.delegate.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Component("integrityChecker")
public class IntegrityCheckerDelegate implements JavaDelegate {
    private final static Logger logger = Logger.getLogger(IntegrityCheckerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("开始检查参保申请资料的完整性");

        boolean isDocumentComplete = checkRequestIntegrity(execution);
        logger.info("申请资料是完整的吗？" + isDocumentComplete);

        execution.setVariable("isDocumentComplete", isDocumentComplete);

        if (!isDocumentComplete) {
            execution.setVariable("declineMessage", "参保申请资料不完整，请核对并补充相关资料。");
        }
    }

    private boolean checkRequestIntegrity(DelegateExecution execution) {
        // TODO 检查参保申请资料的完整性
        String id = (String) execution.getVariable("id");
        String name = (String) execution.getVariable("name");
        logger.fine(id + ": " + name);

        return id != null && !id.isEmpty() && name != null && !name.isEmpty();
    }
}
