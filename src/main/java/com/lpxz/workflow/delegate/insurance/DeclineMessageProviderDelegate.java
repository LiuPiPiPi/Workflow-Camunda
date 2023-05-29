package com.lpxz.workflow.delegate.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Component("declineMessageProvider")
public class DeclineMessageProviderDelegate implements JavaDelegate {

    private final static Logger logger = Logger.getLogger(DeclineMessageProviderDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("开始准备拒保通知");

        String declineMessage = prepareDeclineMessage();

        execution.setVariable("declineMessage", declineMessage);
    }

    private String prepareDeclineMessage() {
        //TODO: 准备拒保通知
        return "抱歉！保险申请被拒绝，请联系人工客服以获取详细信息。";
    }
}
