package com.cloudlibrary.admin.application.mapper;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    Optional<Admin> findAdminByAdminId(Long adminId);
    List<Admin> findAdminAll();
    Optional<Admin> findAdminByEmail(String email);
    Optional<AdminEntity> findAdminById(String id);
}
