package com.positivehotel.nabusi_server.policy.notification.ui;

import com.positivehotel.nabusi_server.policy.notification.application.PolicyNotificationAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PolicyNotificationAdminController {
    private final PolicyNotificationAdminService policyNotificationAdminService;
}
