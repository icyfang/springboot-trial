package com.example.swagger.feign.api;

import com.example.swagger.feign.ApiClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * API tests for DownloadApi
 */
public class DownloadApiTest {

    private DownloadApi api;

    @BeforeAll
    public void setup() {
        api = new ApiClient().buildClient(DownloadApi.class);
    }

    /**
     *
     */
    @Test
    public void downloadFileTest() {
        // File response = api.downloadFile();

        // TODO: test validations
    }

    /**
     *
     */
    @Test
    public void uploadFileTest() {
        File file = null;
        // api.uploadFile(file);

        // TODO: test validations
    }

}
