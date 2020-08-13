package com.github.limo.myioc.test.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 12:50
 */
@Data
@Slf4j
public class User {

    private Long id;

    private String name;

    /** 1. Male. 2. Female */
    private Integer sex;

    private Integer age;

    @PostConstruct
    public void init() {
        System.out.println("User PostConstruct init start...");
        System.out.println("User PostConstruct initializing...");
        setSex(1);
        System.out.println("User PostConstruct init end...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("User releases some resources....");
    }

}
