package com.positivehotel.nabusi_server.memberPackage.memberAddress.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_address")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private String detailAddress;
    private String recipient;
    private String mobile;
    private String zipCode;
    private String roadName;
    private String isDefault;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static MemberAddressEntity create(Long memberId, String name, String address, String detailAddress, String recipient, String mobile, String zipCode, String roadName, String isDefault) {
        return MemberAddressEntity.builder()
                .memberId(memberId)
                .name(name)
                .address(address)
                .detailAddress(detailAddress)
                .recipient(recipient)
                .mobile(mobile)
                .zipCode(zipCode)
                .roadName(roadName)
                .isDefault(isDefault)
                .build();
    }

    public void update(String name, String address, String detailAddress, String recipient, String mobile, String zipCode, String roadName, String isDefault) {
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
        this.recipient = recipient;
        this.mobile = mobile;
        this.zipCode = zipCode;
        this.roadName = roadName;
        this.isDefault = isDefault;
    }

    public void updateIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
