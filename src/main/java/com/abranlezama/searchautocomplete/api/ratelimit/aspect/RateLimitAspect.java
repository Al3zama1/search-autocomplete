package com.abranlezama.searchautocomplete.api.ratelimit.aspect;

import com.abranlezama.searchautocomplete.api.exception.RateLimitException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class RateLimitAspect {
    public static final String ERROR_MESSAGE = "Too many requests at endpoint %s from IP %s!. Please try again after %d milliseconds!.";
    private final ConcurrentHashMap<String, List<Long>> userRequestCounts = new ConcurrentHashMap<>();

    @Value("${ratelimit.limit:#{200}}")
    private int rateLimit;
    @Value("${ratelimit.duration:#{60000}}")
    private long rateDuration;


    @Before("@annotation(com.abranlezama.searchautocomplete.api.ratelimit.annotation.RateLimitProtection))")
    public void rateLimit() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final String key = requestAttributes.getRequest().getRemoteAddr();
        final long currentTime = System.currentTimeMillis();
        userRequestCounts.putIfAbsent(key, new ArrayList<>());
        userRequestCounts.get(key).add(currentTime);
        cleanUpRequestCounts(key, currentTime);
        if (userRequestCounts.get(key).size() > rateLimit) {
            throw new RateLimitException(String.format(ERROR_MESSAGE, requestAttributes.getRequest().getRequestURI(), key, rateDuration));
        }
    }

    private void cleanUpRequestCounts(final String key, final long currentTime) {
        userRequestCounts.get(key).removeIf(t -> timeIsTooOld(currentTime, t));
    }

    private boolean timeIsTooOld(final long currentTime, final long timeToCheck) {
        return currentTime - timeToCheck > rateDuration;
    }
}
