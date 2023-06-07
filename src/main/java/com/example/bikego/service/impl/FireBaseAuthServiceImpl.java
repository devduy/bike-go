//package com.example.bikego.service.impl;
//
//import com.example.bikego.service.FireBaseAuthService;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import com.google.firebase.auth.UserRecord;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FireBaseAuthServiceImpl implements FireBaseAuthService {
//    @Override
//    public String authenticateUser(String idToken) throws FirebaseAuthException {
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//        String uid = decodedToken.getUid();
//
//        // Kiểm tra thông tin người dùng trong cơ sở dữ liệu của bạn
//        // Trả về UID của người dùng hoặc ném một ngoại lệ nếu người dùng không tồn tại
//
//        return uid;
//    }
//
//    @Override
//    public String refreshAccessToken(String refreshToken) throws FirebaseAuthException {
//        String newAccessToken = FirebaseAuth.getInstance().revokeRefreshTokens();
//        UserRecord user = FirebaseAuth.getInstance().getUser(uid);
//
//        // Lưu trữ và trả về newAccessToken cho người dùng
//
//        return newAccessToken;
//    }
//}
