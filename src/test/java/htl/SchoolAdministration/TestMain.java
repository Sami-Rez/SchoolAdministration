package htl.SchoolAdministration;


import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestMain {



    public static void main(String[] args) {
        SpringApplication.from(SchoolAdministrationApplication::main)
                .with(TestMain.class)
                /*     .with(TestContainerConfiguration.class)*/
                .run(args);
    }

    @Bean
    @ServiceConnection
        // kümmert sich um die Verbindung zur DB
    PostgreSQLContainer<?> postgresContainer() {
        final int exposedPort = 5432;
        final int localPort = 15432;
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withExposedPorts(exposedPort)
                .withCreateContainerCmdModifier(cmd -> {
                    cmd.withName("schoolAdministration-postgresql");
                    cmd.withHostConfig(new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(exposedPort))));
                })
                .withReuse(true);
    }
}


