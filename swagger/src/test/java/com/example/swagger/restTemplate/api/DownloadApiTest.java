/*
 * Swagger Petstore
 * This is a sample server Petstore server.  You can find out more about     Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).      For this sample, you can use the api key `special-key` to test the authorization     filters.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: apiteam@swagger.io
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.example.swagger.restTemplate.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * API tests for DownloadApi
 */
@Disabled
public class DownloadApiTest {

    private final DownloadApi api = new DownloadApi();

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void downloadFileTest() {
        File response = api.downloadFile();

        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadFileTest() {
        File file = null;
        api.uploadFile(file);

        // TODO: test validations
    }

}
