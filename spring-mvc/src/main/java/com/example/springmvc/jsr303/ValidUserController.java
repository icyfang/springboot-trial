package com.example.springmvc.jsr303;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/validation")
@Validated
public class ValidUserController {

    /**
     * 创建线程安全的Map，模拟users信息的存储
     */
    final static Map<Long, ValidUser> users = Collections.synchronizedMap(new HashMap<>());

    @GetMapping("/userList")
    public List<ValidUser> getUserList() {
        return new ArrayList<>(users.values());
    }

    @Validated(ValidUser.Single.class)
    @PostMapping("/group/single")
    public String postUser(@Valid @RequestBody ValidUser user) {
        users.put(user.getId(), user);
        return "success";
    }

    @Validated(ValidUser.Batch.class)
    @PostMapping("/group/batch")
    public String postUserInBatch(@Valid @RequestBody List<ValidUser> user) {
        user.forEach(this::postUser);
        return "success";
    }

    @PostMapping("/custom")
    public String postCustomUser(@Validated(ValidUser.Single.class) @CustomCon @RequestBody ValidUser user) {
        users.put(user.getId(), user);
        return "success";
    }

    @Validated(ValidUser.Single.class)
    @PostMapping("/nest")
    public String postNestedUser(@Valid @RequestBody NestedUser user) {
        users.put(user.getId(), user);
        return "success";
    }

    @GetMapping("/path/{id}")
    public ValidUser getUser(@Validated @Min(3) @PathVariable Long id) {
        return users.get(id);
    }

    @GetMapping("/query")
    public ValidUser getUserByQuery(@Validated @Min(3) @RequestParam Long id) {
        return users.get(id);
    }

}
