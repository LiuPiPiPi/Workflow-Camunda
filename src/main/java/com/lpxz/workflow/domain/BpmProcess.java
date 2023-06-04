package com.lpxz.workflow.domain;

import com.lpxz.workflow.common.domain.BaseEntity;
import lombok.Data;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@Data
public class BpmProcess extends BaseEntity {
    /**
     * 任务 ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 实例状态 1激活 2挂起
     */
    private String suspendState;

    /**
     * 已激活/已挂起
     */
    private String suspendStateName;
}
