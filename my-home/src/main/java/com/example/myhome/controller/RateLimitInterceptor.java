package com.example.myhome.controller;

import com.example.myhome.domain.SystemAlarm;
import com.example.myhome.repository.SystemAlarmRepository;
import com.example.myhome.utils.TokenUtils;
import io.github.bucket4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private SystemAlarmRepository systemAlarmRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authToken = tokenUtils.getToken(request);
        String username = tokenUtils.getUsernameFromToken(authToken);

        if(username == null){
            username = "non-authorized-methods";
        }
        Bucket tokenBucket  = cache.computeIfAbsent(username, this::newBucket);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            //ovde alarm
            SystemAlarm alarm = new SystemAlarm();
            alarm.setDate(new Date());
            alarm.setMessage("User with username: " + username + " is spamming requests");
            systemAlarmRepository.save(alarm);

            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                    "You have exhausted your API Request Quota");
            return false;
        }
    }

    private Bucket newBucket(String s) {
        if(s.equals("non-authorized-methods")){
            return Bucket4j.builder()
                    .addLimit(Bandwidth.classic(20, Refill.intervally(100, Duration.ofMinutes(1))))
                    .build();
        }
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(20, Refill.intervally(20, Duration.ofMinutes(1))))
                .build();
    }

}
