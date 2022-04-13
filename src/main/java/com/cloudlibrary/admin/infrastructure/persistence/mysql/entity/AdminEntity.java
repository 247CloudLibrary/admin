package com.cloudlibrary.admin.infrastructure.persistence.mysql.entity;

import com.cloudlibrary.admin.application.domain.Admin;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@DynamicUpdate
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "admin")
public class AdminEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;

    @Column(nullable = false)
    private String adminName;

    @Column(nullable=false)
    private String libraryName;

    @Column(nullable=false)
    private String tell;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private String id;

    @Column(nullable=false)
    private String pw;

    @Column(nullable=false)
    private String encryptedPw;

    public Admin toAdmin() {
        return Admin.builder()
                .adminId(getAdminId())
                .adminName(getAdminName())
                .libraryName(getLibraryName())
                .tell(getTell())
                .email(getEmail())
                .address(getAddress())
                .id(getId())
                .id(getPw())
                .build();

    }
    public AdminEntity(Admin admin) {
        this.adminId = admin.getAdminId();
        this.adminName = admin.getAdminName();
        this.libraryName = admin.getLibraryName();
        this.tell = admin.getTell();
        this.email = admin.getEmail();
        this.address = admin.getAddress();
        this.id = admin.getId();
        this.pw = admin.getPw();
    }
}
