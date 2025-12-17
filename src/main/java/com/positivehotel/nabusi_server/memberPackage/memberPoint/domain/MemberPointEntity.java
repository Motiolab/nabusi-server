package com.positivehotel.nabusi_server.memberPackage.memberPoint.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_point")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "point", nullable = false)
    private Long point;

    @LastModifiedDate
    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public static MemberPointEntity create(Long memberId, Long point) {
        return MemberPointEntity.builder()
                .memberId(memberId)
                .point(point)
                .build();
    }

    public void addPoint(Long amount) {
        this.point += amount;
    }

    public void usePoint(Long amount) {
        if (this.point < amount) {
            throw new IllegalArgumentException("Not enough points");
        }
        this.point -= amount;
    }
}
