package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.infrastructure.mapper.AdminMapper;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.entity.AdminEntity;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.repository.AdminEntityRepository;
import com.cloudlibrary.admin.ui.security.SecurityConfig;
import com.cloudlibrary.admin.ui.requestBody.AdminFindPwRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService implements AdminOperationUseCase, AdminReadUseCase {

    private final AdminEntityRepository adminEntityRepository;
    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminEntityRepository adminEntityRepository
            , AdminMapper adminMapper){
        this.adminEntityRepository = adminEntityRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public List<FindAdminResult> getAdminListAll() {
        return adminMapper.findAdminAll().stream().map(FindAdminResult::findByAdmin).collect(Collectors.toList());
    }

    @Override
    public FindAdminResult getAdmin(AdminFindQuery query) {
        Optional<Admin> result = adminMapper.findAdminByAdminId(query.getAdminId());

        if(result.isEmpty()) {
            throw new CloudLibraryException(MessageType.NOT_FOUND);
        }

        return FindAdminResult.findByAdmin((result.get()));
    }

    @Override
    public FindAdminResult createAdmin(AdminCreatedCommand command) {
        //command -> Admin -> Entity
        Admin admin = Admin.builder()
                .adminName(command.getAdminName())
                .libraryName(command.getLibraryName())
                .tel(command.getTel())
                .email(command.getEmail())
                .address(command.getAddress())
                .id(command.getId())
                .pw(command.getPw())
                .build();
        AdminEntity adminEntity = new AdminEntity(admin);
        adminEntity.setEncryptedPw(SecurityConfig.passwordEncoder().encode(admin.getPw()));
        adminEntityRepository.save(adminEntity);

        admin = adminEntity.toAdmin();
        return FindAdminResult.findByAdmin(admin);
    }

    @Override
    public FindAdminResult updateAdmin(AdminUpdateCommand command) {
        Optional<AdminEntity> adminEntity = adminEntityRepository.findById(command.getAdminId());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        mapper.map(command, adminEntity.get());

        if(command.getPw()!=null){
            adminEntity.get().setEncryptedPw(SecurityConfig.passwordEncoder().encode(command.getPw()));
        }
        adminEntityRepository.save(adminEntity.get());

        Admin admin = adminEntity.get().toAdmin();
        return FindAdminResult.findByAdmin(admin);
    }

    @Override
    public void deleteAdmin(AdminDeleteCommand command) {
        AdminEntity adminEntity = AdminEntity.builder()
                .adminId(command.getAdminId()).build();
        adminEntityRepository.delete(adminEntity);
    }

    @Override
    public FindAdminResult getAdminByEmail(String email) {
        Optional<Admin> result = adminMapper.findAdminByEmail(email);

        if(result.isEmpty()) {
            throw new CloudLibraryException(MessageType.NOT_FOUND);
        }

        return FindAdminResult.findByAdmin((result.get()));
    }

    @Override
    public Long isValidIdAndEmail(AdminFindPwRequest request) {
        Optional<AdminEntity> resultId = adminMapper.findAdminById(request.getId());
        Optional<Admin> resultEmail = adminMapper.findAdminByEmail(request.getEmail());
        if(resultId.get().getAdminId() == resultEmail.get().getAdminId()){
            return resultId.get().getAdminId();
        } else {
            return 0L;
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminEntity> resultId = adminMapper.findAdminById(username);
        if (resultId == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(resultId.get().getEmail(), resultId.get().getEncryptedPw(),
                true, true, true, true, new ArrayList<>());
    }
}
