package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.application.mapper.AdminMapper;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.repository.AdminEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdmninService implements AdminOperationUseCase, AdminReadUseCase{

    private final AdminEntityRepository adminEntityRepository;
    private final AdminMapper adminMapper;

    @Autowired
    public AdmninService(AdminEntityRepository adminEntityRepository
            , AdminMapper adminMapper){
        this.adminEntityRepository = adminEntityRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public FindAdminResult createAdmin(AdminCreatedCommand command) {

        return null;
    }

    @Override
    public void updateAdmin(AdminUpdateCommand command) {

    }

    @Override
    public void deleteAdmin(AdminDeleteCommand command) {

    }

    @Override
    public List<FindAdminResult> getAdminListAll() {
        //return adminEntityRepository.findAdminAll().stream().map(FindAdminResult::findByAdmin).collect(Collectors.toList());
        return adminMapper.findAdminAll().stream().map(FindAdminResult::findByAdmin).collect(Collectors.toList());
    }

    @Override
    public FindAdminResult getAdmin(AdminFindQuery query) {
        Optional<Admin> result = adminMapper.findAdminById(query.getAdminId());

        if(result.isEmpty()) {
            throw new CloudLibraryException(MessageType.NOT_FOUND);
        }

        return FindAdminResult.findByAdmin((result.get()));
    }
}
