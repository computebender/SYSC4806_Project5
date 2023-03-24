package ca.carleton.AmazinBookStore.jbehave;

import org.jbehave.core.configuration.Configuration;
        import org.jbehave.core.configuration.MostUsefulConfiguration;
        import org.jbehave.core.junit.JUnitStories;
        import org.jbehave.core.reporters.ConsoleOutput;
        import org.jbehave.core.reporters.StoryReporterBuilder;
        import org.jbehave.core.steps.InjectableStepsFactory;
        import org.jbehave.core.steps.InstanceStepsFactory;

        import java.util.Arrays;
        import java.util.List;

public class LoginRunner extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats());
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new LoginSteps());
    }

    @Override
    public List<String> storyPaths() {
        return Arrays.asList("ca/carleton/AmazinBookStore/bookstore.story");
    }
}