package com.motiolab.nabusi_server.policy.notification.application;

import com.motiolab.nabusi_server.policy.notification.domain.PolicyNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyNotificationServiceImpl implements PolicyNotificationService{
    private final PolicyNotificationRepository policyNotificationRepository;
}
