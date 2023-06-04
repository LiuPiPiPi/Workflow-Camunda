package com.lpxz.workflow.controller.camunda;

import com.lpxz.workflow.common.BaseController;
import com.lpxz.workflow.domain.BpmProcess;
import com.lpxz.workflow.domain.TaskVo;
import com.lpxz.workflow.service.IProcessService;
import com.lpxz.workflow.util.Resp;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/4/17
 */
@RestController
@RequestMapping("/process")
public class ProcessController extends BaseController {

    private final static Logger logger = Logger.getLogger(ProcessController.class.getName());

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IProcessService processService;

    @ApiOperation(value = "查看已部署流程")
    @PostMapping("/list")
    public Resp list() {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> deploymentList = deploymentQuery.list();
        List<BpmProcess> processList = new ArrayList<>();
        for (Deployment deployment : deploymentList) {
            BpmProcess process = new BpmProcess();
            process.setTaskId(deployment.getId());
            process.setTaskName(deployment.getName());
            process.setCreateTime(deployment.getDeploymentTime());
            processList.add(process);
        }
        return success(processList);
//        return getDataTable(list);
    }

    @ApiOperation(value = "部署流程")
    @PostMapping("/deploy/{filePath}")
    public Resp deploy(@PathVariable String filePath) {
        Deployment deploy = repositoryService.createDeployment()
                .name(filePath)
                .addClasspathResource(filePath + ".bpmn")
                .deploy();
        logger.info("[deployment] success! id = " + deploy.getId());
        return success(deploy);
    }

    @ApiOperation(value = "启动流程实例")
    @RequestMapping("/start")
    public Resp startProcess(String processKey) {
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        logger.info("processInstance.getId() = " + processInstance.getId());
        logger.info("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
        return success(processInstance);
    }

    @ApiOperation(value = "查询待办")
    @RequestMapping("/query/{assignee}")
    public Resp queryTask(@PathVariable String assignee) {
        List<Task> list = taskService.createTaskQuery()
//                .processInstanceBusinessKey(businessKey)
                .taskAssignee(assignee)
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                logger.info("task.getId() = " + task.getId());
                logger.info("task.getAssignee() = " + task.getAssignee());
            }
        }
        return success(list);
    }

    @RequestMapping("/complete/{taskId}")
    public Resp completeTask(@PathVariable String taskId) {
        taskService.complete(taskId);
        logger.info("任务审批完成...");
        return success();
    }

    /**
     * 我的已办列表
     */
    @GetMapping("/taskDoneList")
    @ResponseBody
    public Resp taskDoneList(TaskVo taskVo) {
        return success(processService.findDoneTasks(taskVo));
    }
}
