package com.lpxz.workflow.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@Data
public class TaskVo {

    private String taskId;

    private String taskName;

    private String instanceId;

    private String suspendState;

    private String suspendStateName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Map<?, ?> formData;

    private String userId;

    private Integer pageNum;

    private Integer pageSize;

    private Integer offset;

    private String assignee;

    private String assigneeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 类型 0todo 待办 done 已办
     */
    private String type;
}
