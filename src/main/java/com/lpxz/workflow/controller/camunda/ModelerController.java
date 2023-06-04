package com.lpxz.workflow.controller.camunda;

import com.lpxz.workflow.common.BaseController;
import com.lpxz.workflow.util.Resp;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@RestController
@RequestMapping("/modeler")
public class ModelerController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/list")
    public Resp list(ProcessDefinition definition) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        return success(deploymentQuery.list());
    }

    @PostMapping("/insert")
    public Resp insert(ProcessDefinition definition) {
//        DeploymentQuery deploymentQuery = repositoryService.getDeploymentResources();
//        return Resp.success(deploymentQuery());

        return null;
    }

    @PostMapping("/update")
    public Resp insert() {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
//        return Resp.success(deploymentQuery());
        return null;
    }

    @DeleteMapping("/delete")
    public Resp delete() {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
//        return Resp.success(deploymentQuery());
        return null;
    }
}
