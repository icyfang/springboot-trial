package com.example.swagger.feign.api;

import com.example.swagger.feign.ApiClient;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * API tests for DownloadApi
 */
public class DownloadApiTest {

    private DownloadApi api;

    @Before
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
