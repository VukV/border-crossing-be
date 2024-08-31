package vukv.bordercrossingbe;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        DockerImageName imageName = DockerImageName
                .parse("timescale/timescaledb-ha:pg16")
                .asCompatibleSubstituteFor("postgres");

        return new PostgreSQLContainer<>(imageName);
    }

}
