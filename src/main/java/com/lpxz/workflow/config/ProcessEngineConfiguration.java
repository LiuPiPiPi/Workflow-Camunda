package com.lpxz.workflow.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Configuration
@Order(1)
public class ProcessEngineConfiguration implements ProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        List<BpmnParseListener> bpmnParseListener = new ArrayList<>();
//        bpmnParseListener.add(new LoggingExecutionParseListener());
//        bpmnParseListener.add(new LoggingStartEndEventExecutionParseListener());
//        bpmnParseListener.add(new LoggingTransitionParseListener());
//        bpmnParseListener.add(new LoggingUserTaskExecutionParseListener());
        processEngineConfiguration.setCustomPostBPMNParseListeners(bpmnParseListener);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }

    //    @Bean
//    public ProcessEnginePlugin administratorAuthorizationPlugin() {
//        AdministratorAuthorizationPlugin administratorAuthorizationPlugin = new AdministratorAuthorizationPlugin();
//        administratorAuthorizationPlugin.setAdministratorGroupName("admins");
//        return administratorAuthorizationPlugin;
//    }

    //    @Bean
//    public ProcessEngineConfigurationImpl processEngineConfiguration() {
//        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
//        // 禁用用户身份模块
//        processEngineConfiguration.setDbIdentityUsed(false);
//        return processEngineConfiguration;
//    }

//    @Bean
//    public ProcessEnginePlugin identityProviderPlugin(UserIdentityService userIdentityService) {
//        return new JpaIdentityProviderPlugin(userIdentityService);
//    }
}
