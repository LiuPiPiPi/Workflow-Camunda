package com.lpxz.workflow;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ProcessScenarioTest {

    private static final String PROCESS_DEFINITION_KEY = "insurance";

    private Map<String, Object> variables;

//    @Autowired
//    private ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    @Deployment
    public void ruleUsageExample() {
        RuntimeService runtimeService = processEngineRule.getRuntimeService();
        runtimeService.startProcessInstanceByKey("ruleUsage");

        TaskService taskService = processEngineRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        Assert.assertEquals("My Task", task.getName());

        taskService.complete(task.getId());
        Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }

    @Test
    @Deployment(resources = {"insurance.bpmn", "reject_insurance.bpmn"})
    public void testNotQualified() {
        variables = Variables.createVariables();
        variables.put("id", "id");
        variables.put("name", "name");
        variables.put("hasSocialSecurity", false);
        variables.put("hasOtherInsurance", false);

        repositoryService.createDeployment().name("部署保险流程")
                .addClasspathResource("insurance.bpmn")
                .deploy();

//        ExecutableRunner starter = Scenario.run(myProcess).startByKey(PROCESS_DEFINITION_KEY, variables);
//
//        // OK - everything prepared - let's go and execute the scenario
//        Scenario scenario = starter.execute();
//
//        // now you can do some assertions
//        verify(myProcess).hasFinished("EndEvent_InsuranceRejected_NotQualify");

    }

//    @PostConstruct
//    void initRule() {
//        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
//    }

//    @Before
//    public void setup() {
//        init(processEngine);
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Mock
//    private ProcessScenario myProcess;
//
//    @Test
//    @Deployment(resources = "insurance.bpmn") // only required for process test coverage
//    public void testHappyPath() {
//        // Define scenarios by using camunda-bpm-assert-scenario:
//        variables = Variables.createVariables();
//        variables.put("id", "id");
//        variables.put("name", "name");
//        variables.put("hasSocialSecurity", true);
//        variables.put("hasOtherInsurance", true);
//
//        ExecutableRunner starter = Scenario.run(myProcess).startByKey(PROCESS_DEFINITION_KEY, variables);
//
//        when(myProcess.waitsAtMessageIntermediateCatchEvent("IntermediateThrowEvent_ReceiveInsuranceContract")).
//                thenReturn(new MessageIntermediateCatchEventAction() {
//                    @Override
//                    public void execute(EventSubscriptionDelegate message) throws Exception {
//                        message.receive();
//                    }
//                });
//
//        // OK - everything prepared - let's go and execute the scenario
//        Scenario scenario = starter.execute();
//
//        // now you can do some assertions
//        verify(myProcess).hasFinished("EndEvent_InsuranceConfirmed");
//    }
//
//    @Test
//    @Deployment(resources = "insurance.bpmn")
//    public void testManualCheckApproved() {
//        variables = Variables.createVariables();
//        variables.put("id", "id");
//        variables.put("name", "name");
//        variables.put("hasSocialSecurity", true);
//        variables.put("hasOtherInsurance", false);
//
//        ExecutableRunner starter = Scenario.run(myProcess).startByKey(PROCESS_DEFINITION_KEY, variables);
//
//        when(myProcess.waitsAtMessageIntermediateCatchEvent("IntermediateThrowEvent_ReceiveInsuranceContract")).
//                thenReturn(new MessageIntermediateCatchEventAction() {
//                    @Override
//                    public void execute(EventSubscriptionDelegate message) throws Exception {
//                        message.receive();
//                    }
//                });
//        when(myProcess.waitsAtUserTask("Task_CheckInsuranceCase")).thenReturn((task) -> {
//            task.complete(withVariables("isRiskManagable", true));
//        });
//
//        // OK - everything prepared - let's go and execute the scenario
//        Scenario scenario = starter.execute();
//
//        // now you can do some assertions
//        verify(myProcess).hasFinished("EndEvent_InsuranceConfirmed");
//    }
//
//    @Test
//    @Deployment(resources = {"insurance.bpmn", "reject_insurance.bpmn"})
//    public void testManualCheckRejected() {
//        variables = Variables.createVariables();
//        variables.put("id", "id");
//        variables.put("name", "name");
//        variables.put("hasSocialSecurity", true);
//        variables.put("hasOtherInsurance", false);
//
//        ExecutableRunner starter = Scenario.run(myProcess).startByKey(PROCESS_DEFINITION_KEY, variables);
//
//        when(myProcess.waitsAtUserTask("Task_CheckInsuranceCase")).thenReturn((task) -> {
//            task.complete(withVariables("isRiskManagable", false));
//        });
//
//        // OK - everything prepared - let's go and execute the scenario
//        Scenario scenario = starter.execute();
//
//        // now you can do some assertions
//        verify(myProcess).hasFinished("EndEvent_InsuranceRejected_Risky");
//    }
//
//    @Test
//    @Deployment(resources = "insurance.bpmn")
//    public void testNeedMoreDocuments() {
//        variables = Variables.createVariables();
//        variables.put("id", "id");
////    variables.put("name", "name");
//
//        ExecutableRunner starter = Scenario.run(myProcess).startByKey(PROCESS_DEFINITION_KEY, variables);
//
//        // OK - everything prepared - let's go and execute the scenario
//        Scenario scenario = starter.execute();
//
//        // now you can do some assertions
//        verify(myProcess).hasFinished("EndEvent_NeedMoreDocument");
//    }

}
