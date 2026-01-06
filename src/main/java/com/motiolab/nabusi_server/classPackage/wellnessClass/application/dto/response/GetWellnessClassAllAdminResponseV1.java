package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
public class GetWellnessClassAllAdminResponseV1 {
    Long id;
    String name;
    String description;
    String teacherName;
    String room;
    String type;
    List<Ticket> ticketList;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ticket {
        String name;
        String type;
        String backgroundColor;
        String textColor;
    }
}
