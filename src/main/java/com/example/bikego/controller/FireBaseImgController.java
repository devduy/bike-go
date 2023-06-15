package com.example.bikego.controller;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.service.FireBaseImgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/fire-base")
public class FireBaseImgController {

    private final FireBaseImgService fireBaseImgService;

    public FireBaseImgController(FireBaseImgService fireBaseImgService) {
        this.fireBaseImgService = fireBaseImgService;
    }


    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponseObject> create(@RequestParam(name = "file") MultipartFile[] files) {

        for (MultipartFile file : files) {

            try {

                String fileName = fireBaseImgService.save(file);

                String imageUrl = fireBaseImgService.getImageUrl(fileName);
                System.out.println(imageUrl);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, null));

    }

}
