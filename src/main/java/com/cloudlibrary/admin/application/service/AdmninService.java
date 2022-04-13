package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.application.mapper.AdminMapper;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.entity.AdminEntity;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.repository.AdminEntityRepository;
import com.cloudlibrary.admin.ui.requestBody.AdminFindPwRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdmninService implements AdminOperationUseCase, AdminReadUseCase{

    private final AdminEntityRepository adminEntityRepository;
    private final AdminMapper adminMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public AdmninService(AdminEntityRepository adminEntityRepository
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
                .tell(command.getTell())
                .email(command.getEmail())
                .address(command.getAddress())
                .id(command.getId())
                .pw(command.getPw())
                .build();
        AdminEntity adminEntity = new AdminEntity(admin);
        adminEntity.setEncryptedPw(passwordEncoder.encode(admin.getPw()));
        adminEntityRepository.save(adminEntity);

        //Entity -> Admin -> FindAdminResult
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
            adminEntity.get().setEncryptedPw(passwordEncoder.encode(command.getPw()));
        }
        adminEntityRepository.save(adminEntity.get());

        //Entity -> Admin -> FindAdminResult
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
        Optional<Admin> resultId = adminMapper.findAdminById(request.getId());
        Optional<Admin> resultEmail = adminMapper.findAdminByEmail(request.getEmail());
        if(resultId.get().getAdminId() == resultEmail.get().getAdminId()){
            return resultId.get().getAdminId();
        } else {
            return 0L;
        }

    }


}
