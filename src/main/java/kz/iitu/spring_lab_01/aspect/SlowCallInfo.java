package kz.iitu.spring_lab_01.aspect;

import java.time.LocalDateTime;

public class SlowCallInfo {
    private final String methodName;
    private final long executionTimeMs;
    private final LocalDateTime timestamp;

    public SlowCallInfo(String methodName, long executionTimeMs) {
        this.methodName = methodName;
        this.executionTimeMs = executionTimeMs;
        this.timestamp = LocalDateTime.now();
    }

    public String getMethodName() {
        return methodName;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}