package kz.iitu.spring_lab_01;

import kz.iitu.spring_lab_01.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringLab01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLab01Application.class, args);
    }
}