package com.motiolab.nabusi_server.schedule.ui;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleAdminController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleAdminController.class);

    @GetMapping("/v1/admin/schedule/{centerId}")
    public ResponseEntity<Boolean> getUser(Long centerId) {
        log.info("test");
        return ResponseEntity.ok(true);
    }
}
