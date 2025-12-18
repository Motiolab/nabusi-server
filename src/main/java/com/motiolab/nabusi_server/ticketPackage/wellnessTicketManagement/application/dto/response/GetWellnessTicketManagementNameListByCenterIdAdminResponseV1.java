package com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetWellnessTicketManagementNameListByCenterIdAdminResponseV1 {
    private Long id;
    private String wellnessTicketIssuanceName;
    private Long wellnessTicketId;
    private String wellnessTicketName;
}
