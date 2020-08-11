package com.example.springmvc.aop.exception;

import com.example.springmvc.aop.privilege.PrivilegeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Shanghong Cai
 * @descirption: person handler
 * @create: 2020-07-20 17:19
 */
@Controller
@RequestMapping("/person")
public class PersonHandler {

    @GetMapping("/save")
    @ResponseBody
    @PrivilegeInfo(name = "savePerson")
    public String savePerson() throws Exception {
        int b = 0;
        if (b != 0) {
            int a = 1 / b;
            return "save person";
        } else {
            throw new MyException("不能除以0");
        }
    }

    @GetMapping("/update")
    @ResponseBody
    @PrivilegeInfo(name = "updatePerson")
    public String updatePerson() {
        return "update person";
    }
}
