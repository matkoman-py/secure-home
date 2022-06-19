package com.example.myhome.controller;

import com.example.myhome.domain.TestDrools;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    private final KieContainer kieContainer;

    public TestController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @GetMapping(value = "/getFDInterestRate")
    @PreAuthorize("hasAuthority('TEST_DROOLS')")
    public TestDrools getQuestions(@RequestParam(required = true) String bank, @RequestParam(required = true) Integer durationInYear) {
        KieSession kieSession = kieContainer.newKieSession();
        TestDrools fdRequest = new TestDrools(bank,durationInYear);
        kieSession.insert(fdRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return fdRequest;
    }
}
