package com.lpxz.workflow.config;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LPxz
 * @date 2023/5/28
 */
//@Configuration
public class ProcessEngineConfiguration {

//    @Bean
    public ProcessEnginePlugin administratorAuthorizationPlugin() {
        AdministratorAuthorizationPlugin administratorAuthorizationPlugin = new AdministratorAuthorizationPlugin();
        administratorAuthorizationPlugin.setAdministratorGroupName("admins");
        return administratorAuthorizationPlugin;
    }

//    @Bean
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        // 禁用用户身份模块
        processEngineConfiguration.setDbIdentityUsed(false);
        return processEngineConfiguration;
    }

//    @Bean
//    public ProcessEnginePlugin identityProviderPlugin(UserIdentityService userIdentityService) {
//        return new JpaIdentityProviderPlugin(userIdentityService);
//    }
}
