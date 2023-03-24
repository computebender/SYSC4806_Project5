//package ca.carleton.AmazinBookStore.jbehave;
//
//import org.jbehave.core.steps.InjectableStepsFactory;
//import org.jbehave.core.steps.spring.SpringStepsFactory;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableAutoConfiguration
//public class JBehaveConfiguration extends SpringBootJBehaveConfiguration {
//
//    @Override
//    public List<String> storyPaths() {
//        return Arrays.asList("classpath:search.feature");
//    }
//
//    @Override
//    public InjectableStepsFactory stepsFactory() {
//        return new SpringStepsFactory(configuration(), new LoginSteps());
//    }
//}
