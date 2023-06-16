package com.example.bikego.controller;

import com.example.bikego.dto.RentBikeForm;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.service.BikeService;
import com.example.bikego.service.MobileAppService;
import com.example.bikego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/mobile-app")
public class MobileAppController {
    private final UserService userService;
    private final BikeService bikeService;
    private final MobileAppService mobileAppService;

    public MobileAppController(UserService userService, BikeService bikeService, MobileAppService mobileAppService) {
        this.userService = userService;
        this.bikeService = bikeService;
        this.mobileAppService = mobileAppService;
    }

    @CrossOrigin
    @Operation(summary = "for get all bike available")
    @GetMapping("{uid}")
    public ResponseEntity<ResponseObject> filterBike(@PathVariable("uid") String uid,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "3") int size) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("CUSTOMER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return mobileAppService.getAllBikeAvailable(page,size);
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

    @CrossOrigin
    @Operation(summary = "for user rent bike")
    @PostMapping("/rent")
    public ResponseEntity<ResponseObject> rentBike(@RequestBody RentBikeForm rentBikeForm) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(rentBikeForm.getUid());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("CUSTOMER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return mobileAppService.rentBike(rentBikeForm);
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
    @CrossOrigin
    @Operation(summary = "for user finish rent bike")
    @PostMapping("/finish{uid}")
    public ResponseEntity<ResponseObject> finish(@PathVariable("uid") String uid, @RequestParam Long bikeId) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("CUSTOMER") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return mobileAppService.finish(uid,bikeId);
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
    @CrossOrigin
    @Operation(summary = "for user cancel order")
    @PostMapping("/cancel{uid}")
    public ResponseEntity<ResponseObject> cancel(@PathVariable("uid") String uid, @RequestParam Long bikeId) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("CUSTOMER") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return mobileAppService.cancel(uid,bikeId);
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
    @CrossOrigin
    @Operation(summary = "for user get filter rent history")
    @GetMapping ("/filter{uid}")
    public ResponseEntity<ResponseObject> getFilterRentHistory(@PathVariable("uid") String uid,
                                                               @RequestParam(defaultValue = "0") int pageNumber,
                                                               @RequestParam(defaultValue = "3") int pageSize,
                                                               @Parameter(description = "Rent history status" + " " + "|" + " " +
                                                                       "Values: null for get all,COMPLETED,IN_PROGRESS"
                                                                        )
                                                                   @RequestParam(required = false) String status) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(uid);

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("CUSTOMER") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return mobileAppService.getRentHistory(uid,pageNumber,pageSize,status);
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
