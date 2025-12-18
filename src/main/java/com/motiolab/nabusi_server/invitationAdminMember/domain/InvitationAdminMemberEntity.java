package com.motiolab.nabusi_server.invitationAdminMember.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="invitation_admin_member")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class InvitationAdminMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobile;
    private Long centerId;
    private Boolean isAccept;
    private Long sendAdminMemberId;
    private Long roleId;
    private String code;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static InvitationAdminMemberEntity create(String mobile, Long centerId, Boolean isAccept, Long sendAdminMemberId, Long roleId, String code) {
        return InvitationAdminMemberEntity.builder()
                .mobile(mobile)
                .centerId(centerId)
                .isAccept(isAccept)
                .sendAdminMemberId(sendAdminMemberId)
                .roleId(roleId)
                .code(code)
                .build();
    }

    public void patch(String mobile, Long centerId, Boolean isAccept, Long sendAdminMemberId, Long roleId, String code) {
        if(mobile != null) this.mobile = mobile;
        if(centerId != null) this.centerId = centerId;
        if(isAccept != null) this.isAccept = isAccept;
        if(sendAdminMemberId != null) this.sendAdminMemberId = sendAdminMemberId;
        if(roleId != null) this.roleId = roleId;
        if(code != null) this.code = code;
    }
}
