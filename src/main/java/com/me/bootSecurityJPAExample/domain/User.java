package com.me.bootSecurityJPAExample.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "loginId"))
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

    @Column(nullable = false) private String loginId;
    @Column(nullable = false) private String password;

    private String nickname;
    private String email;
    private String description;

    @Column(nullable = false, updatable = false)    @CreatedDate        private LocalDateTime createdAt;
    @Column(nullable = false, updatable = false)    @CreatedBy          private String createdBy;
    @Column(nullable = false)                       @LastModifiedDate   private LocalDateTime updatedAt;
    @Column(nullable = false)                       @LastModifiedBy     private String updatedBy;
}
