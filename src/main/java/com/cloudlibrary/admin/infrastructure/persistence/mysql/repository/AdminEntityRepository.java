package com.cloudlibrary.admin.infrastructure.persistence.mysql.repository;

import com.cloudlibrary.admin.application.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminEntityRepository {
    //Optional<Admin> findAdminById(Long bookId);
    //List<Admin> findAdminAll();
}
