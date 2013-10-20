package hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class AppMVC {

    public static void main(String[] args) {
        SpringApplication.run(AppMVC.class, args);
    }

}