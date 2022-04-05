package com.cloudlibrary.admin.ui.controller;

import com.cloudlibrary.admin.application.service.AdminOperationUseCase;
import com.cloudlibrary.admin.application.service.AdminReadUseCase;
import com.cloudlibrary.admin.exception.CloudLibraryException;
import com.cloudlibrary.admin.exception.MessageType;
import com.cloudlibrary.admin.ui.requestBody.AdminCreateRequest;
import com.cloudlibrary.admin.ui.requestBody.AdminFindIdRequest;
import com.cloudlibrary.admin.ui.requestBody.AdminFindPwRequest;
import com.cloudlibrary.admin.ui.requestBody.AdminUpdateRequest;
import com.cloudlibrary.admin.ui.view.Admin.AdminCompactView;
import com.cloudlibrary.admin.ui.view.Admin.AdminView;
import com.cloudlibrary.admin.ui.view.ApiResponseView;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(value = "관리자 API")
@RequestMapping("/v1/admin")
public class AdminController {
    /*
    private final AdminOperationUseCase adminOperationUseCase;
    private final AdminReadUseCase adminReadUseCase;

    @Autowired
    public AdminController(AdminOperationUseCase adminOperationUseCase, AdminReadUseCase adminReadUseCase) {
        this.adminOperationUseCase = adminOperationUseCase;
        this.adminReadUseCase = adminReadUseCase;
    }
     */



    //관리자 전체 조회
    @GetMapping("")
    public ResponseEntity<ApiResponseView<List<AdminView>>> getAdmins() {
        AdminView adminView1 = AdminView.builder()
                .adminId(1)
                .adminName("김뭐뭐")
                .libraryName("개포")
                .tell("010-1010-1011")
                .email("abccd@hhaa.com")
                .address("서울시 강남구 개포2동")
                .build();
        AdminView adminView2 = AdminView.builder()
                .adminId(2)
                .adminName("박뭐뭐")
                .libraryName("역삼")
                .tell("010-2202-2221")
                .email("zzzgg@eeff.net")
                .address("서울시 강남구 역삼동")
                .build();
        List<AdminView> adminView = new ArrayList<>();
        adminView.add(adminView1);
        adminView.add(adminView2);

        //return ResponseEntity.ok(new ApiResponseView<>(results.stream().map(AdminView::new).collect(Collectors.toList())));
        return ResponseEntity.ok(new ApiResponseView<>(adminView));
    }

    //관리자 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseView<AdminView>> getadmin(@PathVariable("id") long id) {
        //var query = new AdminReadUseCase.AdminFindQuery(id);

        //var result = AdminReadUseCase.getAdmin(query);

        //return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
        return ResponseEntity.ok(new ApiResponseView<>(AdminView.builder()
                .adminId(1)
                .adminName("김뭐뭐")
                .libraryName("개포")
                .tell("010-1010-1011")
                .email("abccd@hhaa.com")
                .address("서울시 강남구 개포2동")
                .build()
        ));
    }

    //관리자가입
    @PostMapping("")
    public ResponseEntity<ApiResponseView<AdminView>> createAdmin(@RequestBody AdminCreateRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new CloudLibraryException(MessageType.BAD_REQUEST);
        }
/*
        var command = AdminOperationUseCase.AdminCreatedCommand.builder()
                .rid(request.getRid())
                .libraryId(request.getLibraryId())
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .thumbnailImage(request.getThumbnailImage())
                .coverImage(request.getCoverImage())
                .build();

        var result = AdminOperationUseCase.createAdmin(command);
*/
        //return ResponseEntity.ok(new ApiResponseView<>(new AdminView(result)));
        return ResponseEntity.ok(new ApiResponseView<>(AdminView.builder()
                .adminId(1)
                .adminName("김뭐뭐")
                .libraryName("개포")
                .tell("010-1010-1011")
                .email("abccd@hhaa.com")
                .address("서울시 강남구 개포2동")
                .build()
        ));
    }

    //관리자 정보 수정
    @PutMapping("/update-state")
    public ResponseEntity<ApiResponseView<AdminCompactView>> updateAdmin(@RequestBody AdminUpdateRequest request) {

        return ResponseEntity.ok().build();
    }

    //관리자 탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseView<AdminCompactView>> deleteAdmin(@PathVariable("id") long id) {

        return ResponseEntity.ok().build();

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponseView<AdminView>> login(@RequestBody AdminCreateRequest request) {
        return ResponseEntity.ok().build();
    }



    //아이디 찾기
    @GetMapping("/findid")
    public ResponseEntity<ApiResponseView<AdminView>> findId(@RequestBody AdminFindIdRequest request) {
        return ResponseEntity.ok(new ApiResponseView<>(AdminView.builder().id("myid").build()));
    }

    //비밀번호 찾기기
    @PostMapping("/findpw")
    public ResponseEntity<ApiResponseView<AdminView>> findPw(@RequestBody AdminFindPwRequest request) {
        return ResponseEntity.ok(new ApiResponseView<>(AdminView.builder().pw("mypw").build()));
    }

}
