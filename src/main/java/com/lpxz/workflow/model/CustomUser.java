package com.lpxz.workflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Data
@NoArgsConstructor
public class CustomUser implements Serializable {
    private String email;

    private String realName;

    private String userName;

    private String password;
}
