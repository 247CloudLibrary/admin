package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.application.mapper.AdminMapper;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.entity.AdminEntity;
import com.cloudlibrary.admin.infrastructure.persistence.mysql.repository.AdminEntityRepository;
import com.cloudlibrary.admin.ui.requestBody.AdminFindPwRequest;
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
        adminEntityRepository.save(adminEntity);

        //Entity -> Admin -> FindAdminResult
        admin = adminEntity.toAdmin();
        return FindAdminResult.findByAdmin(admin);
    }

    @Override
    public FindAdminResult updateAdmin(AdminUpdateCommand command) {
        //영속성 때문에 일단 조회
        adminEntityRepository.findById(command.getAdminId());

        //command -> Admin -> Entity
        Admin admin = Admin.builder()
                .adminId(command.getAdminId())
                .adminName(command.getAdminName())
                .libraryName(command.getLibraryName())
                .tell(command.getTell())
                .email(command.getEmail())
                .address(command.getAddress())
                .id(command.getId())
                .pw(command.getPw())
                .build();
        System.out.println(admin);
        AdminEntity adminEntity = new AdminEntity(admin);
        System.out.println(adminEntity);
        adminEntityRepository.save(adminEntity);

        //Entity -> Admin -> FindAdminResult
        admin = adminEntity.toAdmin();
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
    public boolean isValidIdAndEmail(AdminFindPwRequest request) {
        Optional<Admin> resultId = adminMapper.findAdminById(request.getId());
        Optional<Admin> resultEmail = adminMapper.findAdminByEmail(request.getEmail());
        if(resultId.get().getAdminId() == resultEmail.get().getAdminId()){
            return true;
        } else {
            return false;
        }

    }


}
