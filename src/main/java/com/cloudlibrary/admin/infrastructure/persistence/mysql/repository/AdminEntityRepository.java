package com.cloudlibrary.admin.infrastructure.persistence.mysql.repository;

import com.cloudlibrary.admin.infrastructure.persistence.mysql.entity.AdminEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminEntityRepository extends CrudRepository<AdminEntity, Long> {

}
