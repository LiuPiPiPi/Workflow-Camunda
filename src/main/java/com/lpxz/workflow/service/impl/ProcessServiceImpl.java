package com.lpxz.workflow.service.impl;

import com.lpxz.workflow.domain.TaskVo;
import com.lpxz.workflow.service.IProcessService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@Service
public class ProcessServiceImpl implements IProcessService {
    @Autowired
    private HistoryService historyService;

    @Override
    public <T> void submitApply(T entity, String key) throws Exception {
    }

    @Override
    public <T> void submitApply(T entity, String key, Map<String, Object> variables) throws Exception {
    }

    @Override
    public <T> void richProcessField(T entity) throws Exception {
    }

    @Override
    public List<TaskVo> findTodoTasks(TaskVo taskVo) {
        return null;
    }

    @Override
    public void complete(String taskId, String instanceId, String variables) {
    }

    @Override
    public List<TaskVo> findDoneTasks(TaskVo taskVo) {

//        return historyService
//                .createHistoricTaskInstanceQuery()
//                .processDefinitionKey(taskVo.getTaskId())
//                .taskAssignee(taskVo.getUserId())
//                .finished()
//                .orderByHistoricTaskInstanceEndTime()
//                .desc()
//                .list();
        return null;
    }
}
