package com.github.limo.myioc.test.model;

import lombok.Data;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 14:40
 */
@Data
public class VipUser {

    private User user;

    /** 会员等级 */
    private Integer level;

    public VipUser(User user, Integer level) {
        this.user = user;
        this.level = level;
    }

}
