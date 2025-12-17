package com.positivehotel.nabusi_server.policy.notification.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyNotificationAdminServiceImpl implements PolicyNotificationAdminService{
    private final PolicyNotificationService policyNotificationService;
}
