package com.lpxz.workflow.service.impl;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import com.lpxz.workflow.enums.ProcessKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Service
public class ProcessService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String startProcess(ProcessKey processKey) {
        return startProcess(processKey, null);
    }

    public String startProcess(ProcessKey processKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey.name(), variables);
        return processInstance.getId();
    }

    public List<Task> getTasks() {
        return taskService.createTaskQuery().list();
    }

    public List<Task> getTasksForAssignee(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void completeTask(String taskId) {
        completeTask(taskId, null);
    }

    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

}
