package com.lpxz.workflow;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/4/18
 */
@SpringBootTest(classes = WorkFlowApplication.class)
public class ApplicationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void startFlow() {
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("workflow-demo-process:3:3da1e72b-dd86-11ed-a34d-88b111942bc6");
        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
    }

    @Test
    public void queryTask() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("admin")
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("task.getId() = " + task.getId());
                System.out.println("task.getAssignee() = " + task.getAssignee());
            }
        }
    }

    @Test
    public void completeTask() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("admin")
                .list();
        Task task = tasks.get(0);
        if (task != null) {
            taskService.complete(task.getId());
            System.out.println("任务审批完成...");
        }
    }
}
