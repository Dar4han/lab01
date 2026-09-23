package kz.iitu.spring_lab_01.web;

import kz.iitu.spring_lab_01.config.EnvironmentBanner;
import kz.iitu.spring_lab_01.config.AppProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(AppProperties props, EnvironmentBanner banner, Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        return Map.of(
                "owner",                props.owner(),
                "group",                props.group(),
                "mailFrom",             props.mail().from(),
                "mailRetryCount",       props.mail().retryCount(),
                "paginationDefaultSize",props.pagination().defaultSize(), // <--- Поле 1
                "paginationMaxSize",    props.pagination().maxSize(),     // <--- Поле 2
                "serverPort",           environment.getProperty("server.port"),
                "activeProfiles",       Arrays.asList(environment.getActiveProfiles()),
                "banner",               banner.describe()
        );
    }
}