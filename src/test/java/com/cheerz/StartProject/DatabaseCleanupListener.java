package com.cheerz.StartProject;

import org.flywaydb.core.Flyway;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class DatabaseCleanupListener implements TestExecutionListener {
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        ConfigurableApplicationContext springApplication = SpringApplication.run(
            StartProjectApplication.class,
            "--spring.profiles.active=test"
        );

        try {
            Flyway flyway = springApplication.getBean(Flyway.class);
            flyway.clean();
        } finally {
            SpringApplication.exit(springApplication);
        }
    }
}

