package com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.response;

import java.util.List;

public record GetWellnessClassDetailAdminResponseV1 (
    Long id,
    String name,
    String description,
    Long centerId,
    Integer maxReservationCnt,
    Long registerId,
    String room,
    List<String> classImageUrlList,
    Long teacherId,
    Long wellnessClassProgramId,
    List<Long> issuedWellnessTicketManageIdList
){}


