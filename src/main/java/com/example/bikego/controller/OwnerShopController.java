package com.example.bikego.controller;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.service.OwnerShopService;
import com.example.bikego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/owner-shop")
public class OwnerShopController {
    private final OwnerShopService ownerShopService;
    private final UserService userService;

    public OwnerShopController(OwnerShopService ownerShopService, UserService userService) {
        this.ownerShopService = ownerShopService;
        this.userService = userService;
    }
    @Operation(summary = "for get all owner shop")
    @GetMapping("/{uid}")
    public ResponseEntity<ResponseObject> getAll(@PathVariable("uid") String uid) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return ownerShopService.getAll();
            } else {
                // Người dùng không có quyền truy cập API getAllBikeBrand
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseObject("Error", "Unauthorized", null,null));
            }
        } catch (AuthenticationFailedException e) {
            // Xử lý nếu xảy ra lỗi xác thực người dùng
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        } catch (ValidationException e) {
            // Xử lý nếu xảy ra lỗi trong việc tìm kiếm người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        }
    }
    @Operation(summary = "for get owner shop by user")
    @GetMapping("shop/{uid}")
    public ResponseEntity<ResponseObject> getOwnerShopByUser(@PathVariable("uid") String uid) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return ownerShopService.getShopByUserId(uid);
            } else {
                // Người dùng không có quyền truy cập API getAllBikeBrand
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseObject("Error", "Unauthorized", null,null));
            }
        } catch (AuthenticationFailedException e) {
            // Xử lý nếu xảy ra lỗi xác thực người dùng
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        } catch (ValidationException e) {
            // Xử lý nếu xảy ra lỗi trong việc tìm kiếm người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        }
    }
}
