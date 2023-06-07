package com.example.bikego.service.impl;

import com.example.bikego.common.Gender;
import com.example.bikego.common.UserStatus;
import com.example.bikego.dto.*;
import com.example.bikego.entity.OwnerShop;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.exception.RegistrationException;
import com.example.bikego.repository.OwnerShopRepository;
import com.example.bikego.repository.RoleRepository;
import com.example.bikego.repository.UserRepository;
import com.example.bikego.service.UserService;
import com.example.bikego.utils.DateTimeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final FirebaseAuth firebaseAuth;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final OwnerShopRepository ownerShopRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(FirebaseAuth firebaseAuth, RoleRepository roleRepository, UserRepository userRepository, OwnerShopRepository ownerShopRepository, ModelMapper modelMapper) {
        this.firebaseAuth = firebaseAuth;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.ownerShopRepository = ownerShopRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public ResponseEntity<ResponseObject> registerUser(SignInForm signInForm) throws RegistrationException {
        try {

            if (userRepository.findByEmail(signInForm.getEmail()) != null) {
                throw new RegistrationException("Email already exists.");
            }
            //Tạo user trong firebase
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(signInForm.getEmail())
                    .setPassword(signInForm.getPassword())
                    //.setEmailVerified()xac thuc email
                    .setDisabled(false);

            UserRecord userRecord = firebaseAuth.createUser(request);
            //Lưu thông tin owner shop vào database
            OwnerShop ownerShop = new OwnerShop();
            ownerShop.setName(signInForm.getOwnerShopName());
            ownerShop.setAddress(signInForm.getOwnerShopAddress());
            ownerShop.setCreateDate(DateTimeUtils.convertStringToLocalDateTime(DateTimeUtils.now()));
            ownerShop.setUpdateDate(DateTimeUtils.convertStringToLocalDateTime(DateTimeUtils.now()));
            ownerShopRepository.save(ownerShop);

            //Lưu thông tin của user vào database
            User user = new User();
            user.setEmail(userRecord.getEmail());
            user.setPassword(signInForm.getPassword());
            user.setId(userRecord.getUid());
            user.setFirstName(signInForm.getFirstName());
            user.setLastName(signInForm.getLastName());
            user.setBirthday(DateTimeUtils.convertStringToLocalDate(signInForm.getBirthday()));
            user.setUserStatus(UserStatus.ACTIVE);
            user.setGender(Gender.valueOf(signInForm.getGender().trim().toUpperCase()));
            user.setPhone(signInForm.getPhone());
            user.setOwnerShop(ownerShop);
            user.setRole(roleRepository.findByName("OWNER"));

            // Đặt các giá trị khác của đối tượng User từ thông tin mà bạn muốn lưu

            userRepository.save(user); // Lưu đối tượng User vào cơ sở dữ liệu

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Create user success", null, null));
        }catch (FirebaseAuthException | ParseException firebaseAuthException) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Fail", "Failed to create user" +firebaseAuthException.getMessage(), null, null));

        }
    }

    @Override
    public ResponseEntity<ResponseObject> loginUser(LoginForm loginForm) throws FirebaseAuthException{
        try {
            UserRecord userRecord = firebaseAuth.getUserByEmail(loginForm.getEmail());

            // Xác thực người dùng với email và password
            if (userRecord.getUid() != null && checkCredentials(loginForm.getEmail(),loginForm.getPassword())) {
                // Xác thực thành công
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, null));
            } else {
                // Xác thực thất bại
                throw new AuthenticationFailedException("Authentication failed");
            }
        }catch (AuthenticationFailedException firebaseAuthException) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Login Fail", firebaseAuthException.getMessage(), null, null));

        }

    }

    @Override
    public ResponseEntity<ResponseObject> refreshAccessToken(TokenRequestForm tokenRequestForm) {
        try {
            String token = firebaseAuth.createCustomToken(tokenRequestForm.getRefreshToken());
            // Lưu trữ thông tin về token trên server của bạn, ví dụ từ cơ sở dữ liệu
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, token));

        }catch (FirebaseAuthException firebaseAuthException) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Fail",  firebaseAuthException.getMessage(), null, null));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> logout(HttpSession httpSession) {
        // Lấy ID token từ header hoặc body của yêu cầu
        String userId = (String) httpSession.getAttribute("userId");
        try {
            // Đăng xuất người dùng khỏi Firebase Authentication
            firebaseAuth.revokeRefreshTokens(userId);

            // Trả về phản hồi thành công cho client

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Success", "User has been logged out.", null, null));
        } catch (FirebaseAuthException e) {
            // Xử lý lỗi đăng xuất và trả về phản hồi lỗi cho client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("Error", "Failed to log out user.", null,null));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> verifyAccessToken(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Verify Successful", null,uid));
        }catch (FirebaseAuthException firebaseAuthException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Error", firebaseAuthException.getMessage(), null,null));
        }

    }
    public String generateAccessToken(String email, String password) throws FirebaseAuthException, AuthenticationFailedException, ExecutionException, InterruptedException {
        if (checkCredentials(email, password)) {
            // Lấy thông tin userRecord từ Firebase Authentication
            Map<String, Object> claims = new HashMap<>();
            User user = userRepository.findByEmail(email);
            claims.put("role",user.getRole().getName());


            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            String uid = userRecord.getUid();
            firebaseAuth.setCustomUserClaims(uid,claims);

            // Tạo token truy cập

            return firebaseAuth.createCustomToken(uid,claims);
        } else {
            // Xác thực thất bại
            throw new AuthenticationFailedException("Email or Password incorrect");
        }
    }

    private boolean checkCredentials(String email, String password) {
        // Kiểm tra thông tin đăng nhập với cơ sở dữ liệu của bạn
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    //chưa test ngaỳ mai test nếu sai chỉ cần sửa lại hàm này
    @Override
    public User getCurrentUser(String uid) throws AuthenticationFailedException {
        if (!uid.isEmpty()) {
            return userRepository.findById(uid)
                    .orElseThrow(() -> new ValidationException("User is not existed"));

        }
        throw new AuthenticationFailedException("User is not login");

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllUser() {

        List<User> userList = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null,userList));
    }

    @Override
    public ResponseEntity<ResponseObject> getUserDetail(String uid) {
        try {
            User user = getCurrentUser(uid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null,convertToDTO(user)));
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null,null));
        }
    }

    public UserDTO convertToDTO(User user) {
        if(user == null) {
            return null;
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setBirthday(String.valueOf(user.getBirthday()));
        userDTO.setCreateDate(String.valueOf(user.getCreateDate()));
        userDTO.setUpdateDate(String.valueOf(user.getUpdateDate()));
        userDTO.setOwnerShopName(user.getOwnerShop().getName());
        userDTO.setOwnerShopAddress(user.getOwnerShop().getAddress());
        return userDTO;
    }
}
