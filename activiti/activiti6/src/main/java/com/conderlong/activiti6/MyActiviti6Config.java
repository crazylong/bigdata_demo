package com.conderlong.activiti6;

import org.springframework.stereotype.Component;

/**
 * @author LongQiong
 * @date 2021/3/2
 */
@Component
public class MyActiviti6Config {
//    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//    @Autowired
//    ProcessEngineConfiguration processEngineConfiguration;
//
//

//    @Bean
//    public BeanPostProcessor activitiConfigurer() {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) {
//                if (bean instanceof SpringProcessEngineConfiguration) {
//                   // ((SpringProcessEngineConfiguration) bean).setActivityBehaviorFactory(rcActivitiBehaviourFactory);
//                    ((SpringProcessEngineConfiguration) bean).setDatabaseSchema("SC_QC");
//                }
//                return bean;
//            }
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) {
//                return bean;
//            }
//        };
//    }
}
