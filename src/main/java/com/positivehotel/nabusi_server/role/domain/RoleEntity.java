package com.positivehotel.nabusi_server.role.domain;

import com.positivehotel.nabusi_server.memberPackage.member.domain.MemberEntity;
import com.positivehotel.nabusi_server.urlPattern.domain.UrlPatternEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="role")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Long centerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_url_pattern",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "url_pattern_id")
    )
    private List<UrlPatternEntity> urlPatterns;

    @ManyToMany(mappedBy = "roles")
    private List<MemberEntity> members;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static RoleEntity create(String name, String description, Long centerId, List<UrlPatternEntity> urlPatternEntityList) {
        return RoleEntity.builder()
                .name(name)
                .description(description)
                .centerId(centerId)
                .urlPatterns(urlPatternEntityList)
                .build();
    }

    public void patchUrlPatterns(List<UrlPatternEntity> urlPatternEntityList) {
        setUrlPatterns(urlPatternEntityList);
    }
}
