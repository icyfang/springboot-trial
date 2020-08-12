package com.example.springmvc.asyncIntercptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Shanghong Cai
 * @since 2020-08-12
 */
@RestController
@RequestMapping("/async/v1")
public class AsyncExampleController {

    @RequestMapping("/deferredResult")
    @ResponseBody
    public DeferredResult<String> getDeferredResult() {
        DeferredResult<String> deferredResult = new DeferredResult<String>();

        deferredResult.setResult(getResult());
        System.out.println("return result");
        return deferredResult;
    }

    private String getResult() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "result";
    }

}
