package com.motiolab.nabusi_server.memberPackage.member.domain;

import com.motiolab.nabusi_server.role.domain.RoleEntity;
import com.motiolab.nabusi_server.util.ListToLongConverter;
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
        name = "member",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_mobile_social_name",
                columnNames = {"mobile", "socialName"}
        )
)
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String mobile;
    private String socialName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles;
    @Convert(converter = ListToLongConverter.class)
    private List<Long> centerIdList;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static MemberEntity create(String name, String email, String mobile, String socialName, List<RoleEntity> roles, List<Long> centerIdList) {
        return MemberEntity.builder()
                .name(name)
                .email(email)
                .mobile(mobile)
                .socialName(socialName)
                .roles(roles)
                .centerIdList(centerIdList)
                .build();
    }

    public void updateCenterIdList(List<Long> centerIdList) {
        setCenterIdList(centerIdList);
    }

    public void updateRoleList(List<RoleEntity> roleEntityList) {
        setRoles(roleEntityList);
    }

    public void updateMobile(String mobile) {
        setMobile(mobile);
    }
}
