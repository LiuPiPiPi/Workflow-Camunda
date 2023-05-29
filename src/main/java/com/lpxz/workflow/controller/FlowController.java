package com.lpxz.workflow.controller;

import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import com.lpxz.workflow.delegate.OfferLoanDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/4/17
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    private final static Logger logger = Logger.getLogger(FlowController.class.getName());

    @ApiOperation(value = "部署流程")
    @GetMapping("/deploy")
    public void deploy() {
//        if (repositoryService.getProcessDefinition("insurance:1:ab8f854a-fd3f-11ed-9a0c-88b111942bc6") != null) {
//            repositoryService.deleteProcessDefinition("insurance:1:ab8f854a-fd3f-11ed-9a0c-88b111942bc6");
//        }
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("insurance/insurance.bpmn")
                .addClasspathResource("insurance/reject_insurance.bpmn")
                .addClasspathResource("insurance/calculate-premium.dmn")
                .deploy();
        logger.info("[deployment] success! id = " + deploy.getId());
    }

    @ApiOperation(value = "开启流程实例")
    @RequestMapping("/start")
    public void startFlow() {
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("workflow-demo-process:3:3da1e72b-dd86-11ed-a34d-88b111942bc6");
        logger.info("processInstance.getId() = " + processInstance.getId());
        logger.info("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
    }

    @RequestMapping("/query")
    public void queryTask() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("admin")
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                logger.info("task.getId() = " + task.getId());
                logger.info("task.getAssignee() = " + task.getAssignee());
            }
        }
    }

    @RequestMapping("/complete")
    public void completeTask() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("admin")
                .list();
        Task task = tasks.get(0);
        if (task != null) {
            taskService.complete(task.getId());
            logger.info("任务审批完成...");
        }
    }
}
