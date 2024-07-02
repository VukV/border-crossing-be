package vukv.bordercrossingbe;

import org.springframework.boot.SpringApplication;

public class TestBorderCrossingBeApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
