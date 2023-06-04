package com.lpxz.workflow.service;

import com.lpxz.workflow.domain.TaskVo;

import java.util.List;
import java.util.Map;

/**
 * @author LPxz
 * @date 2023/6/4
 */
public interface IProcessService {
    /**
     * 提交申请
     */
    <T> void submitApply(T entity, String key) throws Exception;

    <T> void submitApply(T entity, String key, Map<String, Object> variables) throws Exception;

    /**
     * 填充流程相关字段
     */
    <T> void richProcessField(T entity) throws Exception;


    /**
     * 我的待办
     */
    List<TaskVo> findTodoTasks(TaskVo taskVo);

    /**
     * 办理任务
     */
    void complete(String taskId, String instanceId, String variables);

    /**
     * 我的已办
     */
    List<TaskVo> findDoneTasks(TaskVo taskVo);
}
