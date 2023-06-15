package com.lpxz.workflow.controller.camunda;

import com.lpxz.workflow.common.BaseController;
import com.lpxz.workflow.domain.TaskVo;
import com.lpxz.workflow.util.Resp;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("获取任务列表")
    @GetMapping("/getTasks")
    public Resp findTasks() {
        return success(taskService.createTaskQuery().list());
    }

    @ApiOperation("根据负责人获取任务列表")
    @GetMapping("/getTasks/{assignee}")
    public List<Task> getTasksForAssignee(@PathVariable String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @ApiOperation("任务审批完成")
    @PostMapping("/complete")
    public Resp completeTask(String taskId) {
        taskService.complete(taskId);
        logger.info("任务审批完成...");
        return success();
    }
}
