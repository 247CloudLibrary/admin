package com.cloudlibrary.admin.ui.controller;

import com.cloudlibrary.admin.application.service.AdminOperationUseCase;
import com.cloudlibrary.admin.application.service.AdminReadUseCase;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.ui.requestBody.*;
import com.cloudlibrary.admin.ui.view.Admin.AdminCompactView;
import com.cloudlibrary.admin.ui.view.Admin.AdminView;
import com.cloudlibrary.admin.ui.view.ApiResponseView;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(value = "관리자 API")
@RequestMapping("/v1/admin")
public class AdminController {

    private final AdminOperationUseCase adminOperationUseCase;
    private final AdminReadUseCase adminReadUseCase;

    @Autowired
    public AdminController(AdminOperationUseCase adminOperationUseCase, AdminReadUseCase adminReadUseCase) {
        this.adminOperationUseCase = adminOperationUseCase;
        this.adminReadUseCase = adminReadUseCase;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("health-check");
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseView<List<AdminView>>> getAdmins() {
        var results = adminReadUseCase.getAdminListAll();

        return ResponseEntity.ok(new ApiResponseView<>(results.stream().map(AdminView::new).collect(Collectors.toList())));
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<ApiResponseView<AdminView>> getAdmin(@PathVariable("adminId") Long adminId) {
        var query = new AdminReadUseCase.AdminFindQuery(adminId);

        var result = adminReadUseCase.getAdmin(query);

        return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseView<AdminView>> createAdmin(@RequestBody AdminCreateRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new CloudLibraryException(MessageType.BAD_REQUEST);
        }

        var command = AdminOperationUseCase.AdminCreatedCommand.builder()
                .adminName(request.getAdminName())
                .libraryName(request.getLibraryName())
                .tel(request.getTel())
                .email(request.getEmail())
                .address(request.getAddress())
                .id(request.getId())
                .pw(request.getPw())
                .build();

        var result = adminOperationUseCase.createAdmin(command);
        return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
    }

    @GetMapping("/signout")
    public ResponseEntity<ApiResponseView<AdminView>> signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }




    @PatchMapping("/update-state")
    public ResponseEntity<ApiResponseView<AdminView>> updateAdmin(@RequestBody AdminUpdateRequest request) {

        var command = AdminOperationUseCase.AdminUpdateCommand.builder()
                .adminId(request.getAdminId())
                .adminName(request.getAdminName())
                .libraryName(request.getLibraryName())
                .tel(request.getTel())
                .email(request.getEmail())
                .address(request.getAddress())
                .id(request.getId())
                .pw(request.getPw())
                .build();
        var result = adminOperationUseCase.updateAdmin(command);

        return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseView<AdminCompactView>> deleteAdmin(@RequestBody AdminUpdateRequest request) {
        var command = AdminOperationUseCase.AdminDeleteCommand.builder()
                .adminId(request.getAdminId())
                .build();

        adminOperationUseCase.deleteAdmin(command);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/findid")
    public ResponseEntity<ApiResponseView<AdminView>> findId(@RequestBody AdminFindIdRequest request) {

        var result = adminReadUseCase.getAdminByEmail(request.getEmail());

        return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
    }

    @PatchMapping("/findpw")
    public ResponseEntity<ApiResponseView<AdminView>> findPw(@RequestBody AdminFindPwRequest request) {
        Long adminId = adminReadUseCase.isValidIdAndEmail(request);

        if (adminId == 0L) {
            return ResponseEntity.noContent().build();
        } else {
            double doubleValue = Math.random();
            int intValue = (int) (doubleValue * 100000000);

            var command = AdminOperationUseCase.AdminUpdateCommand.builder()
                    .adminId(adminId)
                    .pw(Integer.toString(intValue))
                    .build();
            AdminReadUseCase.FindAdminResult result = adminOperationUseCase.updateAdmin(command);
            result.setChangePassword(Integer.toString(intValue));

            return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
        }



    }





}