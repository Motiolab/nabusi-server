package com.positivehotel.nabusi_server.urlPattern.domain;

import com.positivehotel.nabusi_server.role.domain.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="url_pattern")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class UrlPatternEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionName;
    private String url;
    @Column(length = 10)
    private String method;
    private String description;
    private Long centerId;

    @ManyToMany(mappedBy = "urlPatterns")
    private List<RoleEntity> roles;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static UrlPatternEntity create(Long id, String actionName, String url, String method, String description, Long centerId){
        return UrlPatternEntity.builder()
                .id(id)
                .actionName(actionName)
                .url(url)
                .method(method)
                .description(description)
                .centerId(centerId)
                .build();
    }

}
