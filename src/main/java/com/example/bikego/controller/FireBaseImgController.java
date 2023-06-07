package com.example.bikego.controller;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.service.FireBaseImgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/fire-base")
public class FireBaseImgController {

    private final FireBaseImgService fireBaseImgService;

    public FireBaseImgController(FireBaseImgService fireBaseImgService) {
        this.fireBaseImgService = fireBaseImgService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> create(@RequestParam(name = "files") MultipartFile files) {


            try {

                String fileName = fireBaseImgService.save(files);

                String imageUrl = fireBaseImgService.getImageUrl(fileName);
                System.out.println(imageUrl);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, null));


            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
            }


    }

}
