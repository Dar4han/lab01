package kz.iitu.spring_lab_01.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Aspect
@Component
public class SlowMethodAspect {

    private static final Logger log = LoggerFactory.getLogger(SlowMethodAspect.class);

    // Порог внедряется из конфигурации application.properties / application.yml
    @Value("${app.aspect.slow-threshold-ms:200}")
    private long slowThresholdMs;

    // Потокбезопасный список для сохранения информации о медленных вызовах
    private final List<SlowCallInfo> slowCalls = new CopyOnWriteArrayList<>();

    @Around("kz.iitu.spring_lab_01.aspect.Pointcuts.serviceOperation()")
    public Object logSlowMethod(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed(); // Выполняем целевой метод
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > slowThresholdMs) {
                String methodName = pjp.getSignature().toShortString();
                log.warn("[SLOW LOG] Метод {} выполнялся {} мс (порог: {} мс)", 
                         methodName, duration, slowThresholdMs);
                slowCalls.add(new SlowCallInfo(methodName, duration));
            }
        }
    }

    // Метод для получения всех зарегистрированных медленных вызовов
    public List<SlowCallInfo> getSlowCalls() {
        return Collections.unmodifiableList(slowCalls);
    }
}