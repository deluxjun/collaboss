package com.jj.sbp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationListener implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private Environment environment;

    public void logActiveProfiles() {
        for (String profileName : environment.getActiveProfiles()) {
            log.info("Currently active profile - " + profileName);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("============================");
        log.info("    Application started");
        logActiveProfiles();
        log.info("============================");
    }
}
