package com.inzent.sbp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationListener implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("============================");
        log.info("Application started");
        log.info("============================");
        log.debug("[ApplicationListener]");
    }
}
