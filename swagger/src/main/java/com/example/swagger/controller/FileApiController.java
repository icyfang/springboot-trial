package com.example.swagger.controller;

import com.example.swagger.api.FileApi;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.io.File;

@Controller
public class FileApiController implements FileApi {

    /**
     * @param
     * @return org.springframework.http.ResponseEntity<org.springframework.core.io.Resource>
     * @throws
     * @descirption be sure to test this interface using postman, not swagger.
     * swagger thinks all interfaces return json string, but not document.
     */
    @Override
    public ResponseEntity<Resource> downloadFile() {

        HttpHeaders headers = new HttpHeaders();
        File file = new File("");
        headers.set("Content-Disposition", "attachment; filename*=UTF-8''" + UriUtils.encode(file.getName(), "UTF-8"));
        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> uploadFile(@Valid MultipartFile file) {
        System.out.println("getFile");
        return null;
    }
}