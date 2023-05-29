package com.lpxz.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class WorkFlowApplication {

    public static void main(String... args) {
        SpringApplication.run(WorkFlowApplication.class, args);
    }

    @Autowired
    private RuntimeService runtimeService;

//    @EventListener
//    private void processPostDeploy(PostDeployEvent event) {
//        runtimeService.startProcessInstanceByKey("loan-approval");
//    }

}