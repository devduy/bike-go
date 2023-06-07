package com.example.bikego.controller;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.service.BikeBrandService;
import com.example.bikego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/api/v1/bike-brand")
public class BikeBrandController {
    private final BikeBrandService bikeBrandService;
    private final UserService userService;

    public BikeBrandController(BikeBrandService bikeBrandService, UserService userService) {
        this.bikeBrandService = bikeBrandService;
        this.userService = userService;
    }

    @Operation(summary = "for get all bike brand")
    @GetMapping("{uid}")
    public ResponseEntity<ResponseObject> getAllBikeBrand(@PathVariable("uid") String uid) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeBrandService.getAllBikeBrand();
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
