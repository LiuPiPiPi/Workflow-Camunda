package com.lpxz.workflow.delegate.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Component("qualificationChecker")
public class QualificationCheckerDelegate implements JavaDelegate {

    private final static Logger logger = Logger.getLogger(QualificationCheckerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("开始查验申请人的保险资格");

        String isQualified = checkInsuranceQualification(execution);
        execution.setVariable("isQualified", isQualified);
    }

    private String checkInsuranceQualification(DelegateExecution execution) {
        Boolean hasSocialSecurity = (Boolean) execution.getVariable("hasSocialSecurity");
        Boolean hasOtherInsurance = (Boolean) execution.getVariable("hasOtherInsurance");

        // TODO: 检查申请人资格。此处仅仅是根据申请人是否有社保和商业保险来进行判断
        String result = "";
        if (hasSocialSecurity) {
            if (hasOtherInsurance) {
                result = "yes";
                logger.info("申请人保险资格查验结果：合格");
            } else {
                result = "other";
                logger.info("申请人保险资格查验结果：需人工审核");
            }
        } else {
            result = "no";
            logger.info("申请人保险资格查验结果：不合格");
        }
        return result;
    }
}
