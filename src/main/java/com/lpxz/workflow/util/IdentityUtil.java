package com.lpxz.workflow.util;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import com.lpxz.workflow.model.CustomUser;

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
    public static User toCamundaUser(CustomUser user) {
        if (user != null) {
            User userEntity = new UserEntity();
            userEntity.setId(user.getUserName());
            if (user.getRealName() != null) {
                userEntity.setFirstName(user.getRealName());
            }
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(user.getPassword());
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
    public static CustomUser toCustomUser(User user) {
        if (user != null) {
            CustomUser customUser = new CustomUser();
            customUser.setUserName(user.getId());
            customUser.setRealName(user.getFirstName());
            customUser.setEmail(user.getEmail());
            customUser.setPassword(user.getPassword());
            return customUser;
        }
        return null;
    }
}
