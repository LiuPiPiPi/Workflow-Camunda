package com.lpxz.workflow.delegate.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Component("insuranceContractProvider")
public class InsuranceContractProviderDelegate implements JavaDelegate {
    private final Logger logger = Logger.getLogger(InsuranceContractProviderDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("开始准备保单");

        Long premium = (Long) execution.getVariable("premium");
        String contractMessage = prepareInsuranceContract(premium);
        logger.info(contractMessage);

        execution.setVariable("contractMessage", contractMessage);
    }

    private String prepareInsuranceContract(Long premium) {
        // TODO: 准备保单
        return "恭喜！核保通过。保费为" + premium + "元每年";
    }
}
