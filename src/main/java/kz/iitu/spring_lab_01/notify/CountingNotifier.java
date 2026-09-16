package kz.iitu.spring_lab_01.notify;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("counting")
@Order(3)
public class CountingNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(CountingNotifier.class);

    @PostConstruct
    public void init() {
        log.info("CUSTOM BEAN >> CountingNotifier initialized!");
    }

    @Override
    public String send(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "counting: " + message + " (words: 0, chars: 0)";
        }

        int charCount = message.length();
        int wordCount = message.trim().split("\\s+").length;

        String result = String.format("%s (words: %d, chars: %d)", message, wordCount, charCount);
        log.info("COUNTING >> {}", result);
        return "counting: " + result;
    }

    @Override
    public String channel() {
        return "counting";
    }
}