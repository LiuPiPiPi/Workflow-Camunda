package com.lpxz.workflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Data
@NoArgsConstructor
public class CustomGroup implements Serializable {
    private String groupId;

    private String groupName;
}
