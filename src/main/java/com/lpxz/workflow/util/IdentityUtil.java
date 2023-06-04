package com.lpxz.workflow.util;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import com.lpxz.workflow.domain.SysUser;

/**
 * @author LPxz
 * @date 2023/5/29
 */
public class IdentityUtil {
    /**
     * 将自定义用户实体类转换为 Camunda 的 User
     *
     * @param user
     * @return
     */
    public static User toCamundaUser(SysUser user) {
        if (user != null) {
            User userEntity = new UserEntity();
            userEntity.setId(user.getUserAccount());
            if (user.getNickName() != null) {
                userEntity.setFirstName(user.getNickName());
            }
            userEntity.setEmail(user.getUserEmail());
            userEntity.setPassword(user.getUserPassword());
            return userEntity;
        }
        return null;
    }

    /**
     * 将 Camunda 的 User 转换为自定义用户实体类
     *
     * @param user
     * @return
     */
    public static SysUser toCustomUser(User user) {
        if (user != null) {
            SysUser sysUser = new SysUser();
            sysUser.setUserAccount(user.getId());
            sysUser.setNickName(user.getFirstName());
            sysUser.setUserEmail(user.getEmail());
            sysUser.setUserPassword(user.getPassword());
            return sysUser;
        }
        return null;
    }
}
