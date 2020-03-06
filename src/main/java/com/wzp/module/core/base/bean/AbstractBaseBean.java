package com.wzp.module.core.base.bean;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractBaseBean {
    // ceshi

    private String id;

    private Date createDate;

    private Date updateDate;

    private String status;
}
