package com.lpxz.workflow.controller.camunda;

import com.lpxz.workflow.common.BaseController;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/6/7
 */
@RestController
@RequestMapping("/form")
public class FormController extends BaseController {

    @Autowired
    private FormService formService;

    @ApiOperation("根据流程实例 id 获取开始节点表单数据")
    @GetMapping("/getStartFormData/{processDefinitionId}")
    public void getStartFormData(@PathVariable String processDefinitionId) {
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        List<FormField> formFields = startFormData.getFormFields();
        for (FormField formField : formFields) {
            System.out.println("############");
            System.out.println(formField.getId());
            System.out.println(formField.getValue());
            System.out.println(formField.getType());
            System.out.println(formField.getTypeName());
            System.out.println(formField.getLabel());
            System.out.println(formField.getDefaultValue());
            System.out.println("############");
        }
    }

    @ApiOperation("根据任务 id 获取任务节点表单数据")
    @GetMapping("/getTaskFormData/{taskId}")
    public void getTaskFormData(@PathVariable String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        System.out.println("############");
        System.out.println(taskFormData.getDeploymentId());
        System.out.println(taskFormData.getTask());
        System.out.println(taskFormData.getFormKey());
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        System.out.println(formProperties);
        List<FormField> formFields = taskFormData.getFormFields();
        System.out.println(formFields);
        System.out.println("############");
    }

    @PostMapping("/submitTaskForm/{processDefinitionId}")
    public void submitStartForm(@PathVariable String processDefinitionId) {
        VariableMap variableMap = Variables.createVariables()
                .putValue("reason", "请假三天")
                .putValue("name", "张三");
        formService.submitStartForm(processDefinitionId, variableMap);
    }

    @PostMapping("/submitTaskForm/{taskId}")
    public void submitTaskForm(@PathVariable String taskId) {
        VariableMap variableMap = Variables.createVariables()
                .putValue("name", "王五")
                .putValue("reason", "年假");
        formService.submitTaskForm(taskId, variableMap);
    }
}
