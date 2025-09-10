package group.wimdev.lf08_store.testcontainers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:14"))
            .withDatabaseName("lf08_store")
            .withUsername("lf08_store")
            .withPassword("lf08_store")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        postgres.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword())
                .applyTo(configurableApplicationContext.getEnvironment());
    }
}
