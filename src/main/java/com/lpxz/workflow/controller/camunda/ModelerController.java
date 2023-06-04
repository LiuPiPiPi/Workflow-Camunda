package com.lpxz.workflow.controller.camunda;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@RestController
@RequestMapping("/modeler")
public class ModelerController {

    @Autowired
    private RepositoryService repositoryService;

//    public void test(String modelId) {
//        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
//        deploymentQuery.orderByDeploymentTime();
//
//        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(modelId);
//    }

}
