package com.example.springcontext.conditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Shanghong Cai
 * @since 2020-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemBean {
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统code
     */
    private String systemCode;
}
