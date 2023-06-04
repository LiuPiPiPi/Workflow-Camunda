package com.lpxz.workflow.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Data
@NoArgsConstructor
public class SysGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupId;

    private String groupName;
}
