package com.lpxz.workflow.controller.camunda;

import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@RestController
@RequestMapping("/bpm/user")
public class BpmUserController {

    @Autowired
    private IdentityService identityService;

    @ApiOperation(value = "启动流程", notes = "根据流程定义key启动最新版本并且无租户的流程")
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST, consumes = "application/json")
    public void saveUser(@RequestBody UserEntity userEntity) throws Exception {
        userEntity.setId("zcc1");
        userEntity.setEmail("zcc@qq.com");
        userEntity.setFirstName("zcc");
        userEntity.setLastName("zcc");
        userEntity.setDbPassword("1");
        userEntity.setSalt("1");
        identityService.saveUser(userEntity);
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @RequestMapping(value = "/UserQuery/{first}", method = RequestMethod.POST, consumes = "application/json")
    public void createUserQuery(@PathVariable String first) {
        UserQuery userQuery = identityService.createUserQuery().userFirstName(first);
        List<User> list = userQuery.list();
        // 需要分页到话
        // int maxResult=3;
        //        int fistResult=maxResult*(1-1);
        //        UserQuery userQuery = identityService.createUserQuery();
        for (User user : list) {
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            System.out.println(user.getId());
            System.out.println(user.getPassword());
            System.out.println(user.getEmail());
        }
    }
}
