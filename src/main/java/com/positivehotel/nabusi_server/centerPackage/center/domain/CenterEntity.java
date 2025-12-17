package com.positivehotel.nabusi_server.centerPackage.center.domain;

import com.positivehotel.nabusi_server.util.ListToLongConverter;
import com.positivehotel.nabusi_server.util.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(
        name = "center",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_road_name_name",
                columnNames = {"roadName", "name"}
        )
)
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class CenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Column(length = 50)
    private String code;
    private String detailAddress;
    private String roadName;
    private String description;
    private Boolean isActive;

    @Convert(converter = ListToLongConverter.class)
    private List<Long> memberIdList;

    @Convert(converter = StringListConverter.class)
    private List<String> imageUrlList;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static CenterEntity create(String name, String address, String code, String detailAddress, String roadName, Boolean isActive, List<Long> memberIdList) {
        return CenterEntity.builder()
                .name(name)
                .address(address)
                .code(code)
                .detailAddress(detailAddress)
                .roadName(roadName)
                .isActive(isActive)
                .memberIdList(memberIdList)
                .build();
    }

    public void update(String name, String address, String code, String detailAddress, String roadName, String description, List<Long> memberIdList, List<String> imageUrlList) {
        this.name = name;
        this.address = address;
        this.code = code;
        this.detailAddress = detailAddress;
        this.roadName = roadName;
        this.description = description;
        this.memberIdList = memberIdList;
        this.imageUrlList = imageUrlList;
    }
}
